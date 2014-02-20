/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Executes all TestCase instances in parallel, finishing when the last one completes.
 * Only a single execution of TestRunner.setup() and teardown() occur before/after the
 * parallel test cases are executed.
 *
 * @author toddf
 * @since Dec 2, 2008
 */
public class ParallelTestRunner
extends TestRunner
{
	@Override
	public void executeTestCases()
	throws Exception
	{
		List<Thread> testCaseThreads = createThreads();
		startThreads(testCaseThreads);
		waitForThreads(testCaseThreads);
	}

	
	// SECTION: UTILITY

	/**
	 * @return
	 */
	private List<Thread> createThreads()
	{
		List<Thread> testCaseThreads = new ArrayList<Thread>();

		for (TestCase testCase : getTestCases())
		{
			Thread t = new Thread(new TestCaseRunner(testCase));
			t.setDaemon(false);
			testCaseThreads.add(t);
		}

		return testCaseThreads;
	}

	/**
	 * @param threads
	 */
	private void startThreads(List<Thread> threads)
	{
		for (Thread thread : threads)
		{
			thread.start();
		}
	}

	/**
	 * @param threads
	 * @throws InterruptedException
	 */
	private void waitForThreads(List<Thread> threads)
	throws InterruptedException
	{
		for (Thread thread : threads)
		{
			thread.join();
		}
	}

    
    // SECTION: INNER CLASS
    
	private class TestCaseRunner
	implements Runnable
	{
		private TestCase testCase;

		public TestCaseRunner(TestCase testCase)
		{
			super();
			this.testCase = testCase;
		}

        @Override
        public void run()
        {
			executeTestCase(testCase);
        }
	}
}
