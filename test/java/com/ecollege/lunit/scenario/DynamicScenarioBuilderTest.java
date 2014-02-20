/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ecollege.lunit.fixture.DemoTestFixture;
import com.ecollege.lunit.fixture.TestFixture;
import com.ecollege.lunit.scenario.option.Chooser;
import com.ecollege.lunit.scenario.option.OptionChooser;
import com.ecollege.lunit.scenario.option.OptionChooserImpl;
import com.ecollege.lunit.scenario.option.Options;
import com.ecollege.lunit.scenario.option.SequentialChooser;

/**
 * @author toddf
 * @since Dec 2, 2008
 */
public class DynamicScenarioBuilderTest
{
	@Test
	public void testBuildWithoutSettings()
	throws Exception
	{
		DynamicScenarioBuilder builder = new DynamicScenarioBuilder();
		builder.setScenarioClass(BuiltScenario.class);
		BuiltScenario scenario = (BuiltScenario) builder.build();
		assertNotNull(scenario);
		assertTrue(scenario.isRunning());
	}
	
	@Test
	public void testBuildWithFixtureOnly()
	throws Exception
	{
		DynamicScenarioBuilder builder = new DynamicScenarioBuilder();
		builder.setScenarioClass(BuiltScenario.class);
		TestFixture fixture = new DemoTestFixture();
		builder.setFixture(fixture);
		BuiltScenario scenario = (BuiltScenario) builder.build();
		assertNotNull(scenario);
		assertTrue(scenario.isRunning());
		assertNotNull(scenario.getFixture());
		assertTrue(fixture == scenario.getFixture());
	}
	
	@Test
	public void testBuildWithProperties()
	throws Exception
	{
		DynamicScenarioBuilder builder = new DynamicScenarioBuilder();
		builder.setScenarioClass(BuiltScenario.class);
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("intProperty", 5);
		String stringValue = "toddf";
		properties.put("stringProperty", stringValue);
		DoableImpl doableValue = new DoableImpl();
		properties.put("doableProperty", doableValue);
		builder.setProperties(properties);
		BuiltScenario scenario = (BuiltScenario) builder.build();
		assertNotNull(scenario);
		assertTrue(scenario.isRunning());
		assertEquals(5, scenario.getIntProperty());
		assertEquals(stringValue, scenario.getStringProperty());
	}
	
	@Test
	public void testBuildWithChoosers()
	throws Exception
	{
		DynamicScenarioBuilder builder = new DynamicScenarioBuilder();
		builder.setScenarioClass(BuiltScenario.class);

		Chooser chooser = new SequentialChooser();
		Integer[] ints = {1, 2, 3, 5, 8, 13};
		Options<Integer> intOptions = new Options<Integer>(Arrays.asList(ints));
		OptionChooser<Integer> intChooser = new OptionChooserImpl<Integer>(intOptions, chooser);

		String[] strings = {"aaa", "bbb", "ccc"};
		Options<String> stringOptions = new Options<String>(Arrays.asList(strings));
		OptionChooser<String> stringChooser = new OptionChooserImpl<String>(stringOptions, chooser);

		Map<String, OptionChooser<?>> choosers = new HashMap<String, OptionChooser<?>>();
		choosers.put("intProperty", intChooser);
		choosers.put("stringProperty", stringChooser);
		builder.setChoosers(choosers);

		BuiltScenario scenario = (BuiltScenario) builder.build();
		assertNotNull(scenario);
		assertTrue(scenario.isRunning());
		assertEquals(ints[0].intValue(), scenario.getIntProperty());
		assertEquals(strings[0], scenario.getStringProperty());

		scenario = (BuiltScenario) builder.build();
		assertNotNull(scenario);
		assertTrue(scenario.isRunning());
		assertEquals(ints[1].intValue(), scenario.getIntProperty());
		assertEquals(strings[1], scenario.getStringProperty());

		scenario = (BuiltScenario) builder.build();
		assertNotNull(scenario);
		assertTrue(scenario.isRunning());
		assertEquals(ints[2].intValue(), scenario.getIntProperty());
		assertEquals(strings[2], scenario.getStringProperty());
	}
}
