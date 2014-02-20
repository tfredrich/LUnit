/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author toddf
 * @since Nov 20, 2008
 */
public class BaseTestObserverTest
{
	// SECTION: VARIABLES

	private DemoTestObserver observer;
	
	// SECTION: INITIALIZATION

	@Before
	public void initialize()
	{
		observer = new DemoTestObserver();
	}

	
	// SECTION: TESTS

	@Test
	public void testTimings()
	throws Exception
	{
		assertInitialState(observer);
		observer.notify(new TestEvent(EventType.TEST_SETUP, this));
		observer.notify(new TestEvent(EventType.TEST_START, this));
		long testStart = System.currentTimeMillis();
		observer.notify(new TestEvent(EventType.ACTION_SETUP, this));
		observer.notify(new TestEvent(EventType.ACTION_START, this));
		long iterationStart = System.currentTimeMillis();
		Thread.sleep(49);
		observer.notify(new TestEvent(EventType.LATENCY_TIMING_START, this));
		long latencyStart = System.currentTimeMillis();
		Thread.sleep(50);
		observer.notify(new LatencyTimingStopEvent(this, latencyStart));
		observer.notify(new ActionStopEvent(this, iterationStart));
		observer.notify(new TestEvent(EventType.ACTION_TEARDOWN, this));
		observer.notify(new TestEvent(EventType.ACTION_SETUP, this));
		observer.notify(new TestEvent(EventType.ACTION_START, this));
		iterationStart = System.currentTimeMillis();
		Thread.sleep(87);
		observer.notify(new TestEvent(EventType.LATENCY_TIMING_START, this));
		latencyStart = System.currentTimeMillis();
		Thread.sleep(50);
		observer.notify(new LatencyTimingStopEvent(this, latencyStart));
		observer.notify(new ActionStopEvent(this, iterationStart));
		observer.notify(new TestEvent(EventType.ACTION_TEARDOWN, this));
		observer.notify(new TestEvent(EventType.ACTION_SETUP, this));
		observer.notify(new TestEvent(EventType.ACTION_START, this));
		iterationStart = System.currentTimeMillis();
		Thread.sleep(69);
		observer.notify(new TestEvent(EventType.LATENCY_TIMING_START, this));
		latencyStart = System.currentTimeMillis();
		Thread.sleep(50);
		observer.notify(new LatencyTimingStopEvent(this, latencyStart));
		observer.notify(new ActionStopEvent(this, iterationStart));
		observer.notify(new TestEvent(EventType.ACTION_TEARDOWN, this));
		Thread.sleep(433);
		observer.notify(new TestStopEvent(this, testStart));
		observer.notify(new TestEvent(EventType.TEST_TEARDOWN, this));
		
		
		assertEquals(0, observer.handleErrorCount);
		assertEquals(0, observer.handleFailureCount);
		assertEquals(3, observer.handleIterationSetupCount);
		assertEquals(3, observer.handleIterationStartCount);
		assertEquals(3, observer.handleIterationStopCount);
		assertEquals(3, observer.handleIterationTeardownCount);
		assertEquals(0, observer.handleMessageCount);
		assertEquals(0, observer.handleOtherCount);
		assertEquals(1, observer.handleTestSetupCount);
		assertEquals(1, observer.handleTestStartCount);
		assertEquals(1, observer.handleTestStopCount);
		assertEquals(1, observer.handleTestTeardownCount);
		assertEquals(3, observer.handleLatencyStartCount);
		assertEquals(3, observer.handleLatencyStopCount);

		assertEquals(0, observer.getErrorCount());
		assertEquals(0, observer.getFailureCount());
		assertEquals(3, observer.getIterationCount());
		assertTrue("ET(ms): " + observer.getElapsedTime(), (observer.getElapsedTime() >= 787));
		assertTrue("IT(ms): " + observer.getTotalIterationTime(), (observer.getTotalIterationTime() >= 354));
		assertTrue("LT(ms): " + observer.getTotalLatencyTime(), (observer.getTotalLatencyTime() >= 149));
	}

