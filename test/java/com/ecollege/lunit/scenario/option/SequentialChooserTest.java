/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ecollege.lunit.scenario.option.Chooser;
import com.ecollege.lunit.scenario.option.Options;
import com.ecollege.lunit.scenario.option.SequentialChooser;

/**
 * @author toddf
 * @since Nov 19, 2008
 */
public class SequentialChooserTest
{
    // SECTION: TESTS

	/**
	 * Test method for {@link com.ecollege.lunit.scenario.option.SequentialChooser#choose(com.ecollege.lunit.scenario.option.Options)}.
	 */
	@Test
	public void testChoose()
	{
		List<Integer> values1 = new ArrayList<Integer>();
		values1.add(1);
		values1.add(2);
		values1.add(3);
		values1.add(5);
		values1.add(8);
		values1.add(13);
		Options<Integer> options1 = new Options<Integer>(values1);

		List<Integer> values2 = new ArrayList<Integer>();
		values2.add(10);
		values2.add(20);
		values2.add(30);
		values2.add(50);
		values2.add(80);
		values2.add(130);
		Options<Integer> options2 = new Options<Integer>(values2);

		Chooser<Integer> chooser = new SequentialChooser<Integer>();
		Integer result;
		
		for (int j = 0; j < 3; j++)
		{
			for (int i = 0; i < values1.size(); i++)
			{
				result = chooser.choose(options1);
				assertNotNull(result);
				assertEquals(options1.get(i), result);

				result = chooser.choose(options2);
				assertNotNull(result);
				assertEquals(options2.get(i), result);
			}
		}
	}
}
