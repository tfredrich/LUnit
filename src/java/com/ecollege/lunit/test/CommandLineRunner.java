/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.test;

import java.io.FileNotFoundException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jigsforjava.exception.CommandLineException;
import com.jigsforjava.util.CommandLine;

/**
 * Runs a TestRunner/TestCase/Scenario set from the command line, given an XML Spring context
 * file name, which may be on the classpath or a path on the file system.
 * 
 * @author toddf
 * @since Sep 16, 2008
 */
public class CommandLineRunner
{
	private static final String ARG_PATTERN = "hc~";
	private static final String TEST_RUNNER_BEAN_NAME = "testRunner";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			CommandLine commandLine = new CommandLine(ARG_PATTERN).parse(args);

			if (commandLine.isOptionSet('h'))
			{
				throw new CommandLineException("Help requested.");
			}
			if (!commandLine.hasOptionArgumentFor('c'))
			{
				throw new CommandLineException("Context file name required");
			}
			
			ApplicationContext applicationContext = getApplicationContext(commandLine.getOptionArgument('c'));
			TestRunner runner = (TestRunner) applicationContext.getBean(TEST_RUNNER_BEAN_NAME);
			runner.execute();
//			runner.summarize().toString();
		}
		catch (CommandLineException e)
		{
			printUsage(e);
		}
		catch (Exception e)
		{
			printFailure(e);
		}
	}
	
	private static ApplicationContext getApplicationContext(String contextName)
	{
		ApplicationContext context;
		
		try
		{
			context = new ClassPathXmlApplicationContext(contextName);
		}
		catch (BeansException e)
		{
			if (e.getCause() instanceof FileNotFoundException)
			{
				context = new FileSystemXmlApplicationContext(contextName);
			}
			else
			{
				throw e;
			}
		}
		
		return context;
	}

	private static void printUsage(CommandLineException e)
	{
		System.out.println(e.getMessage());
		System.out.println("Usage: java -jar LUnit.jar  [-h] | -c context");
		System.out.println("where:");
		System.out.println("\t-h displays this message (other options are ignored)");
		System.out.println("\t-c provides the name of the load test Spring context file (on the classpath)");
	}
	
	private static void printFailure(Exception e)
	{
		System.out.println("Test(s) failed: " + e.getMessage());
		e.printStackTrace();
	}
}
