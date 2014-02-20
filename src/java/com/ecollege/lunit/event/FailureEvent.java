/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

/**
 * @author toddf
 * @since Nov 19, 2008
 */
public class FailureEvent
extends MessageEvent
{
	// SECTION: CONSTRUCTOR

    public FailureEvent(String message, Object originator)
    {
	    super(EventType.FAILURE, message, originator);
    }
}
