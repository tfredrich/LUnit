/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ecollege.lunit.scenario.option.Chooser;
import com.ecollege.lunit.scenario.option.Options;
import com.ecollege.lunit.scenario.option.RandomChooser;

/**
 * @author toddf
 * @since Nov 19, 2008
 */
public class RandomChooserTest
{
	// SECTION: CONSTANTS

    private static final int MAXIMUM_CHOICES = 50;

    
    // SECTION: TESTS

	@Test
	public void testChoose()
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
		Integer result;

		for (int i = 0; i < MAXIMUM_CHOICES; i++)
		{
			result = chooser.choose(options);
			assertNotNull(result);
		}
	}

	@Test
	public void testChooseWithNullOptions()
	{
		Chooser<Integer> chooser = new RandomChooser<Integer>();
		Integer result;

		for (int i = 0; i < MAXIMUM_CHOICES; i++)
		{
			result = chooser.choose(null);
			assertNull(result);
		}
	}
}
