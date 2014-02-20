/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.scenario;


/**
 * Defines a Scenario that is limited by how many times the executeIteration() method is called.
 * 
 * @author toddf
 * @since Sep 12, 2008
 */
public abstract class IterationConstrainedScenario<T extends IterationConstrainedScenario<T>>
extends Scenario<T>
{
	// SECTION: INSTANCE VARIABLES

	private int maxIterations;

	
	// SECTION: CONSTRUCTOR

	public IterationConstrainedScenario()
	{
		super();
	}
	
	public IterationConstrainedScenario(int maxIterations)
	{
		super();
		setMaxIterations(maxIterations);
	}
	
	
	// SECTION: ACCESSORS/MUTATORS

	public int getMaxIterations()
    {
    	return maxIterations;
    }

	public void setMaxIterations(int maxIterations)
    {
    	this.maxIterations = maxIterations;
    }

	
	// SECTION: SUB-CLASS RESPONSIBILITIES

	@Override
	protected boolean shouldContinue()
	{
		return (getActionCount() < getMaxIterations());
	}
	
	@Override
	public T copy()
	{
		T clone = super.copy();
		clone.setMaxIterations(getMaxIterations());
		return clone;
	}
}
