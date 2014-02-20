/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.scenario;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ecollege.lunit.event.TestObserver;
import com.ecollege.lunit.fixture.TestFixture;
import com.ecollege.lunit.scenario.option.OptionChooser;
import com.jigsforjava.string.StringUtils;
import com.jigsforjava.util.ClassUtils;

/**
 * @author toddf
 * @since Dec 1, 2008
 */
public class DynamicScenarioBuilder
{
	// SECTION: INSTANCE VARIABLES

	private Class<? extends Scenario<?>> scenarioClass;
	private Map<String, ?> properties;
	private Map<String, OptionChooser<?>> choosers;
	private List<TestObserver> observers;
	private TestFixture fixture;
	private boolean shouldExitOnError = false;

	
	// SECTION: ACCESSORS/MUTATORS

	public Class<? extends Scenario<?>> getScenarioClass()
    {
    	return scenarioClass;
    }

	public void setScenarioClass(Class<? extends Scenario<?>> scenarioClass)
    {
    	this.scenarioClass = scenarioClass;
    }

	public Map<String, ?> getProperties()
    {
    	return properties;
    }
	
	public boolean hasProperties()
	{
		return ((getProperties() != null) && (! getProperties().isEmpty()));
	}

	public void setProperties(Map<String, Object> properties)
    {
    	this.properties = properties;
    }

	public Map<String, OptionChooser<?>> getChoosers()
    {
    	return choosers;
    }
	
	public boolean hasChoosers()
	{
		return ((getChoosers() != null) && (! getChoosers().isEmpty()));
	}

	public void setChoosers(Map<String, OptionChooser<?>> choosers)
    {
    	this.choosers = choosers;
    }
	
	public List<TestObserver> getObservers()
	{
		return observers;
	}
	
	public boolean hasObservers()
	{
		return ((getObservers() != null) && (! getObservers().isEmpty()));
	}
	
	public void setObservers(List<TestObserver> observers)
	{
		this.observers = observers;
	}
	
	public TestFixture getFixture()
    {
    	return fixture;
    }

	public void setFixture(TestFixture fixture)
    {
    	this.fixture = fixture;
    }
	
	public void setShouldExitOnError(boolean value)
	{
		this.shouldExitOnError = value;
	}
	
	public boolean shouldExitOnError()
	{
		return shouldExitOnError;
	}
	
	
	// SECTION: BUILDER

	public Scenario<?> build()
	throws Exception
	{
        Scenario<?> scenario = (Scenario<?>) getScenarioClass().newInstance();
        setPropertiesFor(scenario);
        setChoicesFor(scenario);
        setObserversFor(scenario);
        scenario.setFixture(getFixture());
        scenario.setShouldExitOnError(shouldExitOnError());
        return scenario;
	}
	
	
	// SECTION: UTILITY

