/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ecollege.lunit.fixture.DemoTestFixture;
import com.ecollege.lunit.scenario.DemoIterationScenario;
import com.ecollege.lunit.scenario.DemoTimedScenario;
import com.ecollege.lunit.scenario.Scenario;
import com.ecollege.lunit.test.TestCaseImpl;

/**
 * @author toddf
 * @since Sep 12, 2008
 */
public class TestCaseImplTest
{

	/**
	 * Test method for {@link com.ecollege.lunit.test.TestCaseImpl#execute()}.
	 */
	@Test
	public void testExecute()
	throws Exception
	{
		DemoTestFixture testFixture1 = new DemoTestFixture();
		DemoTestFixture iterationFixture1 = new DemoTestFixture();

		DemoIterationScenario iterationLoadTest = new DemoIterationScenario(10);
		iterationLoadTest.setFixture(testFixture1);
		iterationLoadTest.setActionFixture(iterationFixture1);

		DemoTestFixture testFixture2 = new DemoTestFixture();
		DemoTestFixture iterationFixture2 = new DemoTestFixture();

		DemoTimedScenario timedLoadTest = new DemoTimedScenario(2);
		timedLoadTest.setFixture(testFixture2);
		timedLoadTest.setActionFixture(iterationFixture2);

		List<Scenario<?>> loadTests = new ArrayList<Scenario<?>>();
		loadTests.add(iterationLoadTest);
		loadTests.add(timedLoadTest);
		DemoTestFixture testCaseFixture = new DemoTestFixture();

		TestCaseImpl testCase = new TestCaseImpl("Demo Test Case");
		testCase.setFixture(testCaseFixture);
		testCase.setScenarios(loadTests);
		testCase.execute();

		assertEquals(1, testCaseFixture.getSetupCount());
		assertEquals(1, testCaseFixture.getTeardownCount());
		
		assertTrue(iterationLoadTest.wasExecuted());
		assertEquals(10, iterationLoadTest.getActionCount());
		assertEquals(iterationLoadTest.getActionCount(), iterationLoadTest.getMaxIterations());
		assertEquals(1, testFixture1.getSetupCount());
		assertEquals(1, testFixture1.getTeardownCount());
		assertEquals(10, iterationFixture1.getSetupCount());
		assertEquals(10, iterationFixture1.getTeardownCount());
		
		
		assertTrue(timedLoadTest.wasExecuted());
		assertTrue(timedLoadTest.getActionCount() > 0);
		assertEquals(1, testFixture2.getSetupCount());
		assertEquals(1, testFixture2.getTeardownCount());
		assertTrue(iterationFixture2.getSetupCount() > 0);
		assertTrue(iterationFixture2.getTeardownCount() > 0);
	}
}
