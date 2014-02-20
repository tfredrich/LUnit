/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.validation;

import com.ecollege.lunit.summary.TestSummary;

/**
 * @author toddf
 * @since Sep 12, 2008
 */
public interface TestResultValidator
{
	public void validate(TestSummary testResult);
}
