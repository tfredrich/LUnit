/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.fixture;

import com.ecollege.lunit.command.CommandException;
import com.ecollege.lunit.command.ExternalCommand;

/**
 * Executes a command as if from the command line. However, note that since this
 * class utilizes Runtime.exec(), redirects, etc. (e.g. cat > test.out) will not
 * work. ExternalCommandFixture executes a single executable program or script.
 * If redirect is desired, simply create a script that does redirection
 * internally.
 * 
 * @author toddf
 * @since Sep 15, 2008
 */
public class ExternalCommandFixture
implements TestFixture
{
	// SECTION: INSTANCE VARIABLES

	private ExternalCommand setupCommand;
	private ExternalCommand teardownCommand;


	// SECTION: CONSTRUCTOR

	public ExternalCommandFixture()
	{
		super();
		setupCommand = new ExternalCommand();
		teardownCommand = new ExternalCommand();
	}


	// SECTION: ACCESSORS/MUTATORS

	public String getSetupCommand()
	{
		return setupCommand.getCommand();
	}

	public boolean hasSetupCommand()
	{
		return setupCommand.hasCommand();
	}

	public void setSetupCommand(String command)
	{
		setupCommand.setCommand(command);
	}

	public boolean shouldValidateExitValue()
	{
		return (setupCommand.shouldValidateExitValue() || teardownCommand
		    .shouldValidateExitValue());
	}

	public void setValidateExitValue(boolean value)
	{
		setupCommand.setValidateExitValue(value);
		teardownCommand.setValidateExitValue(value);
	}

	public String getTeardownCommand()
	{
		return teardownCommand.getCommand();
	}

	public boolean hasTeardownCommand()
	{
		return teardownCommand.hasCommand();
	}

	public void setTeardownCommand(String command)
	{
		teardownCommand.setCommand(command);
	}


	// SECTION: TEST FIXTURE

	@Override
	public void setup() throws Exception
	{
		if (hasSetupCommand())
		{
			try
			{
				setupCommand.execute();
			}
			catch (CommandException e)
			{
				throw new FixtureException("Setup Failed: " + getSetupCommand(), e);
			}
		}
	}

	@Override
	public void teardown() throws Exception
	{
		if (hasTeardownCommand())
		{
			try
			{
				teardownCommand.execute();
			}
			catch (CommandException e)
			{
				throw new FixtureException("Teardown Failed: " + getTeardownCommand(), e);
			}
		}
	}
}
