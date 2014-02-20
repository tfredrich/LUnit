/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;

/**
 * @author toddf
 * @since Nov 10, 2008
 */
public interface TestObserver
{
	public void notify(TestEvent event);
}
