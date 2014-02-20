package com.ecollege.lunit.scenario.option;

import java.util.Random;

/**
 * @author toddf
 *
 */
public class RandomIntegerOptionChooser
implements OptionChooser<Integer>
{
	// SECTION: INSTANCE VARIABLES

	private Random random = new Random();
	private int maximum = 10;
	private int minimum = 0;


	// SECTION: ACCESSORS/MUTATORS

	public int getMaximum()
	{
		return maximum;
	}

	public void setMaximum(int maximum)
	{
		this.maximum = maximum;
	}

	public int getMinimum()
	{
		return minimum;
	}

	public void setMinimum(int minimum)
	{
		this.minimum = minimum;
	}

	
	// SECTION: CHOOSER

	@Override
	public Integer choose()
	{
		int result;
		
		while ((result = random.nextInt(maximum)) < minimum);
		
		return result;
	}
}
