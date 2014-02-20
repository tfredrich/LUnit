/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author toddf
 * @since Nov 10, 2008
 */
public class TestEvent
{
	// SECTION: INSTANCE VARIABLES

	private Object originator;
	private EventType eventType;
	private Date occurredAt;

	
	// SECTION: CONSTRUCTOR

	public TestEvent (EventType type, Object originator)
	{
		super();
		setType(type);
		setOriginator(originator);
		setOccurredAt(new Date());
	}

	
	// SECTION: ACCESSORS/MUTATORS

	private void setType(EventType type)
    {
	    this.eventType = type;
    }

	public EventType getType()
    {
	    return eventType;
    }

	public Object getOriginator()
    {
    	return originator;
    }

	private void setOriginator(Object originator)
    {
    	this.originator = originator;
    }
	
	public Date getOccurredAt()
    {
    	return new Date(getOccurredAtInternal().getTime());
    }
	
	protected Date getOccurredAtInternal()
	{
		return occurredAt;
	}

	private void setOccurredAt(Date occurredAt)
    {
    	this.occurredAt = new Date(occurredAt.getTime());
    }
	
	public String getOriginatorName()
	{
		return getOriginator().getClass().getName();
	}
	
	public void getValuesInto(Map<String, String> values)
	{
		values.put("eventType", getType().name());
		values.put("originator", getOriginator().getClass().getName());
		values.put("occurredAt", new Timestamp(getOccurredAt().getTime()).toString());
	}
	
	
	// SECTION: TESTING

	public boolean isError()
	{
		return (EventType.ERROR.equals(getType()));
	}
	
	public boolean isFailure()
	{
		return (EventType.FAILURE.equals(getType()));
	}
	
	public boolean isActionSetup()
	{
		return (EventType.ACTION_SETUP.equals(getType()));
	}
	
	public boolean isActionStart()
	{
		return (EventType.ACTION_START.equals(getType()));
	}
	
	public boolean isActionStop()
	{
		return (EventType.ACTION_STOP.equals(getType()));
	}
	
	public boolean isActionTeardown()
	{
		return (EventType.ACTION_TEARDOWN.equals(getType()));
	}
	
	public boolean isScenarioSetup()
	{
		return (EventType.SCENARIO_SETUP.equals(getType()));
	}
	
	public boolean isScenarioStart()
	{
		return (EventType.SCENARIO_START.equals(getType()));
	}
	
	public boolean isScenarioStop()
	{
		return (EventType.SCENARIO_STOP.equals(getType()));
	}
	
	public boolean isScenarioTeardown()
	{
		return (EventType.SCENARIO_TEARDOWN.equals(getType()));
	}

	public boolean isMessage()
	{
		return (EventType.MESSAGE.equals(getType()));
	}
	
	public boolean isOther()
	{
		return (EventType.OTHER.equals(getType()));
	}
	
	public boolean isTestSetup()
	{
		return (EventType.TEST_SETUP.equals(getType()));
	}
	
	public boolean isTestStart()
	{
		return (EventType.TEST_START.equals(getType()));
	}
	
	public boolean isTestStop()
	{
		return (EventType.TEST_STOP.equals(getType()));
	}
	
	public boolean isTestTeardown()
	{
		return (EventType.TEST_TEARDOWN.equals(getType()));
	}
	
	public boolean isLatencyTimingStart()
	{
		return (EventType.LATENCY_TIMING_START.equals(getType()));
	}
	
	public boolean isLatencyTimingStop()
	{
		return (EventType.LATENCY_TIMING_STOP.equals(getType()));
	}
}
