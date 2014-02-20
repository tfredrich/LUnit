/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import com.jigsforjava.util.Range;

/**
 * @author toddf
 * @since Nov 20, 2008
 */
public abstract class TimerStopEvent
extends TestEvent
{
	// SECTION: INSTANCE VARIABLES

	private long startedAtMillis;

	
	// SECTION: CONSTRUCTORS

	/**
     * @param type
     * @param originator
     * @param startedAtMillis
     */
    public TimerStopEvent(EventType type, Object originator, long startedAtMillis)
    {
	    super(type, originator);
	    setStartedAt(startedAtMillis);
    }


    // SECTION: ACCESSORS/MUTATORS

	public Range<Date> getTimespan()
    {
    	return new Range<Date>(new Date(getStartedAtMillis()), getOccurredAt());
    }

	public long getStartedAtMillis()
	{
		return startedAtMillis;
	}

	private void setStartedAt(long millis)
    {
    	this.startedAtMillis = millis;
    }
	
	public long getStoppedAtMillis()
	{
		return getOccurredAtInternal().getTime();
	}
	
	@Override
	public void getValuesInto(Map<String, String> values)
	{
		super.getValuesInto(values);
		values.put("startedAt", new Timestamp(getOccurredAt().getTime()).toString());
	}
}
