/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ecollege.lunit.event.EventCallback;
import com.ecollege.lunit.event.TestEvent;

/**
 * @author toddf
 * @since Dec 2, 2008
 */
public class InMemoryEventRepository
implements EventRepository
{
	// SECTION: INSTANCE VARIABLES
	
	private List<TestEvent> events = new ArrayList<TestEvent>();
	private final LinkedBlockingQueue<TestEvent> queue = new LinkedBlockingQueue<TestEvent>();
	private final Thread dequeueingThread;
	private final AtomicBoolean isStopRequested = new AtomicBoolean(false);
	
	
	// SECTION: Constructor

	public InMemoryEventRepository() {
		dequeueingThread = new Thread(new Runnable() {
			public void run() {
				while(true)
				{
					TestEvent item = (TestEvent) queue.poll();
					if(item != null)
						events.add(item);
					else if(isStopRequested.get())
						break;
				}
			}
		});

		dequeueingThread.setName("InMemoryRepository-Dequeueing");
		dequeueingThread.start();
	}

	// SECTION: RESULT REPOSITORY

	/* (non-Javadoc)
	 * @see com.ecollege.lunit.result.ResultRepository#insert(com.ecollege.lunit.event.TestEvent)
	 */
	@Override
	public void insert(TestEvent event) throws RepositoryException
	{
		if( isStopRequested.get() )
			throw new RepositoryException("Illegal operation:  insert() cannot be called after iterate() (TestEvent originator: " + event.getOriginatorName()); 
		try {
			queue.put(event);
		} catch (InterruptedException e) {
			(new Exception("Interrupted while putting a TestEvent on the queue",e)).printStackTrace();
		}
	}

	private void waitForDequeueingThreadToFinish()
	{
		isStopRequested.set(true);
		try {
			dequeueingThread.join();
		} catch (InterruptedException e) {
			(new Exception("Interrupted while joining the dequeueing thread",e)).printStackTrace();
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * Will tell the dequeuing thread to catch up and quit, and then wait for it to complete so
	 * that all the queued items are recorded before we try to iterate them.
	 */
	public void iterate(EventCallback callback)
	{
		waitForDequeueingThreadToFinish();
		
		for (TestEvent event : events)
		{
			callback.handleEvent(event);
		}
	}
}
