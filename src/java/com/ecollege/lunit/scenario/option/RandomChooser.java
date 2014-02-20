/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import java.util.Random;

/**
 * @author toddf
 * @since Nov 19, 2008
 */
public class RandomChooser<T>
implements Chooser<T>
{
	// SECTION: INSTANCE VARIABLES

	private Random random = new Random();

	
	// SECTION: CHOOSER

	/* (non-Javadoc)
	 * @see com.ecollege.lunit.scenario.Chooser#choose(com.ecollege.lunit.scenario.Options)
	 */
	@Override
	public T choose(Options<T> options)
	{
		if (options == null) return null;

		return options.get(random.nextInt(options.size()));
	}
}
