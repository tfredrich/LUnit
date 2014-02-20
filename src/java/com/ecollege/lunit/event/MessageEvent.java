/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

import java.util.Map;

/**
 * @author toddf
 * @since Nov 11, 2008
 */
public class MessageEvent
extends TestEvent
{
	// SECTION: INSTANCE VARIABLES

	private String message;


	// SECTION: CONSTRUCTORS

	public MessageEvent(String message, Object originator)
	{
		this(EventType.MESSAGE, message, originator);
	}

	protected MessageEvent(EventType type, String message, Object originator)
	{
		super(type, originator);
		setMessage(message);
	}


	// SECTION: ACCESSORS/MUTATORS

	public String getMessage()
	{
		return message;
	}

	private void setMessage(String message)
	{
		this.message = message;
	}
	
	@Override
	public void getValuesInto(Map<String, String> values)
	{
		super.getValuesInto(values);
		values.put("message", getMessage());
	}
}
