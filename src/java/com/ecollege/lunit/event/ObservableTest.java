/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.event;


/**
 * @author toddf
 * @since Nov 11, 2008
 */
public interface ObservableTest
{
	public void subscribe(TestObserver observer);
	public void unsubscribe(TestObserver observer);
}
