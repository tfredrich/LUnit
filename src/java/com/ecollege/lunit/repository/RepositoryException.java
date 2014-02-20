/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.repository;

/**
 * @author toddf
 * @since Nov 19, 2008
 */
public class RepositoryException
extends Exception
{
	private static final long serialVersionUID = -2559819000737089058L;

	public RepositoryException()
	{
	}

	/**
	 * @param message
	 */
	public RepositoryException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public RepositoryException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RepositoryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
