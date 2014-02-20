/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.scenario;

import com.ecollege.lunit.scenario.IterationConstrainedScenario;


/**
 * @author toddf
 * @since Sep 12, 2008
 */
public class DemoIterationScenario
extends IterationConstrainedScenario<DemoIterationScenario>
{
	private boolean wasExecuted = false;

	public DemoIterationScenario()
	{
		super();
	}

	public DemoIterationScenario(int max)
	{
		super(max);
	}

	@Override
	protected void executeAction() throws Exception
	{
		wasExecuted = true;
	}
	
	public boolean wasExecuted()
	{
		return wasExecuted;
	}

	// SECTION: FACTORY METHOD

	@Override
    protected DemoIterationScenario newInstance()
    {
	    return new DemoIterationScenario();
    }
}
