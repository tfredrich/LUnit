package com.ecollege.lunit.scenario.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SequentialNameChooserTest
{
	private List<String>errors = Collections.synchronizedList(new ArrayList<String>());

	@Test
	public void testUnlimitedChooseWrap()
	{
		SequentialNameChooser chooser = new SequentialNameChooser();
		chooser.setStartingId(1);
		chooser.setPrefix("user");
		
		assertEquals("user1", chooser.choose());
		
		for (int i = 0; i < 100000; ++i)
		{
			assertFalse("user1".equals(chooser.choose()));
		}
	}
	
	@Test
	public void testThreadedSynchronizedChoosing()
	throws InterruptedException
	{
		SequentialNameChooser chooser = new SequentialNameChooser();
		chooser.setStartingId(1);
		chooser.setPrefix("user");
		Set<String> choices = Collections.synchronizedSet(new HashSet<String>(500000));
		List<Thread> threads = createThreads(500, 1000, chooser, choices);
		startThreads(threads);
		waitForThreads(threads);
		
		for (String error : errors)
		{
			System.out.println(error);
		}

		assertTrue(errors.isEmpty());
	}

	private List<Thread> createThreads(int threadCount, int choicesPerThread, SequentialNameChooser chooser, Set<String> choices)
	{
		List<Thread> threads = new ArrayList<Thread>(threadCount);
		
		for (int i = 0; i < threadCount; ++i)
		{
			Thread thread = new ChooserThread(choicesPerThread, chooser, choices);
			thread.setName("ChooserTest" + i);
			threads.add(thread);
		}

		return threads;
	}

	private void startThreads(List<Thread> threads)
	{
		for (Thread thread : threads)
		{
			thread.run();
		}
	}

	private void waitForThreads(List<Thread> threads)
	throws InterruptedException
	{
		for (Thread thread : threads)
		{
			thread.join();
		}
	}
	
	private class ChooserThread
	extends Thread
	{
		private int maxChoices;
		private SequentialNameChooser chooser;
		private Set<String>values;
		
		public ChooserThread(int maxChoices, SequentialNameChooser chooser, Set<String> values)
		{
			super();
			this.maxChoices = maxChoices;
			this.chooser = chooser;
			this.values = values;
		}

		@Override
		public void run()
		{
			for (int i = 0; i < maxChoices; ++i)
			{
				String choice = chooser.choose();
				boolean isAdded = values.add(choice);

				if (choice == null)
				{
					errors.add(getName() + " returned NULL choice");
				}
				else if (!isAdded)
				{
					errors.add(getName() + " encountered duplicate choice: " + choice);
				}
			}
		}
	}
}
