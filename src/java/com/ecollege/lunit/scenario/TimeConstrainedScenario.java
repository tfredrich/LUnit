/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.scenario;

import java.util.Timer;
import java.util.TimerTask;

import com.jigsforjava.time.TimeConstants;

/**
 * Defines a Scenario that is limited by how long it will run.
 * 
 * @author toddf
 * @since Sep 12, 2008
 */
public abstract class TimeConstrainedScenario<T extends TimeConstrainedScenario<T>>
extends Scenario<T>
{
	// SECTION: INSTANCE VARIABLES

	private long maxSeconds = 0L;

	
	// SECTION: CONSTRUCTORS
	
	public TimeConstrainedScenario()
	{
		super();
	}

	public TimeConstrainedScenario(long seconds)
	{
		super();
		setMaxSeconds(seconds);
	}

	
	// SECTION: ACCESSORS/MUTATORS

	public long getMaxSeconds()
    {
    	return maxSeconds;
    }

	public void setMaxSeconds(long seconds)
    {
    	this.maxSeconds = seconds;
    }

	
	// SECTION: SUB-CLASS RESPONSIBILITIES

	@Override
    protected void execute()
	throws Exception
    {
		Timer timer = new Timer( false );
		timer.schedule(new StopScenarioTimerTask(this), (getMaxSeconds() * TimeConstants.MILLIS_PER_SECOND));

		try
		{
			super.execute();
		}
		finally
		{
			timer.cancel();
		}
    }

    @Override
    protected boolean shouldContinue()
    {
    	return true;
    }
    
    @Override
    public T copy()
    {
    	T clone = super.copy();
    	clone.setMaxSeconds(getMaxSeconds());
    	return clone;
    }

	
	// SECTION: INNER CLASS

	private class StopScenarioTimerTask
	extends TimerTask
	{
		TimeConstrainedScenario<?> loadTest;
	
		public StopScenarioTimerTask(TimeConstrainedScenario<?> loadTest)
		{
			super();
			this.loadTest = loadTest;
		}

		@Override
		public void run()
		{
			loadTest.setStopped();
		}
	}
}
