/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.command;

import java.io.IOException;

import com.ecollege.lunit.util.Executable;
import com.jigsforjava.string.StringUtils;

/**
 * Wraps an external command-line.  Runs an external program or utility.  Note that if you want to run
 * a shell command, the shell must be initiated (e.g. 'cmd /c dir' for windows, or '/bin/bash -c ls' for *nix).
 * The command is executed asynchronously, with input and output ignored.  Only the return value of the command
 * is inspected (if shouldValidateExitValue is set to true--the default is false).
 * 
 * <p/>ExternalCommand is primarily designed for such tasks as running setup/teardown scripts, such as deploying
 * software to virtual servers before running tests, etc.
 * 
 * @author toddf
 * @since Oct 14, 2008
 */
public class ExternalCommand
implements Executable
{
	// SECTION: VARIABLES

	private String command;
	private boolean shouldValidateExitValue = false;


	// SECTION: CONSTRUCTORS

	/**
	 * Create an empty ExternalCommand instance.
	 */
	public ExternalCommand()
	{
		super();
	}
	
	/**
	 * @param command
	 */
	public ExternalCommand(String command)
	{
		this(command, false);
	}

	/**
	 * @param command
	 * @param shouldValidateExitValue
	 */
	public ExternalCommand(String command, boolean shouldValidateExitValue)
	{
		this();
		setCommand(command);
		setValidateExitValue(shouldValidateExitValue);
	}

	
	// SECTION: ACCESSORS/MUTATORS

	/**
	 * Return the previously-set external command string.
	 * 
	 * @return the command
	 */
	public String getCommand()
	{
		return command;
	}

	/**
	 * Returns true if the external command has been set.
	 * 
	 * @return whether the external command has been set.
	 */
	public boolean hasCommand()
	{
		return StringUtils.isNotNullNorEmpty(getCommand());
	}

	/**
	 * Set the external command string to execute.  Note that if you want to run a shell
	 * command, the shell must be initiated (e.g. 'cmd /c dir' for windows, or '/bin/bash -c ls' for *nix).
	 * 
	 * @param command the external command string.
	 */
	public void setCommand(String command)
	{
		this.command = command;
	}

	/**
	 * Returns whether we care about the exit value of the external command.
	 * 
	 * @return the shouldValidateExitValue
	 */
	public boolean shouldValidateExitValue()
	{
		return shouldValidateExitValue;
	}

	/**
	 * Set whether we care or not about the exit value of the external command.
	 * 
	 * @param shouldValidateExitValue the shouldValidateExitValue to set
	 */
	public void setValidateExitValue(boolean shouldValidateExitValue)
	{
		this.shouldValidateExitValue = shouldValidateExitValue;
	}


	// SECTION: EXECUTABLE INTERFACE

	/**
	 * Executes the external command.  If shouldValidateExitValue is true and
	 * the exit value is non-zero, the command is assumed to have failed and
	 * a CommandException is thrown.  A CommandException is also thrown if any
	 * other exception occurs, with the cause set to the underlying exception.
	 */
	@Override
	public void execute()
	throws Exception
	{
		if (hasCommand())
		{
			try
			{
				int exitValue = executeCommand(getCommand());
	
				if (shouldValidateExitValue() && exitValue != 0)
				{
					throw new CommandException("Command Failed: " + getCommand());
				}
			}
			catch (Exception e)
			{
				throw new CommandException("Command Failed: " + getCommand(), e);
			}
		}
	}


	// SECTION: UTILITY - PRIVATE

	/**
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private int executeCommand(String command)
	throws IOException, InterruptedException
	{
		Process process = Runtime.getRuntime().exec(command);
		CommandStreamReader errStreamReader = new CommandStreamReader(process.getErrorStream(), "ERROR");
		CommandStreamReader outStreamReader = new CommandStreamReader(process.getInputStream(), "OUTPUT");

		errStreamReader.start();
		outStreamReader.start();

		int exitValue = process.waitFor();
		return exitValue;
	}

}
