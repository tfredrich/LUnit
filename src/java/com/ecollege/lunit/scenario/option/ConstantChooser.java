/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;


/**
 * A Chooser that returns a constant value.  If the Options instance passed in to
 * choose() is null, the value is returned.  Otherwise, the value is used as an index
 * into the Options items.
 * 
 * @author toddf
 * @since Nov 19, 2008
 */
public class ConstantChooser<T>
implements Chooser<T>
{
	// SECTION: INSTANCE VARIABLES

	private T value;

	
	// SECTION: CONSTRUCTORS

	public ConstantChooser()
	{
		super();
	}
	
	public ConstantChooser(T value)
	{
		this();
		setValue(value);
	}

	
	// SECTION: MUTATOR

	public void setValue(T value)
	{
		this.value = value;
	}

	
	// SECTION: CHOOSER

	@Override
	public T choose(Options<T> options)
	{
		if (options == null) return value;

		return options.get(Integer.valueOf((String) value));
	}

}
