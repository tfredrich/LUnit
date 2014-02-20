/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import java.util.HashMap;

/**
 * Returns the Options items in order. Note that, in order to provide thread safety,
 * SequentialChooser keeps a hash of Options objects being sequenced.  Therefore, the choices
 * are single threaded during the retrieval (and updating) of the counter for that
 * particular Options instance.
 *
 * @author toddf
 * @since Nov 20, 2008
 */
public class SequentialChooser<T>
implements Chooser<T>
{
	// SECTION: INSTANCE VARIABLES

	private HashMap<Object, Integer> optionCounts = new HashMap<Object, Integer>();

	
	// SECTION: CHOOSER

	@Override
	public T choose(Options<T> options)
	{
		if (options == null) return null;
		
		return options.get(getNext(options));
	}
	
	
	// SECTION: UTILITY

	private synchronized int getNext(Options<T> options)
	{
		int next = 0;
		
		if (optionCounts.containsKey(options))
		{
			next = ((Integer) optionCounts.get(options)).intValue();
			++next;
		}

		if (next >= options.size())
		{
			next = 0;
		}
		
		optionCounts.put(options, Integer.valueOf(next));
		return next;
	}
}
