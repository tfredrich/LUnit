/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.validation;

import com.ecollege.lunit.summary.TestSummary;

/**
 * @author toddf
 * @since Sep 17, 2008
 */
public class MinimumExecutionsPerSecondValidator
implements TestResultValidator
{
	private int threshold;

	public void setThreshold(int minExecutionsPerSecond)
    {
    	this.threshold = minExecutionsPerSecond;
    }

	/* (non-Javadoc)
	 * @see com.ecollege.lunit.validation.TestResultValidator#validate(com.ecollege.lunit.result.TestResult)
	 */
	@Override
	public void validate(TestSummary ltr)
	{
		if (((int) ltr.computeActionsPerSecond()) < threshold)
		{
			ltr.addValidationMessage("Executions per second of "
					+ ((int) ltr.computeActionsPerSecond())
					+ " is less than target of " + threshold + ".");
		}
	}

}
