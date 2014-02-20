/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ecollege.lunit.scenario.option.Chooser;
import com.ecollege.lunit.scenario.option.OptionChooserImpl;
import com.ecollege.lunit.scenario.option.Options;
import com.ecollege.lunit.scenario.option.RandomChooser;

/**
 * @author toddf
 * @since Dec 10, 2008
 */
public class OptionChooserTest
{
	@Test
	public void testChooseWithObject()
	{
		List<Integer> values = new ArrayList<Integer>();
		values.add(1);
		values.add(2);
		values.add(3);
		values.add(5);
		values.add(8);
		values.add(13);
		Options<Integer> options = new Options<Integer>();
		options.setValues(values);
		
		Chooser<Integer> chooser = new RandomChooser<Integer>();
		OptionChooser<Integer> optionChooser = new OptionChooserImpl<Integer>(options, chooser);
		Integer choice = optionChooser.choose();
		assertNotNull(choice);
	}
}
