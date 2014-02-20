package com.ecollege.lunit.repository;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Test;

import com.ecollege.lunit.event.EventCallback;
import com.ecollege.lunit.event.FailureEvent;
import com.ecollege.lunit.event.TestEvent;

public class InMemoryEventRepositoryTest {
	
	@Test
	public void insertEvents() throws RepositoryException, InterruptedException
	{
		InMemoryEventRepository repo = new InMemoryEventRepository();
		int numEventsToAdd = 1000;
		
		for(int x=0; x < numEventsToAdd; x++)
		{
			repo.insert(new FailureEvent("Event " + x, this));
		}
		
		Thread.sleep(10);
		
		final AtomicInteger count = new AtomicInteger();
		repo.iterate(new EventCallback() {
			@Override
			public void handleEvent(TestEvent event) {
				count.incrementAndGet();
			}
		});
		
		Assert.assertEquals(numEventsToAdd, count.get());
	}

}
