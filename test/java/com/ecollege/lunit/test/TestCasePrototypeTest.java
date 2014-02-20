/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ecollege.lunit.fixture.DemoTestFixture;
import com.ecollege.lunit.scenario.DemoIterationScenario;
import com.ecollege.lunit.scenario.DynamicScenarioBuilder;
import com.ecollege.lunit.scenario.Scenario;

/**
 * @author toddf
 * @since Sep 16, 2008
 */
public class TestCasePrototypeTest
{
	// SECTION: CONSTANTS

    private static final int MAXIMUM_ITERATIONS = 10;

    
    // SECTION: TESTS

	/**
	 * Test method for {@link com.ecollege.lunit.test.TestCasePrototype#asTestCase()}.
	 * @throws Exception 
	 */
	@Test
	public void testAsTestCase()
	throws Exception
	{
		DemoTestFixture scenarioFixture = new DemoTestFixture();
		DemoTestFixture scenarioIterationFixture = new DemoTestFixture();

		DemoIterationScenario prototypeScenario = new DemoIterationScenario(MAXIMUM_ITERATIONS);
		prototypeScenario.setFixture(scenarioFixture);
		prototypeScenario.setActionFixture(scenarioIterationFixture);

		TestCasePrototype prototype = new TestCasePrototype("Prototype Test");
		prototype.setScenarioCount(10);
		prototype.setPrototype(prototypeScenario);
		DemoTestFixture testCaseFixture = new DemoTestFixture();
		prototype.setFixture(testCaseFixture);
		
		TestCaseImpl testCase = prototype.asTestCase();
		testCase.execute();

		assertEquals(1, testCaseFixture.getSetupCount());
		assertEquals(1, testCaseFixture.getTeardownCount());
		assertTrue(testCaseFixture == testCase.getFixture());

		for (Scenario<?> scenario : testCase.getScenarios())
		{
			DemoIterationScenario executedScenario = (DemoIterationScenario) scenario;
			assertTrue(executedScenario.wasExecuted());
			assertEquals(10, executedScenario.getActionCount());
			assertEquals(executedScenario.getActionCount(), executedScenario.getMaxIterations());
			
			DemoTestFixture testFixture = (DemoTestFixture) executedScenario.getFixture();
			assertNotNull(testFixture);
			assertEquals(10, testFixture.getSetupCount());
			assertEquals(10, testFixture.getTeardownCount());
			assertTrue(scenarioFixture == testFixture);

			DemoTestFixture iterationFixture = (DemoTestFixture) executedScenario.getActionFixture();
			assertNotNull(iterationFixture);
			assertEquals(100, iterationFixture.getSetupCount());
			assertEquals(100, iterationFixture.getTeardownCount());
			assertTrue(iterationFixture == scenarioIterationFixture);
			
		}
	}
	
	@Test
	public void testAsTestCaseWithBuilder()
	throws Exception
	{
		String testName = "Prototype Test with Builder";
		TestCasePrototype prototype = new TestCasePrototype(testName);
		int scenarioCount = 3;
		prototype.setScenarioCount(scenarioCount);
		
		DynamicScenarioBuilder builder = new DynamicScenarioBuilder();
		builder.setScenarioClass(DemoIterationScenario.class);
		prototype.setBuilder(builder);
		TestCaseImpl testCase = prototype.asTestCase();
		
		assertNotNull(testCase);
		assertEquals(testName, testCase.getName());
		assertEquals(scenarioCount, testCase.getScenarios().size());

		for (Scenario<?> scenario : testCase.getScenarios())
		{
			assertEquals(DemoIterationScenario.class, scenario.getClass());
		}
	}
}
