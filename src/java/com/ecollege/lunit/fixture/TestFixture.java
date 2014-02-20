/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.fixture;

/**
 * A TestFixture is responsible for setting up (before) and tearing down (after) a test.
 * Note that at least in a TestCasePrototype, TestFixture instances must be thread safe
 * as TestFixture instances are not replicated--each scenario utilizes the same instance
 * of a TestFixture.
 * 
 * @author toddf
 * @since Sep 12, 2008
 */
public interface TestFixture
{
	public void setup()
	throws Exception;
	
	public void teardown()
	throws Exception;
}
