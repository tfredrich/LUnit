/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

/**
 * @author toddf
 * @since Nov 10, 2008
 */
public enum EventType
{
	ERROR,
	FAILURE,
	ACTION_START,
	ACTION_STOP,
	ACTION_SETUP,
	ACTION_TEARDOWN,
	SCENARIO_START,
	SCENARIO_STOP,
	SCENARIO_SETUP,
	SCENARIO_TEARDOWN,
	TEST_START,
	TEST_STOP,
	TEST_SETUP,
	TEST_TEARDOWN,
	LATENCY_TIMING_START,
	LATENCY_TIMING_STOP,
	MESSAGE,
	OTHER
}
