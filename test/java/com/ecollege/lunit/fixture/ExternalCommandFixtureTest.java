/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.fixture;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ecollege.lunit.util.OSUtil;

/**
 * @author toddf
 * @since Sep 15, 2008
 */
public class ExternalCommandFixtureTest
{
    private static final String WINDOWS_SETUP_COMMAND_STRING = "cmd /c echo setting up command fixture";
    private static final String WINDOWS_TEARDOWN_COMMAND_STRING = "cmd /c echo tearing down command fixture";
    private static final String LINUX_SETUP_COMMAND_STRING = "/bin/sh -c echo setting up command fixture";
    private static final String LINUX_TEARDOWN_COMMAND_STRING = "/bin/sh -c echo tearing down command fixture";

	private static ExternalCommandFixture fixture;
	
	@BeforeClass
	public static void configureFixture()
	{
		fixture = new ExternalCommandFixture();
		
		if (OSUtil.isWindows())
		{
			fixture.setSetupCommand(WINDOWS_SETUP_COMMAND_STRING);
			fixture.setTeardownCommand(WINDOWS_TEARDOWN_COMMAND_STRING);
		}
		else
		{
			fixture.setSetupCommand(LINUX_SETUP_COMMAND_STRING);
			fixture.setTeardownCommand(LINUX_TEARDOWN_COMMAND_STRING);
		}

		fixture.setValidateExitValue(true);
	}

	@Test
	public void shouldExecuteSetupWithoutException()
	throws Exception
	{
		fixture.setup();
	}

	@Test
	public void shouldExecuteTeardownWithoutException()
	throws Exception
	{
		fixture.teardown();
	}
}
