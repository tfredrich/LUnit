/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;


/**
 * @author toddf
 * @since Nov 24, 2008
 */
public class TestStopEvent
extends TimerStopEvent
{
	/**
     * @param originator
     * @param startedAtMillis
     */
    public TestStopEvent(Object originator, long startedAtMillis)
    {
	    super(EventType.TEST_STOP, originator, startedAtMillis);
    }
}
