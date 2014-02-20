/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

/**
 * Abstract interface for an object that selects a value from the given Options instance.
 * 
 * @author toddf
 * @since Nov 19, 2008
 */
public interface Chooser<T>
{
	public T choose(Options<T> options);
}
