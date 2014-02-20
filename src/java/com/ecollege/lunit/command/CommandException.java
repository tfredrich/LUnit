/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.command;

/**
 * @author toddf
 * @since Oct 14, 2008
 */
public class CommandException
extends Exception
{
    private static final long serialVersionUID = 485580418987938540L;

	/**
     * 
     */
    public CommandException()
    {
	    super();
    }

	/**
     * @param message
     * @param cause
     */
    public CommandException(String message, Throwable cause)
    {
	    super(message, cause);
    }

	/**
     * @param message
     */
    public CommandException(String message)
    {
	    super(message);
    }

	/**
     * @param cause
     */
    public CommandException(Throwable cause)
    {
	    super(cause);
    }
}
