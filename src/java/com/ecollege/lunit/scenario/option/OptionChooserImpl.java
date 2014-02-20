/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

/**
 * Associates a particular Chooser with the given Options instance.
 * 
 * @author toddf
 * @since Nov 25, 2008
 */
public class OptionChooserImpl<T>
implements OptionChooser<T>
{
	// SECTION: INSTANCE VARIABLES

	private Options<T> options;
	private Chooser<T> chooser;

	
	// SECTION: CONSTRUCTORS

	public OptionChooserImpl()
	{
		super();
	}
	
	public OptionChooserImpl(Options<T> options, Chooser<T> chooser)
	{
		this();
		setOptions(options);
		setChooser(chooser);
	}

	
	// SECTION: ACCESSORS/MUTATORS

	public Options<T> getOptions()
    {
    	return options;
    }

	public void setOptions(Options<T> options)
    {
    	this.options = options;
    }

	public Chooser<T> getChooser()
    {
    	return chooser;
    }

	public void setChooser(Chooser<T> chooser)
    {
    	this.chooser = chooser;
    }

	
	// SECTION: CHOOSING

	@Override
	public T choose()
	{
		return getChooser().choose(getOptions());
	}
}
