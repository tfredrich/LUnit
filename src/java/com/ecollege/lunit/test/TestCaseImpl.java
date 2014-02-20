/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ecollege.lunit.event.EventType;
import com.ecollege.lunit.event.TestEvent;
import com.ecollege.lunit.event.TestObserver;
import com.ecollege.lunit.event.TestStopEvent;
import com.ecollege.lunit.fixture.FixtureException;
import com.ecollege.lunit.fixture.TestFixture;
import com.ecollege.lunit.scenario.Scenario;

/**
 * A TestCaseImpl runs a collection of Scenario instances.  This is a course-grained test
 * that aggregates finer-grained load tests.  A TestCaseImpl utilizes TestFixture instances
 * to configure (setup) and deconfigure (teardown) before the test cases (scenarios) execute.
 * 
 * @author toddf
 * @since Sep 12, 2008
 */
public class TestCaseImpl
implements TestCase
{
	// SECTION: INSTANCE VARIABLES

	/**
	 * A name describing the test case.
	 */
	private String name;
	
	/**
	 * A list of Scenario instances to execute during this TestCaseImpl.
	 */
	private List<Scenario<?>> scenarios = new ArrayList<Scenario<?>>();
	
	/**
	 * A reusable TestFixture that performs setup and teardown activities
	 * before the scenarios are run.  Fixture setup occurs after TestCaseImpl.setup()
	 * and before TestCaseImpl.teardown().
	 */
	private TestFixture fixture = null;

	// A List of TestObservers to notify when TestEvents occur.
	private Vector<TestObserver> observers = new Vector<TestObserver>(0);
	
	// The number of milliseconds to wait before starting the next scenario.
	private long initialDelay = 0L;


	// SECTION: CONSTRUCTORS

	public TestCaseImpl(String name)
	{
		super();
	}

	
	// SECTION: ACCESSORS/MUTATORS

	public TestFixture getFixture()
	{
		return fixture;
	}

	public boolean hasFixture()
	{
		return (fixture != null);
	}

	public void setFixture(TestFixture fixture)
	{
		this.fixture = fixture;
	}

	public long getInitialDelay()
	{
		return initialDelay;
	}
	
	public void setInitialDelay(long millis)
	{
		this.initialDelay = millis;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Scenario<?>> getScenarios()
	{
		return scenarios;
	}

	public void setScenarios(List<Scenario<?>> scenarios)
	{
		this.scenarios = scenarios;
	}


	// SECTION: SETUP/TEARDOWN

	/**
	 * TestCaseImpl class-specific setup logic to execute before all LoadTests are run.
	 * Executes before TestFixture.setup().
	 */
	public void setup()
	{
		// no default behavior
	}

	/**
	 * TestCaseImpl class-specific teardown logic to execute after all LoadTests are run.
	 * Executes after TestFixture.setup().
	 */
	public void teardown()
	{
		// no default behavior
	}


	// SECTION: INTERNAL BEHAVIOR

	@Override
	public void execute()
	throws Exception
	{
		performSetup();
		notifyObservers(new TestEvent(EventType.TEST_START, this));
		long startedAt = System.currentTimeMillis();
		startScenarios(getScenarios());
		waitforScenarios(getScenarios());
		notifyObservers(new TestStopEvent(this, startedAt));
		performTeardown();
	}
	
	
	// SECTION: OBSERVERS

	public void setObservers(List<TestObserver> observers)
	{
		this.observers.clear();
		
		for (TestObserver observer : observers)
		{
			subscribe(observer);
		}
	}

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


	// SECTION: INTERNAL BEHAVIOR

	/**
	 * @throws FixtureException
	 */
	private void performSetup()
	throws Exception
	{
		notifyObservers(new TestEvent(EventType.TEST_SETUP, this));
		setup();

		if (hasFixture())
		{
			fixture.setup();
		}
	}

	private void startScenarios(List<Scenario<?>> scenarios)
	{
		for (Scenario<?> scenario : scenarios)
		{
			scenario.start();
			delay(getInitialDelay());
		}
	}

	private void waitforScenarios(List<Scenario<?>> scenarios)
	throws InterruptedException
	{
		for (Scenario<?> scenario : scenarios)
		{
			scenario.join();
		}
	}

	/**
	 * @throws FixtureException
	 */
	private void performTeardown()
	throws Exception
	{
		notifyObservers(new TestEvent(EventType.TEST_TEARDOWN, this));

		if (hasFixture())
		{
			fixture.teardown();
		}

		teardown();
	}

	private void delay(long millis)
	{
		try
        {
	        Thread.sleep(millis);
        }
        catch (InterruptedException e)
        {
        	// no one cares if there's an exception here.
        }
	}
}
