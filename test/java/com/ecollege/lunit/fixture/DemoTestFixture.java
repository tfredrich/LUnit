/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.fixture;

/**
 * @author toddf
 * @since Sep 12, 2008
 */
public class DemoTestFixture
implements TestFixture
{
	private int setupCount = 0;
	private int teardownCount = 0;

	private Object setupLock = new Object();
	private Object teardownLock = new Object();

	@Override
	public void setup() throws Exception
	{
		synchronized (setupLock)
        {
			++setupCount;
        }
	}

	@Override
	public void teardown() throws Exception
	{
		synchronized (teardownLock)
        {
			++teardownCount;
        }
	}

	public int getSetupCount()
    {
		synchronized (setupLock)
        {
	    	return setupCount;
        }
    }

	public int getTeardownCount()
    {
		synchronized (teardownLock)
        {
	    	return teardownCount;
        }
    }
}
