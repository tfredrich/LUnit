/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.fixture;

/**
 * @author toddf
 * @since Sep 15, 2008
 */
public class FixtureException
extends Exception
{
	private static final long serialVersionUID = -3512017339271631931L;

	public FixtureException()
	{
		super();
	}

	/**
	 * @param message
	 */
	public FixtureException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public FixtureException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FixtureException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
