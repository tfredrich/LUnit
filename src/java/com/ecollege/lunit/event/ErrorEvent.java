/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

import java.util.Map;

/**
 * @author toddf
 * @since Nov 11, 2008
 */
public class ErrorEvent
extends TestEvent
{
	// SECTION: INSTANCE VARIABLES

	private Throwable throwable;

	
	// SECTION: CONSTRUCTOR

    public ErrorEvent(Throwable throwable, Object originator)
    {
	    super(EventType.ERROR, originator);
	    setThrowable(throwable);
    }

    
    // SECTION: ACCESSORS/MUTATORS

	public Throwable getThrowable()
    {
    	return throwable;
    }

	private void setThrowable(Throwable throwable)
    {
    	this.throwable = throwable;
    }
	
	@Override
	public void getValuesInto(Map<String, String> values)
	{
		super.getValuesInto(values);
		values.put("throwable", getThrowable().getStackTrace().toString());
		values.put("message", getThrowable().getMessage());
	}
}
