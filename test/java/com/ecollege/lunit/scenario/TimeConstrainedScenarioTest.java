/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.scenario;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ecollege.lunit.fixture.DemoTestFixture;

/**
 * @author toddf
 * @since Sep 12, 2008
 */
public class TimeConstrainedScenarioTest
{

	/**
	 * Test method for {@link com.ecollege.lunit.scenario.IterationConstrainedScenario#run()}.
	 */
	@Test
	public void testRun()
	throws Exception
	{
		DemoTestFixture testFixture = new DemoTestFixture();
		DemoTestFixture iterationFixture = new DemoTestFixture();

		DemoTimedScenario loadTest = new DemoTimedScenario(2);
		loadTest.setFixture(testFixture);
		loadTest.setActionFixture(iterationFixture);
		loadTest.run();

		assertTrue(loadTest.wasExecuted());
		assertTrue(loadTest.getActionCount() > 0);
		assertEquals(1, testFixture.getSetupCount());
		assertEquals(1, testFixture.getTeardownCount());
		assertTrue(iterationFixture.getSetupCount() > 0);
		assertTrue(iterationFixture.getTeardownCount() > 0);
	}
}