	@Test
	public void testErrorNotification()
	{
		assertInitialState(observer);
		observer.notify(new ErrorEvent(new UnsupportedOperationException(), this));
		observer.notify(new ErrorEvent(new UnsupportedOperationException(), this));
		observer.notify(new ErrorEvent(new UnsupportedOperationException(), this));
		
		assertEquals(3, observer.handleErrorCount);
		assertEquals(0, observer.handleFailureCount);
		assertEquals(0, observer.handleIterationSetupCount);
		assertEquals(0, observer.handleIterationStartCount);
		assertEquals(0, observer.handleIterationStopCount);
		assertEquals(0, observer.handleIterationTeardownCount);
		assertEquals(0, observer.handleMessageCount);
		assertEquals(0, observer.handleOtherCount);
		assertEquals(0, observer.handleTestSetupCount);
		assertEquals(0, observer.handleTestStartCount);
		assertEquals(0, observer.handleTestStopCount);
		assertEquals(0, observer.handleTestTeardownCount);
		assertEquals(0, observer.handleLatencyStartCount);
		assertEquals(0, observer.handleLatencyStopCount);

		assertEquals(3, observer.getErrorCount());
		assertEquals(0, observer.getElapsedTime());
		assertEquals(0, observer.getFailureCount());
		assertEquals(0, observer.getIterationCount());
		assertEquals(0, observer.getTotalIterationTime());
		assertEquals(0, observer.getTotalLatencyTime());
	}

	@Test
	public void testFailureNotification()
	{
		assertInitialState(observer);
		String message = "Failure Event";
		observer.notify(new FailureEvent(message, this));
		observer.notify(new FailureEvent(message, this));
		observer.notify(new FailureEvent(message, this));
		
		assertEquals(0, observer.handleErrorCount);
		assertEquals(3, observer.handleFailureCount);
		assertEquals(0, observer.handleIterationSetupCount);
		assertEquals(0, observer.handleIterationStartCount);
		assertEquals(0, observer.handleIterationStopCount);
		assertEquals(0, observer.handleIterationTeardownCount);
		assertEquals(0, observer.handleMessageCount);
		assertEquals(0, observer.handleOtherCount);
		assertEquals(0, observer.handleTestSetupCount);
		assertEquals(0, observer.handleTestStartCount);
		assertEquals(0, observer.handleTestStopCount);
		assertEquals(0, observer.handleTestTeardownCount);
		assertEquals(0, observer.handleLatencyStartCount);
		assertEquals(0, observer.handleLatencyStopCount);

		assertEquals(0, observer.getErrorCount());
		assertEquals(0, observer.getElapsedTime());
		assertEquals(3, observer.getFailureCount());
		assertEquals(0, observer.getIterationCount());
		assertEquals(0, observer.getTotalIterationTime());
		assertEquals(0, observer.getTotalLatencyTime());
	}

	@Test
	public void testMessageNotification()
	{
		assertInitialState(observer);
		String message = "Message Event";
		observer.notify(new MessageEvent(message, this));
		observer.notify(new MessageEvent(message, this));
		observer.notify(new MessageEvent(message, this));
		
		assertEquals(0, observer.handleErrorCount);
		assertEquals(0, observer.handleFailureCount);
		assertEquals(0, observer.handleIterationSetupCount);
		assertEquals(0, observer.handleIterationStartCount);
		assertEquals(0, observer.handleIterationStopCount);
		assertEquals(0, observer.handleIterationTeardownCount);
		assertEquals(3, observer.handleMessageCount);
		assertEquals(0, observer.handleOtherCount);
		assertEquals(0, observer.handleTestSetupCount);
		assertEquals(0, observer.handleTestStartCount);
		assertEquals(0, observer.handleTestStopCount);
		assertEquals(0, observer.handleTestTeardownCount);
		assertEquals(0, observer.handleLatencyStartCount);
		assertEquals(0, observer.handleLatencyStopCount);

		assertEquals(0, observer.getErrorCount());
		assertEquals(0, observer.getElapsedTime());
		assertEquals(0, observer.getFailureCount());
		assertEquals(0, observer.getIterationCount());
		assertEquals(0, observer.getTotalIterationTime());
		assertEquals(0, observer.getTotalLatencyTime());
	}