	/**
	 * @param result
	 * @param properties2
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void setPropertiesFor(Scenario<?> scenario)
	throws Exception
	{
		if (! hasProperties()) return;

		for (Entry<String, ?> entry : getProperties().entrySet())
		{
			invokeMutator(scenario, entry.getKey(), entry.getValue());
		}
	}

	/**
	 * @param result
	 * @param choosers2
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void setChoicesFor(Scenario<?> scenario)
	throws Exception
	{
		if (! hasChoosers()) return;

		for (Entry<String, OptionChooser<?>> entry : getChoosers().entrySet())
		{
			invokeMutator(scenario, entry.getKey(), entry.getValue().choose());
		}
	}
	
	private void setObserversFor(Scenario<?> scenario)
	{
		if (! hasObservers()) return;
		
		for (TestObserver observer : getObservers())
		{
			scenario.subscribe(observer);
		}
	}

	/**
	 * @param scenario
	 * @param key
	 * @param value
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void invokeMutator(Scenario<?> scenario, String propertyName, Object value)
	throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Method mutator = null;
		Object castValue = value;
		String mutatorName = StringUtils.toMutatorName(propertyName);

		try
		{
			// Attempt primitive mutator first.
			mutator = scenario.getClass().getMethod(StringUtils.toMutatorName(propertyName), getPrimitiveClass(value.getClass()));
		}
		catch (NoSuchMethodException e)
		{
			// do nothing as we'll try the non-primitive mutator before failure.
		}

		// Attempt to derive a possible primitive type if the value is a String.
		if (mutator == null && value instanceof String)
		{
			Map<Class<?>, Object> primitives = getPossiblePrimitivesFromValue((String) value);
			
			for (Class<?> primitive : primitives.keySet())
			{
				try
				{
					mutator = scenario.getClass().getMethod(mutatorName, primitive);
					castValue = primitives.get(primitive);
					break;
				}
				catch (NoSuchMethodException e)
				{
					// do nothing as we'll try the non-primitive mutator before failure.
				}
			}
		}

		if (mutator == null)
		{
			List<Class<?>> classes = getSuperClasses(value.getClass());
			
			for (Class<?> aClass : classes)
			{
				try
				{
					mutator = scenario.getClass().getMethod(mutatorName, aClass);
					break;
				}
				catch (NoSuchMethodException e)
				{
					// do nothing as we'll try the non-primitive mutator before failure.
				}
			}
		}

		if (mutator == null)
		{
			throw new NoSuchMethodException(scenario.getClass() + "." + mutatorName + "(" + value.getClass() + ")");
		}

		mutator.invoke(scenario, castValue);
	}

	private Map<Class<?>, Object> getPossiblePrimitivesFromValue(String value)
	{
		Map<Class<?>, Object> primitives = new HashMap<Class<?>, Object>();
		
		if (value != null)
		{
			attemptDouble(value, primitives);
			attemptFloat(value, primitives);
			attemptInteger(value, primitives);
			attemptLong(value, primitives);
			attemptShort(value, primitives);
			attemptByte(value, primitives);
		}

		return primitives;
	}

	private void attemptDouble(String value, Map<Class<?>, Object> primitives)
	{
		try
		{
			double v = Double.parseDouble(value);
			primitives.put(Double.TYPE, v);
		}
		catch (NumberFormatException e)
		{
			// bummer.  No dice.
		}
	}

	private void attemptFloat(String value, Map<Class<?>, Object> primitives)
	{
		try
		{
			float v = Float.parseFloat(value);
			primitives.put(Float.TYPE, v);
		}
		catch (NumberFormatException e)
		{
			// bummer.  No dice.
		}
	}

	private void attemptInteger(String value, Map<Class<?>, Object> primitives)
	{
		try
		{
			int v = Integer.parseInt(value);
			primitives.put(Integer.TYPE, v);
		}
		catch (NumberFormatException e)
		{
			// bummer.  No dice.
		}
	}

	private void attemptLong(String value, Map<Class<?>, Object> primitives)
	{
		try
		{
			long v = Long.parseLong(value);
			primitives.put(Long.TYPE, v);
		}
		catch (NumberFormatException e)
		{
			// bummer.  No dice.
		}
	}

	private void attemptShort(String value, Map<Class<?>, Object> primitives)
	{
		try
		{
			short v = Short.parseShort(value);
			primitives.put(Short.TYPE, v);
		}
		catch (NumberFormatException e)
		{
			// bummer.  No dice.
		}
	}

	private void attemptByte(String value, Map<Class<?>, Object> primitives)
	{
		try
		{
			byte v = Byte.parseByte(value);
			primitives.put(Byte.TYPE, v);
		}
		catch (NumberFormatException e)
		{
			// bummer.  No dice.
		}
	}

	/**
     * @param aClass
     * @return
     */
    private Class<?> getPrimitiveClass(Class<?> aClass)
    {
    	Class<?> result = aClass;

    	if (Integer.class.equals(aClass))
    	{
    		result = Integer.TYPE;
    	}
    	else if (Boolean.class.equals(aClass))
    	{
    		result = Boolean.TYPE;
    	}
    	else if (Double.class.equals(aClass))
    	{
    		result = Double.TYPE;
    	}
    	else if (Float.class.equals(aClass))
    	{
    		result = Float.TYPE;
    	}
    	else if (Long.class.equals(aClass))
    	{
    		result = Long.TYPE;
    	}
    	else if (Byte.class.equals(aClass))
    	{
    		result = Byte.TYPE;
    	}
    	else if (Short.class.equals(aClass))
    	{
    		result = Short.TYPE;
    	}
    	
    	return result;
    }
    
    private List<Class<?>> getSuperClasses(Class<?> aClass)
    {
    	List<Class<?>> results = new ArrayList<Class<?>>();
    	Class<?> current = aClass;
    	
    	do
    	{
    		if (current.toString().contains("Object"))
    		{
    			break;
    		}
  
    		results.add(current);
    		current = aClass.getSuperclass();
    	}
    	while (current != null);

    	Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(aClass);
    	results.addAll(Arrays.asList(interfaces));
    	return results;
    }
}
