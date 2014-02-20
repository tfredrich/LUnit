/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;


/**
 * @author toddf
 * @since Nov 24, 2008
 */
public class LatencyTimingStopEvent
extends TimerStopEvent
{
	/**
     * @param originator
     * @param startMillis
     */
    public LatencyTimingStopEvent(Object originator, long startMillis)
    {
	    super(EventType.LATENCY_TIMING_STOP, originator, startMillis);
    }
}
