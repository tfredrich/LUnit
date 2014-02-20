/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ecollege.lunit.scenario.option.Chooser;
import com.ecollege.lunit.scenario.option.Options;
import com.ecollege.lunit.scenario.option.RatioChooser;

/**
 * @author toddf
 * @since Nov 21, 2008
 */
public class RatioChooserTest
{
	Options<Integer> options1;
	Options<Integer> options2;

	@Before
	public void initialize()
	{
		List<Integer> values1 = new ArrayList<Integer>();
		values1.add(1);
		values1.add(2);
		options1 = new Options<Integer>(values1);

		List<Integer> values2 = new ArrayList<Integer>();
		values2.add(3);
		values2.add(4);
		options2 = new Options<Integer>(values2);
	}

	@Test
	public void testChooseForOneToOne()
	{
		Chooser<Integer> chooser = new RatioChooser<Integer>(50);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
	}

	@Test
	public void testChooseForTwoToOne()
	{
		Chooser<Integer> chooser = new RatioChooser<Integer>(67);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
	}

	@Test
	public void testChooseForThreeToOne()
	{
		Chooser<Integer> chooser = new RatioChooser<Integer>(75);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(0, chooser.choose(options1), options1);
		makeAssertions(0, chooser.choose(options2), options2);
		makeAssertions(1, chooser.choose(options1), options1);
		makeAssertions(1, chooser.choose(options2), options2);
	}
	
	
	// SECTION: UTILITY
	
	private void makeAssertions(int index, Object actual, Options<Integer> options)
	{
		assertNotNull(actual);
		assertEquals(options.get(index), actual);
	}
}
