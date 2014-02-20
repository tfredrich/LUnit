/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
*/
package com.ecollege.lunit.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author toddf
 * @since Sep 15, 2008
 */
public class CommandStreamReader
extends Thread
{
	// SECTION: CONSTANTS
	
	private static final Logger LOG = Logger.getLogger(CommandStreamReader.class);


	// SECTION: INSTANCE VARIABLES

	private InputStream inputStream;
	private String type;
	
	public CommandStreamReader(InputStream is, String type)
	{
		super();
		this.inputStream = is;
		this.type = type;
	}
	
	public void run()
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			
			while ((line = br.readLine()) != null)
			{
				LOG.info(type + ">" + line);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
