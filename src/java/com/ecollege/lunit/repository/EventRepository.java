/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.repository;

import com.ecollege.lunit.event.EventCallback;
import com.ecollege.lunit.event.TestEvent;

/**
 * @author toddf
 * @since Nov 19, 2008
 */
public interface EventRepository
{
	public void insert(TestEvent event)
	throws RepositoryException;
	
	public void iterate(EventCallback callback);
}
