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
public class IterationConstrainedScenarioTest
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

		DemoIterationScenario loadTest = new DemoIterationScenario(10);
		loadTest.setFixture(testFixture);
		loadTest.setActionFixture(iterationFixture);
		loadTest.run();

		assertTrue(loadTest.wasExecuted());
		assertEquals(10, loadTest.getActionCount());
		assertEquals(loadTest.getActionCount(), loadTest.getMaxIterations());
		assertEquals(1, testFixture.getSetupCount());
		assertEquals(1, testFixture.getTeardownCount());
		assertEquals(10, iterationFixture.getSetupCount());
		assertEquals(10, iterationFixture.getTeardownCount());
	}
}
