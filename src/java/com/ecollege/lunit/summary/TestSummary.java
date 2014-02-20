/*
    Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.summary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jigsforjava.string.StringConstants;

/**
 * @author toddf
 * @since Sep 12, 2008
 */
public class TestSummary
{
	// SECTION: INSTANCE VARIABLES

	private String testName = StringConstants.EMPTY_STRING;
	private int scenarioCount = 0;
	private long elapsedTimeMillis = 0L;
	private int failureCount = 0;
	private int errorCount = 0;
	private int actionCount = 0;
	private long totalActionTime = 0L;
	private long totalScenarioTime = 0L;
	private int maxActions = 0;
	private long maxTimeMillis = 0L;
	private Map<String, Object> properties = new LinkedHashMap<String, Object>();
	private List<String> validationMessages = new ArrayList<String>();

	
	// SECTION: CONSTRUCTORS
	
	public TestSummary()
	{
		super();
	}
	
	public TestSummary(Map<String, Object> properties)
	{
		this();
		this.properties = properties;
	}
	
	// SECTION: COMPUTATIONS
	
	public double computeMeanActionTime()
	{
		return ((double) getTotalActionTime() / (double) getActionCount());
	}
	
	public double computeMeanScenarioTime()
	{
		return ((double) getTotalScenarioTime() / (double) getScenarioCount());
	}
	
	public double computeActionsPerSecond()
	{
		return (computeTransactionsPerSecond() * (double) getScenarioCount());
	}
	
	public double computeTransactionsPerSecond()
	{
		return (double) getActionCount() / ((double) getTotalActionTime() / 1000.0);
	}

	
	// SECTION: ACCESSORS/MUTATORS

	public String getTestName()
    {
    	return testName;
    }

	public void setTestName(String testName)
    {
    	this.testName = testName;
    }
	
	public Object getProperty(String name)
	{
		return properties.get(name);
	}

	public boolean hasProperties()
	{
		return (! properties.isEmpty());
	}

	public void setProperty(String name, Object value)
	{
		properties.put(name, value);
	}

	public int getScenarioCount()
	{
		return scenarioCount;
	}

	public void setScenarioCount(int threadCount)
	{
		this.scenarioCount = threadCount;
	}

	public long getTotalScenarioTime()
	{
		return totalScenarioTime;
	}

	public void setTotalScenarioTime(long totalScenarioTime)
	{
		this.totalScenarioTime = totalScenarioTime;
	}

	public long getElapsedTimeMillis()
	{
		return elapsedTimeMillis;
	}

	public void setElapsedTimeMillis(long elapsedTimeMillis)
	{
		this.elapsedTimeMillis = elapsedTimeMillis;
	}

	public int getFailureCount()
	{
		return failureCount;
	}
	
	public boolean hasFailures()
	{
		return (getFailureCount() > 0);
	}

	public void setFailureCount(int failureCount)
	{
		this.failureCount = failureCount;
	}

	public int getErrorCount()
	{
		return errorCount;
	}
	
	public boolean hasErrors()
	{
		return (getErrorCount() > 0);
	}

	public void setErrorCount(int errorCount)
	{
		this.errorCount = errorCount;
	}

	public List<String> getValidationMessages()
	{
		return validationMessages;
	}
	
	public boolean hasValidationMessages()
	{
		return !(getValidationMessages().isEmpty());
	}
	
	public boolean didTestPass()
	{
		return (getValidationMessages().isEmpty() && !hasErrors() && !hasFailures());
	}

	public void addAllValidationMessages(List<String> validationMessages)
	{
		getValidationMessages().addAll(validationMessages);
	}

	public void addValidationMessage(String validationMessage)
	{
		getValidationMessages().add(validationMessage);
	}

	public int getActionCount()
	{
		return (actionCount);
	}

	public void setActionCount(int iterationCount)
	{
		this.actionCount = iterationCount;
	}

	public long getTotalActionTime()
	{
		return totalActionTime;
	}

	public void setTotalActionTime(long totalIterationTime)
	{
		this.totalActionTime = totalIterationTime;
	}

	public int getMaxActions()
	{
		return maxActions;
	}

	public void setMaxActions(int maxIterations)
	{
		this.maxActions = maxIterations;
	}

	public long getMaxTimeMillis()
	{
		return maxTimeMillis;
	}

	public void setMaxTimeMillis(long maxTimeMillis)
	{
		this.maxTimeMillis = maxTimeMillis;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		appendLine(sb, getTestName(), didTestPass() ? "PASSED" : "FAILED");
		
		if (hasValidationMessages())
		{
			for (String message : getValidationMessages())
			{
				sb.append("\t");
				sb.append(message);
				sb.append("\n");
			}
		}

		appendLine(sb, "scenarios (threads)", String.valueOf(getScenarioCount()));
		appendLine(sb, "actions (txns)", String.valueOf(getActionCount()));
		appendLine(sb, "TPS", String.valueOf(computeTransactionsPerSecond()));
		appendLine(sb, "tot actions/sec", String.valueOf(computeActionsPerSecond()));
		appendLine(sb, "avg action time (ms)", String.valueOf(computeMeanActionTime()));
		appendLine(sb, "elapsed (ms)", String.valueOf(getElapsedTimeMillis()));
		appendLine(sb, "tot action time (ms)", String.valueOf(getTotalActionTime()));
		appendLine(sb, "tot scenario time (ms)", String.valueOf(getTotalScenarioTime()));
		appendLine(sb, "failures", String.valueOf(getFailureCount()));
		appendLine(sb, "errors", String.valueOf(getErrorCount()));
		appendProperties(sb);
		return sb.toString();
	}

	private void appendLine(StringBuilder sb, String prompt, String value)
	{
		sb.append(prompt);
		sb.append(": ");
		sb.append(value);
		sb.append("\n");
	}

    private void appendProperties(StringBuilder sb)
    {
    	for (Entry<String, Object> entry : properties.entrySet())
    	{
    		appendLine(sb, entry.getKey(), entry.getValue().toString());
    	}
    }
}
