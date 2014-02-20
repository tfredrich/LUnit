/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.test;

/**
 * Executes each TestCase in sequential order then finishes.
 * 
 * @author toddf
 * @since Sep 12, 2008
 */
public class SequentialTestRunner
extends TestRunner
{
	public void executeTestCases()
	throws Exception
	{
		for (TestCase testCase : getTestCases())
		{
			executeTestCase(testCase);
		}
	}
}
