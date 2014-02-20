/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.test;

import java.util.ArrayList;
import java.util.List;

import com.ecollege.lunit.fixture.DemoTestFixture;
import com.ecollege.lunit.scenario.DemoIterationScenario;
import com.ecollege.lunit.scenario.Scenario;
import com.ecollege.lunit.test.TestCase;
import com.ecollege.lunit.test.TestCaseImpl;
import com.ecollege.lunit.test.SequentialTestRunner;

/**
 * @author toddf
 * @since Sep 12, 2008
 */
public class DemoTestRunner
{
	public static void main(String[] args)
	throws Exception
	{
		DemoTestFixture fixture = new DemoTestFixture();
		
		DemoIterationScenario lt1 = new DemoIterationScenario(10);
		lt1.setFixture(fixture);
		lt1.setActionFixture(fixture);
//		DemoTimedScenario lt2 = new DemoTimedScenario(1);
//		lt2.setTestFixture(fixture);
//		lt2.setIterationFixture(fixture);
		List<Scenario<?>> loadTests = new ArrayList<Scenario<?>>();
		loadTests.add(lt1);
//		loadTests.add(lt2);

		TestCaseImpl testCase = new TestCaseImpl("Demo Test Case");
		testCase.setFixture(fixture);
		testCase.setScenarios(loadTests);

//		testCase.setValidators(validators);
		
		SequentialTestRunner tr = new SequentialTestRunner();
		tr.setFixture(fixture);
		List<TestCase> testCases = new ArrayList<TestCase>(1);
		testCases.add(testCase);
		tr.setTestCases(testCases);
		
		tr.execute();
		
		assert (lt1.wasExecuted());
//		assert (lt2.wasExecuted());
		assert (fixture.getSetupCount() > 20);
		assert (fixture.getTeardownCount() > 20);
	}
}
