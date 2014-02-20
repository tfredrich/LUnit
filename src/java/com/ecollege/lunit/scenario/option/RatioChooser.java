/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import java.util.HashMap;

/**
 * Based on the percentage, chooses either Options[0] or Options[1].
 * This chooser simply uses an internal counter, mod'ing that counter
 * with the percentage (represented as a ratio).  If the mod operation
 * returns zero (0), option one (1) is chosen.  Otherwise, option zero
 * (0) is returned. 
 * 
 * @author toddf
 * @since Nov 20, 2008
 */
public class RatioChooser<T>
implements Chooser<T>
{
	// SECTION: INSTANCE VARIABLES

	private HashMap<Object, Integer> optionCounts = new HashMap<Object, Integer>();
	private int ratio;

	// SECTION: CONSTRUCTORS
	
	public RatioChooser()
	{
		super();
	}
	
	public RatioChooser(int percentage)
	{
		this();
		setPercentage(percentage);
	}

	
	// SECTION: ACCESSORS/MUTATORS

	public void setPercentage(int percentage)
    {
    	this.ratio = 100 / (100 - percentage);
    }

	
	// SECTION: CHOOSER

	/**
	 * Returns either option 0 or option 1.
	 * 
	 * @param options
	 */
	@Override
	public T choose(Options<T> options)
	{
		if ((getNext(options) % ratio) == 0)
		{
			return options.get(1);
		}
		
		return options.get(0);
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
		
		optionCounts.put(options, Integer.valueOf(next));
		return next;
	}
}
