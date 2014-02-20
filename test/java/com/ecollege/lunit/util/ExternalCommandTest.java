/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.util;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ecollege.lunit.command.CommandException;
import com.ecollege.lunit.command.ExternalCommand;

/**
 * @author toddf
 * @since Oct 14, 2008
 */
public class ExternalCommandTest
{
	// SECTION: CONSTANTS

    private static final String INVALID_COMMAND_STRING = "XSd^%$#";
    private static final String VALID_WINDOWS_COMMAND_STRING = "cmd /c dir";
    private static final String VALID_LINUX_COMMAND_STRING = "/bin/sh -c ls";

    
    // SECTION: VARIABLES

    private static ExternalCommand command;
	
	@BeforeClass
	public static void configure()
	{
		command = new ExternalCommand();
		
		if (OSUtil.isWindows())
		{
			command.setCommand(VALID_WINDOWS_COMMAND_STRING);
		}
		else
		{
			command.setCommand(VALID_LINUX_COMMAND_STRING);
		}
		
		command.setValidateExitValue(true);
	}

	@Test
	public void shouldExecuteWithoutFailure()
	throws Exception
	{
		command.execute();
	}

	@Test
	public void shouldThrowCommandExceptionOnInvalidCommand()
	throws Exception
	{
		command.setCommand(INVALID_COMMAND_STRING);
		
		try
		{
			command.execute();
		}
		catch (CommandException e)
		{
			// do nothing--expected behavior.
		}
	}
}
