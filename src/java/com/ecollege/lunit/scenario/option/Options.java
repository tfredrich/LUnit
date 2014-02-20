/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario.option;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a list of option values.  Used by an Chooser instance to select an option
 * from the list.
 * 
 * @author toddf
 * @since Nov 19, 2008
 */
public class Options<T>
{
	// SECTION: INSTANCE VARIABLES

	private List<T> values = new ArrayList<T>();

	
	// SECTION: CONSTRUCTORS

    public Options()
    {
	    super();
    }

	/**
     * @param values
     */
    public Options(List<T> values)
    {
	    this();
	    setValues(values);
    }

    
    // SECTION: ACCESSORS/MUTATORS

	public void setValues(List<T> values)
	{
		this.values = values;
	}

	public int size()
	{
		return values.size();
	}
	
	public T get(int index)
	{
		return values.get(index);
	}
}
