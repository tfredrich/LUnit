/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.scenario;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ecollege.lunit.event.ActionStopEvent;
import com.ecollege.lunit.event.ErrorEvent;
import com.ecollege.lunit.event.EventType;
import com.ecollege.lunit.event.LatencyTimingStopEvent;
import com.ecollege.lunit.event.ObservableTest;
import com.ecollege.lunit.event.ScenarioStopEvent;
import com.ecollege.lunit.event.TestEvent;
import com.ecollege.lunit.event.TestObserver;
import com.ecollege.lunit.fixture.TestFixture;
import com.ecollege.lunit.test.Ownable;
import com.ecollege.lunit.test.TestCase;
import com.jigsforjava.exception.AssertionFailedError;
import com.jigsforjava.util.Copyable;
import com.jigsforjava.util.ObjectUtils;

/**
 * Represents a generic fine-grained load test, run as part of a TestCaseImpl.
 * 
 * @author toddf
 * @since Sep 12, 2008
 */
public abstract class Scenario<T extends Scenario<T>>
extends Thread
implements Copyable<T>, ObservableTest, Ownable<TestCase>
{
	// SECTION: CONSTANTS
	
    private static final String DEFAULT_NULL_MESSAGE = "Null encountered";

    protected static final Logger LOG = Logger.getLogger(Scenario.class);

	
	// SECTION: INSTANCE VARIABLES
    
    private TestCase owner;

	// True when the thread has been requested to stop (after it's done processing).
	private volatile boolean isStopped = false;
	
	// The test fixture for the scenario-level setup/teardown.
	private TestFixture fixture = null;
	
	// The fixture for each action setup/teardown.
	private TestFixture actionFixture = null;
	
	// A List of TestObservers to notify when TestEvents occur.
	private List<TestObserver> observers = new ArrayList<TestObserver>(0);
	
	// The number of actions executed.
	private int actionCount = 0;
	
	// The number of milliseconds to delay before proceeding to the next action.
	private long actionDelay = 0;

	private boolean shouldExitOnError = false;


	// SECTION: CONSTRUCTORS
	
	public Scenario()
	{
		super();
		setDaemon(false);
	}

	
	// SECTION: ACCESSORS/MUTATORS

	private void setIsStopped(boolean value)
	{
		this.isStopped = value;
	}

	public boolean isStopped()
    {
    	return isStopped;
    }
	
	public boolean isRunning()
	{
		return !isStopped();
	}
	
	public void setStopped()
	{
		isStopped = true;
	}

	public TestFixture getFixture()
    {
    	return fixture;
    }
	
	public boolean hasFixture()
	{
		return (getFixture() != null);
	}

	public void setFixture(TestFixture testFixture)
    {
    	this.fixture = testFixture;
    }

	public TestFixture getActionFixture()
	{
		return actionFixture;
	}

	public boolean hasActionFixture()
	{
		return (getActionFixture() != null);
	}

	public void setActionFixture(TestFixture iterationFixture)
	{
		this.actionFixture = iterationFixture;
	}

	public int getActionCount()
    {
    	return actionCount;
    }

	public void incrementActionCount()
    {
    	++actionCount;
    }
	
	public long getActionDelay()
	{
		return actionDelay;
	}
	
	public void setActionDelay(long millis)
	{
		this.actionDelay = millis;
	}
	
	@Override
	public TestCase getOwner()
	{
		return owner;
	}
	
	@Override
	public boolean hasOwner()
	{
		return (getOwner() != null);
	}
	
	public void setOwner(TestCase owner)
	{
		this.owner = owner;
	}
	
	public boolean shouldExitOnError()
	{
		return shouldExitOnError;
	}

	public void setShouldExitOnError(boolean value)
	{
		this.shouldExitOnError = value;
	}

	
	// SECTION: THREAD

	@Override
	public void run()
	{
		long startedAtMillis = System.currentTimeMillis();

		try
		{
			notifyOfScenarioSetup();
			performSetup();
			notifyOfScenarioStart();
			startedAtMillis = System.currentTimeMillis();
			execute();
		}
		catch(Exception e)
		{
			notifyOfError(e);
			LOG.fatal("Scenario error", e);
		}
		finally
		{
			notifyOfScenarioStop(startedAtMillis);

			try
			{
				notifyOfScenarioTeardown();
				performTeardown();
			}
			catch (Exception e)
			{
				LOG.fatal("Scenario error", e);
			}			
		}
	}

	protected void execute()
	throws Exception
	{
		long startedAtMillis;

		while (isRunning() && shouldContinue())
		{
			try
			{
				performActionSetup();
				notifyOfActionStart();
				startedAtMillis = System.currentTimeMillis();
				try
				{
					executeAction();
					notifyOfActionStop(startedAtMillis);
					incrementActionCount();
				}
				catch (Exception e)
				{
					if (shouldExitOnError())
					{
						throw e;
					}
					else
					{
						e.printStackTrace();
						notifyOfError(e);
					}
				}
			}
			finally
			{
				notifyOfActionTeardown();
				performActionTeardown();
			}

			delay();
		}
	}

	private void delay()
	{
		try
        {
	        Thread.sleep(getActionDelay());
        }
        catch (InterruptedException e)
        {
        	// no one cares if there's an exception here.
        }
	}

    private void performSetup()
    throws Exception
    {
    	if (hasFixture())
    	{
    		getFixture().setup();
    	}

    	beforeScenario();
    }

	private void performTeardown()
	throws Exception
    {
    	afterScenario();

    	if (hasFixture())
    	{
    		getFixture().teardown();
    	}
    }

	private void performActionSetup() throws Exception
	{
		notifyOfActionSetup();

		if (hasActionFixture())
		{
			getActionFixture().setup();
		}

		beforeAction();
	}

	private void performActionTeardown() throws Exception
	{
		afterAction();

		if (hasActionFixture())
		{
			getActionFixture().teardown();
		}
	}


	// SECTION: SUB-CLASS RESPONSIBILITIES

	/**
	 * Setup activities performed before the scenario.  Executed after the
	 * fixture setup() method (if present).
	 * @throws Exception 
	 */
	protected void beforeScenario()
	throws Exception
	{
		// no default behavior
	}
	
	/**
	 * Teardown activities performed after the scenario.  Executed before
	 * the fixture teardown() method (if present).
	 */
	protected void afterScenario()
	throws Exception
	{
		// no default behavior
	}

	/**
	 * Setup activities performed before each action.  Executed after the
	 * action fixture setup() method (if present).
	 */
	protected void beforeAction()
	throws Exception
	{
		// no default behavior
	}

	/**
	 * Teardown activities performed after each action.  Executed before
	 * the action fixture teardown() method (if present).
	 */
	protected void afterAction()
	throws Exception
	{
		// no default behavior
	}

	/**
	 * A predicate method that returns true if the load test should continue
	 * with another iteration.
	 * 
	 * @return true if the load test should continue with another iteration.  Otherwise false.
	 */
	protected abstract boolean shouldContinue();

	/**
	 * The actual testing code to occur with each iteration of this load test.
	 * 
	 * @throws Exception if an exception occurs.
	 */
	protected abstract void executeAction() throws Exception;
	
	protected abstract T newInstance();
	
	
	// SECTION: COPYABLE

	@Override
	public T copy()
	{
		T clone = newInstance();
		clone.setIsStopped(isStopped());
		clone.setFixture(getFixture());
		clone.setActionFixture(getActionFixture());
		return clone;
	}
	
	
	// SECTION: CONVENIENCE/UTILITY

	protected void assertNotNull(Object object)
	{
		assertNotNull(DEFAULT_NULL_MESSAGE, object);
	}
	
	protected void assertNotNull(String message, Object object)
	{
		if (object == null)
		{
			notifyOfFailure(message);
		}
	}

	protected void assertEquals(Object expected, Object actual)
	{
		assertEquals(null, expected, actual);
	}

	protected void assertEquals(String message, Object expected, Object actual)
	{
		if (!ObjectUtils.areEqual(expected, actual))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Expected [");
			sb.append(expected.toString());
			sb.append("] but was [");
			sb.append(actual.toString());
			sb.append("]");

			if (message != null)
			{
				sb.append(" ");
				sb.append(message);
			}

			notifyOfFailure(sb.toString());
		}
	}
	
	protected void assertTrue(boolean value)
	{
		assertTrue("Expected [true] but was [false]", value);
	}

	protected void assertTrue(String message, boolean value)
	{
		if (!value)
		{
			notifyOfFailure(message);
		}
	}
	
	// SECTION: NOTIFICATIONS

	@Override
    public void subscribe(TestObserver observer)
    {
		if (!observers.contains(observer))
		{
			observers.add(observer);
		}
    }

	@Override
    public void unsubscribe(TestObserver observer)
    {
		if (observers.contains(observer))
		{
			observers.remove(observer);
		}
    }

	private void notifyObservers(TestEvent event)
	{
		for (TestObserver testObserver : observers)
		{
			testObserver.notify(event);
		}
	}

	
	// SECTION: UTILITY - INTERNAL

	private void notifyOfError(Throwable t)
	{
		notifyObservers(new ErrorEvent(t, this));
	}

	private void notifyOfFailure(String message)
	{
		notifyObservers(new TestEvent(EventType.FAILURE, this));
		throw new AssertionFailedError(message);
	}

	private void notifyOfActionSetup()
	{
		notifyObservers(new TestEvent(EventType.ACTION_SETUP, this));
	}
	
	private void notifyOfActionTeardown()
	{
		notifyObservers(new TestEvent(EventType.ACTION_TEARDOWN, this));
	}

	private void notifyOfActionStart()
	{
		notifyObservers(new TestEvent(EventType.ACTION_START, this));
	}
	
	private void notifyOfActionStop(long startedAtMillis)
	{
		notifyObservers(new ActionStopEvent(this, startedAtMillis));
	}

	private void notifyOfScenarioSetup()
	{
		notifyObservers(new TestEvent(EventType.SCENARIO_SETUP, this));
	}
	
	private void notifyOfScenarioTeardown()
	{
		notifyObservers(new TestEvent(EventType.SCENARIO_TEARDOWN, this));
	}

	private void notifyOfScenarioStart()
	{	
		notifyObservers(new TestEvent(EventType.SCENARIO_START, this));
	}
	
	private void notifyOfScenarioStop(long startedAtMillis)
	{
		notifyObservers(new ScenarioStopEvent(this, startedAtMillis));
	}
	
	
	// SECTION: CONVENIENCE HELPER METHODS (FOR SUB-CLASSES)

	protected void notifyOfLatencyStart()
	{
		notifyObservers(new TestEvent(EventType.LATENCY_TIMING_START, this));
	}
	
	protected void notifyOfLatencyStop(long startedAtMillis)
	{
		notifyObservers(new LatencyTimingStopEvent(this, startedAtMillis));
	}
}
