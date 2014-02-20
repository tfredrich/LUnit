/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.ecollege.lunit.event.TestObserver;
import com.ecollege.lunit.fixture.TestFixture;
import com.ecollege.lunit.scenario.DynamicScenarioBuilder;
import com.ecollege.lunit.scenario.Scenario;
import com.ecollege.lunit.scenario.option.OptionChooser;

/**
 * Uses a prototype Scenario to create a TestCase dynamically (at run time).  This provides the ability to create
 * varying scenarios using Options and Choosers to create dynamic, user-load-like usage scenarios without hard coding
 * them.
 * 
 * @author toddf
 * @since Sep 16, 2008
 */
public class TestCasePrototype
implements TestCase
{
	// SECTION: INSTANCE VARIABLES

	private String name;
	private Scenario<?> prototype;
	private DynamicScenarioBuilder builder;
	private int scenarioCount = 1;
	private OptionChooser<?> scenarioCountChooser;
	private TestFixture fixture;
	private long initialDelay = 0L;
	
	// A List of TestObservers to notify when TestEvents occur.
	private Vector<TestObserver> observers = new Vector<TestObserver>(0);

	
	// SECTION: CONSTRUCTOR
	
	public TestCasePrototype()
	{
		super();
	}

	public TestCasePrototype(String name)
	{
		super();
		setName(name);
	}

	
	// SECTION: ACCESSORS/MUTATORS

	public TestFixture getFixture()
    {
    	return fixture;
    }

	public void setFixture(TestFixture fixture)
    {
    	this.fixture = fixture;
    }

	public int getScenarioCount()
    {
    	return scenarioCount;
    }

	public void setScenarioCount(int value)
    {
    	this.scenarioCount = value;
    }

	public String getName()
    {
    	return name;
    }

	public void setName(String name)
    {
    	this.name = name;
    }

	public Scenario<?> getPrototype()
    {
    	return prototype;
    }

	public void setPrototype(Scenario<?> prototype)
    {
    	this.prototype = prototype;
    }

	public OptionChooser<?> getScenarioCountChooser()
    {
    	return scenarioCountChooser;
    }
	
	public boolean hasScenarioCountChooser()
	{
		return (getScenarioCountChooser() != null);
	}

	public void setScenarioCountChooser(OptionChooser<?> scenarioCountChooser)
    {
    	this.scenarioCountChooser = scenarioCountChooser;
    }

	public DynamicScenarioBuilder getBuilder()
    {
    	return builder;
    }
	
	public boolean hasBuilder()
	{
		return (getBuilder() != null);
	}

	public void setBuilder(DynamicScenarioBuilder builder)
    {
    	this.builder = builder;
    }
	
	public long getInitialDelay()
	{
		return initialDelay;
	}
	
	public void setInitialDelay(long millis)
	{
		this.initialDelay = millis;
	}
	
	
	// SECTION: OBSERVERS	

	public List<TestObserver> getObservers()
	{
		return Collections.unmodifiableList(observers);
	}
	
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

	
	// SECTION: PROTOTYPE

	public TestCaseImpl asTestCase()
	throws Exception
	{
		TestCaseImpl tc = new TestCaseImpl(getName());
		List<Scenario<?>> scenarios = createScenarios(getScenarioCountInternal(), tc);
		tc.setScenarios(scenarios);
		tc.setFixture(getFixture());
		tc.setName(getName());
		tc.setObservers(getObservers());
		tc.setInitialDelay(getInitialDelay());
		return tc;
	}
	
	
	// SECTION: TEST CASE
	
	@Override
	public void execute()
	throws Exception
	{
		asTestCase().execute();
	}
	

	// SECTION: UTILITY - PRIVATE
	
	private List<Scenario<?>> createScenarios(int count, TestCase owner)
	throws Exception
	{
		List<Scenario<?>> tests = new ArrayList<Scenario<?>>(count);
		Scenario<?> scenario;

		for (int i = 0; i < count; ++i)
		{
			if (hasBuilder())
			{
				scenario = getBuilder().build();
			}
			else
			{
				scenario = getPrototype().copy();
			}

			scenario.setOwner(owner);
			tests.add(scenario);
		}

		return tests;
	}
	
	private int getScenarioCountInternal()
	{
		int result = getScenarioCount();

		if (hasScenarioCountChooser())
		{
			result = (Integer.valueOf((String) getScenarioCountChooser().choose()));
		}
		
		return result;
	}
}
