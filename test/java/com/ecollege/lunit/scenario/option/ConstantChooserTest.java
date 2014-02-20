/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.ecollege.lunit.scenario.option.ConstantChooser;
import com.ecollege.lunit.scenario.option.Options;

/**
 * @author toddf
 * @since Dec 1, 2008
 */
public class ConstantChooserTest
{
	private Options<Integer> options;

	@Before
	public void initialize()
	{
		Integer[] values = {1, 2, 3, 5, 8, 13};
		options = new Options<Integer>(Arrays.asList(values));
	}

	@Test
	public void testChooseWithNullOptions()
	{
		ConstantChooser<Integer> chooser = new ConstantChooser<Integer>();
		assertEquals(Integer.valueOf(0), chooser.choose(null));

		chooser.setValue(7);
		assertEquals(Integer.valueOf(7), chooser.choose(null));
	}

	@Test
	public void testChooseWithValueGreaterThanOptionsSize()
	{
		ConstantChooser<Integer> chooser = new ConstantChooser<Integer>(999);
		assertEquals(Integer.valueOf(999), chooser.choose(options));
	}
	
	@Test
	public void testChooseWithValueLessThanZero()
	{
		ConstantChooser<Integer> chooser = new ConstantChooser<Integer>(-5);
		assertEquals(Integer.valueOf(-5), chooser.choose(null));
		assertEquals(Integer.valueOf(-5), chooser.choose(options));
	}
	
	@Test
	public void testChooseWithOptionValue()
	{
		ConstantChooser<Integer> chooser = new ConstantChooser<Integer>(4);
		assertEquals(Integer.valueOf(8), chooser.choose(options));
	}
}