	@Test
	public void testOtherNotification()
	{
		assertInitialState(observer);
		observer.notify(new TestEvent(EventType.OTHER, this));
		observer.notify(new TestEvent(EventType.OTHER, this));
		observer.notify(new TestEvent(EventType.OTHER, this));
		
		assertEquals(0, observer.handleErrorCount);
		assertEquals(0, observer.handleFailureCount);
		assertEquals(0, observer.handleIterationSetupCount);
		assertEquals(0, observer.handleIterationStartCount);
		assertEquals(0, observer.handleIterationStopCount);
		assertEquals(0, observer.handleIterationTeardownCount);
		assertEquals(0, observer.handleMessageCount);
		assertEquals(3, observer.handleOtherCount);
		assertEquals(0, observer.handleTestSetupCount);
		assertEquals(0, observer.handleTestStartCount);
		assertEquals(0, observer.handleTestStopCount);
		assertEquals(0, observer.handleTestTeardownCount);
		assertEquals(0, observer.handleLatencyStartCount);
		assertEquals(0, observer.handleLatencyStopCount);

		assertEquals(0, observer.getErrorCount());
		assertEquals(0, observer.getElapsedTime());
		assertEquals(0, observer.getFailureCount());
		assertEquals(0, observer.getIterationCount());
		assertEquals(0, observer.getTotalIterationTime());
		assertEquals(0, observer.getTotalLatencyTime());
	}
	
	
	// SECTION: UTILITY
	
	private void assertInitialState(DemoTestObserver anObserver)
	{
		assertNotNull(anObserver);
		assertEquals(0, anObserver.handleErrorCount);
		assertEquals(0, anObserver.handleFailureCount);
		assertEquals(0, anObserver.handleIterationSetupCount);
		assertEquals(0, anObserver.handleIterationStartCount);
		assertEquals(0, anObserver.handleIterationStopCount);
		assertEquals(0, anObserver.handleIterationTeardownCount);
		assertEquals(0, anObserver.handleMessageCount);
		assertEquals(0, anObserver.handleOtherCount);
		assertEquals(0, anObserver.handleTestSetupCount);
		assertEquals(0, anObserver.handleTestStartCount);
		assertEquals(0, anObserver.handleTestStopCount);
		assertEquals(0, anObserver.handleTestTeardownCount);
		assertEquals(0, anObserver.handleLatencyStartCount);
		assertEquals(0, anObserver.handleLatencyStopCount);

		assertEquals(0, anObserver.getErrorCount());
		assertEquals(0, anObserver.getElapsedTime());
		assertEquals(0, anObserver.getFailureCount());
		assertEquals(0, anObserver.getIterationCount());
		assertEquals(0, anObserver.getTotalIterationTime());
		assertEquals(0, anObserver.getTotalLatencyTime());
	}

	
	// SECTION: INNER CLASS
	
	private class DemoTestObserver
	extends BaseTestObserver
	{
		private int handleErrorCount;
		private int handleFailureCount;
		private int handleIterationSetupCount;
		private int handleIterationStartCount;
		private int handleIterationStopCount;
		private int handleIterationTeardownCount;
		private int handleTestSetupCount;
		private int handleTestStartCount;
		private int handleTestStopCount;
		private int handleTestTeardownCount;
		private int handleMessageCount;
		private int handleOtherCount;
		private int handleLatencyStartCount;
		private int handleLatencyStopCount;

		@Override
		protected void handleError(ErrorEvent event)
		{
			++handleErrorCount;
		}

		@Override
		protected void handleFailure(FailureEvent event)
		{
			++handleFailureCount;
		}

		@Override
		protected void handleIterationSetup(TestEvent event)
		{
			++handleIterationSetupCount;
		}

		@Override
		protected void handleTestSetup(TestEvent event)
		{
			++handleTestSetupCount;
		}
		
		@Override
		protected void handleMessage(MessageEvent event)
		{
			++handleMessageCount;
		}

		@Override
		protected void handleOtherEvent(TestEvent event)
		{
			++handleOtherCount;
		}

		@Override
        protected void handleIterationStart(TestEvent event)
        {
			++handleIterationStartCount;
        }

		@Override
        protected void handleIterationStop(TimerStopEvent event)
        {
			++handleIterationStopCount;
        }

		@Override
        protected void handleIterationTeardown(TestEvent event)
        {
			++handleIterationTeardownCount;
        }

		@Override
        protected void handleTestStart(TestEvent event)
        {
			++handleTestStartCount;
        }

		@Override
        protected void handleTestStop(TimerStopEvent event)
        {
			++handleTestStopCount;
        }

		@Override
        protected void handleTestTeardown(TestEvent event)
        {
			++handleTestTeardownCount;
        }

		@Override
        protected void handleLatencyTimingStart(TestEvent event)
        {
			++handleLatencyStartCount;
        }

		@Override
        protected void handleLatencyTimingStop(TimerStopEvent event)
        {
			++handleLatencyStopCount;
        }
	}
}
