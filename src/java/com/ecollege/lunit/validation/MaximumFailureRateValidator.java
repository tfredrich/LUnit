/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.validation;

import com.ecollege.lunit.summary.TestSummary;

/**
 * @author toddf
 * @since Sep 17, 2008
 */
public class MaximumFailureRateValidator
implements TestResultValidator
{
	private double percent;

	public void setPercent(double percent)
	{
		this.percent = percent;
	}

	public void validate(TestSummary ltr)
	{
		double failureRate = (double) ((100.0 * ltr.getFailureCount()) / ((double) ltr.getActionCount()));

		if (failureRate > percent)
		{
			ltr.addValidationMessage("Failure rate of " + failureRate
			    + " is greater than target of " + percent + ".");
		}
	}
}
