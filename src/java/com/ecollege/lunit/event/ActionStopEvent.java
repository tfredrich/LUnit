/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;


/**
 * @author toddf
 * @since Nov 24, 2008
 */
public class ActionStopEvent
extends TimerStopEvent
{
	/**
     * @param originator
     * @param startedAtMillis
     */
    public ActionStopEvent(Object originator, long startedAtMillis)
    {
	    super(EventType.ACTION_STOP, originator, startedAtMillis);
    }
}
