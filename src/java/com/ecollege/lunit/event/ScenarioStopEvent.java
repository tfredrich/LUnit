/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;


/**
 * @author toddf
 * @since Sept. 28, 2009
 */
public class ScenarioStopEvent
extends TimerStopEvent
{
	/**
     * @param originator
     * @param startedAtMillis
     */
    public ScenarioStopEvent(Object originator, long startedAtMillis)
    {
	    super(EventType.SCENARIO_STOP, originator, startedAtMillis);
    }
}
