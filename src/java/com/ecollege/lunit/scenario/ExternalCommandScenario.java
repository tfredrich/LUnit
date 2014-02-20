/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario;

import com.ecollege.lunit.command.ExternalCommand;

/**
 * @author toddf
 * @since Oct 14, 2008
 */
public class ExternalCommandScenario
extends Scenario<ExternalCommandScenario>
{
	// SECTION: VARIABLES

	private ExternalCommand command;
	private boolean shouldContinue = true;

	
	// SECTION: CONSTRUCTORS

	public ExternalCommandScenario()
	{
		super();
		this.command = new ExternalCommand();
	}
	
	public ExternalCommandScenario(String command, boolean shouldValidateExitValue)
	{
		this();
		setCommand(command);
		setValidateExitValue(shouldValidateExitValue);
	}

	
	// SECTION: COPY
	
	@Override
	public ExternalCommandScenario copy()
	{
		ExternalCommandScenario copy = super.copy();
		copy.setCommand(getCommand());
		return copy;
	}


	// SECTION: ACCESSORS/MUTATORS

	/**
     * @return the command
     */
    public String getCommand()
    {
    	return command.getCommand();
    }

	/**
     * @param command the command to set
     */
    public void setCommand(String command)
    {
    	this.command.setCommand(command);
    }
    
    public boolean shouldValidateExitValue()
    {
    	return command.shouldValidateExitValue();
    }
    
    public void setValidateExitValue(boolean value)
    {
    	command.setValidateExitValue(value);
    }

	@Override
	protected void executeAction()
	throws Exception
	{
		command.execute();
	}

	@Override
	protected ExternalCommandScenario newInstance()
	{
		return new ExternalCommandScenario();
	}

	@Override
	protected boolean shouldContinue()
	{
		boolean result = shouldContinue;
		shouldContinue = false;
		return result;
	}
}
