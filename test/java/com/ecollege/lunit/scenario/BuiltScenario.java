/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario;


/**
 * @author toddf
 * @since Dec 2, 2008
 */

public class BuiltScenario
extends Scenario<BuiltScenario>
{
	private int intProperty;
	private String stringProperty;
	private Doable doableProperty;
	

	public int getIntProperty()
    {
    	return intProperty;
    }

	public void setIntProperty(int intProperty)
    {
    	this.intProperty = intProperty;
    }

	public String getStringProperty()
    {
    	return stringProperty;
    }

	public void setStringProperty(String stringProperty)
    {
    	this.stringProperty = stringProperty;
    }
	
	public Doable getDoableProperty()
	{
		return doableProperty;
	}
	
	public void setDoableProperty(Doable doableProperty)
	{
		this.doableProperty = doableProperty;
	}

    @Override
    protected void executeAction() throws Exception
    {
    }

    @Override
    protected BuiltScenario newInstance()
    {
        return new BuiltScenario();
    }

    @Override
    protected boolean shouldContinue()
    {
        return false;
    }
}
