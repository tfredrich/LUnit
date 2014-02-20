/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

import java.util.Date;

import com.jigsforjava.util.Range;


/**
 * BaseTestObserver keeps track of errors, failures, elapsed time (ms), iteration
 * count, cumulative iteration time (ms), total latency time (ms).  Simply subscribe
 * to the scenario(s) to observe.
 * 
 * @author toddf
 * @since Nov 11, 2008
 */
public class BaseTestObserver
implements TestObserver
{
	// SECTION: INSTANCE VARIABLES

	// An error occurs when an exception is thrown.
	private int errorCount;

	// The number of assertion failures.
	private int failureCount;
	
	// The number of milliseconds between when the test started (after setup) and the test ended (before teardown).
	private long elapsedTime;

	// The number of loops performed thus far in the test.
	private int iterationCount;
	
	// The additive amount of iteration times, excluding fixture (setup and teardown) time.
	private long totalIterationTime;
	
	// The additive amount of time that accounts for latency.
	private long totalLatencyTime;
	
	
	// SECTION: OBSERVER NOTIFICATION

	@Override
	public void notify(TestEvent event)
	{
		if (event.isLatencyTimingStop())
		{
			TimerStopEvent timerStopEvent = (TimerStopEvent) event;
			Range<Date> timespan = timerStopEvent.getTimespan();
			totalLatencyTime += timespan.getEnd().getTime() - timespan.getStart().getTime();
			handleLatencyTimingStop(timerStopEvent);
		}
		else if (event.isActionStop())
		{
			TimerStopEvent timerStopEvent = (TimerStopEvent) event;
			Range<Date> timespan = timerStopEvent.getTimespan();
			totalIterationTime += timespan.getEnd().getTime() - timespan.getStart().getTime();
			handleIterationStop(timerStopEvent);
		}
		else if (event.isTestStop())
		{
			TimerStopEvent timerStopEvent = (TimerStopEvent) event;
			Range<Date> timespan = timerStopEvent.getTimespan();
			elapsedTime += timespan.getEnd().getTime() - timespan.getStart().getTime();
			handleTestStop(timerStopEvent);
		}
		else if (event.isLatencyTimingStart())
		{
			handleLatencyTimingStart(event);
		}
		else if (event.isActionStart())
		{
			++iterationCount;
			handleIterationStart(event);
		}
		else if (event.isActionSetup())
		{
			handleIterationSetup(event);
		}
		else if (event.isActionTeardown())
		{
			handleIterationTeardown(event);
		}
		else if (event.isTestStart())
		{
			handleTestStart(event);
		}
		else if (event.isTestSetup())
		{
			errorCount = 0;
			failureCount = 0;
			elapsedTime = 0L;
			iterationCount = 0;
			totalIterationTime = 0L;
			handleTestSetup(event);
		}
		else if (event.isTestTeardown())
		{
			handleTestTeardown(event);
		}
		else if (event.isError())
		{
			++errorCount;
			handleError((ErrorEvent) event);
		}
		else if (event.isFailure())
		{
			++failureCount;
			handleFailure((FailureEvent) event);
		}
		else if (event.isMessage())
		{
			handleMessage((MessageEvent) event);
		}
		else
		{
			handleOtherEvent(event);
		}
	}

	
	// SECTION: ACCESSORS (OBSERVATION STATISTICS)

	public int getErrorCount()
    {
    	return errorCount;
    }

	public int getFailureCount()
    {
    	return failureCount;
    }

	public long getElapsedTime()
    {
    	return elapsedTime;
    }

	public int getIterationCount()
    {
    	return iterationCount;
    }

	public long getTotalIterationTime()
    {
    	return totalIterationTime;
    }
	
	public long getTotalLatencyTime()
	{
		return totalLatencyTime;
	}
	
	
	// SECTION: TEMPLATE METHODS FOR SUBCLASS EVENT HANDLING
	
	protected void handleError(ErrorEvent event)
	{
		// no default behavior
	}

	protected void handleFailure(FailureEvent event)
	{
		// no default behavior
	}

	protected void handleIterationSetup(TestEvent event)
	{
		// no default behavior
	}

	protected void handleIterationStart(TestEvent event)
	{
		// no default behavior
	}

	protected void handleIterationStop(TimerStopEvent event)
	{
		// no default behavior
	}

	protected void handleIterationTeardown(TestEvent event)
	{
		// no default behavior
	}

	protected void handleTestSetup(TestEvent event)
	{
		// no default behavior
	}

	protected void handleTestStart(TestEvent event)
	{
		// no default behavior
	}

	protected void handleTestStop(TimerStopEvent event)
	{
		// no default behavior
	}

	protected void handleTestTeardown(TestEvent event)
	{
		// no default behavior
	}
	
	protected void handleMessage(MessageEvent event)
	{
		// no default behavior
	}

	protected void handleOtherEvent(TestEvent event)
	{
		// no default behavior
	}

	protected void handleLatencyTimingStart(TestEvent event)
    {
		// no default behavior
    }

	protected void handleLatencyTimingStop(TimerStopEvent timerStopEvent)
    {
		// no default behavior
    }
}
