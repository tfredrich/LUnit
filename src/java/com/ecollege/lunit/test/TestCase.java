/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.test;

import com.ecollege.lunit.event.ObservableTest;
import com.ecollege.lunit.util.Executable;

/**
 * @author toddf
 * @since Sep 16, 2008
 */
public interface TestCase
extends Executable, ObservableTest
{
	public String getName();
}