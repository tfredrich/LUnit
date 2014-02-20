package com.ecollege.lunit.scenario.option;

import com.ecollege.lunit.scenario.option.OptionChooser;

/**
 * @author toddf
 * @since September 30, 2009
 */
public class SequentialNameChooser
implements OptionChooser<String>
{
	private String prefix = "User";
	private long startingId = 0L;
	private long nextId = 0L;
	private long maxId = 0L;

	public String getPrefix()
	{
		return prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public long getStartingId()
	{
		return startingId;
	}

	public void setStartingId(long startingId)
	{
		this.startingId = startingId;
	}
	
	public long getMaxId()
	{
		return maxId;
	}
	
	public void setMaxId(long maxId)
	{
		this.maxId = maxId;
	}

	@Override
	public String choose()
	{
		return prefix + getNextId();
	}
	
	private synchronized long getNextId()
	{
		if (nextId == 0L
			|| (nextId > getMaxId() && getMaxId() != 0L))
		{
			nextId = getStartingId();
		}
		
		return nextId++;
	}
}
