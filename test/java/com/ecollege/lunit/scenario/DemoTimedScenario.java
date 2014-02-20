/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.scenario;

import com.ecollege.lunit.scenario.TimeConstrainedScenario;


/**
 * @author toddf
 * @since Sep 12, 2008
 */
public class DemoTimedScenario
extends TimeConstrainedScenario<DemoTimedScenario>
{
	private boolean wasExecuted = false;

	public DemoTimedScenario()
	{
		super();
	}
	
	public DemoTimedScenario(long millis)
	{
		super(millis);
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
    protected DemoTimedScenario newInstance()
    {
		return new DemoTimedScenario();
    }
}
