/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

import org.apache.log4j.Logger;

/**
 * Provides minimal logging capability, via Log4J.  Simply logs error, failure and message events.
 * Other event types are ignored.
 * 
 * @author toddf
 * @since Nov 11, 2008
 */
public class LoggingTestObserver
extends BaseTestObserver
{
	// SECTION: CONSTANTS
	
    protected static final Logger LOG = Logger.getLogger(LoggingTestObserver.class);
	
	
	// SECTION: OBSERVER NOTIFICATION
	
	@Override
	protected void handleError(ErrorEvent event)
	{
		LOG.error("Load test error", event.getThrowable());
	}
	
	@Override
	protected void handleFailure(FailureEvent event)
	{
		LOG.error("Assertion failure: " + event.getMessage());
	}
	
	@Override
	protected void handleMessage(MessageEvent event)
	{
		LOG.info(event.getMessage());
	}
}
