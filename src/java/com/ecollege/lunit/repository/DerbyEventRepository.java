/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.derby.jdbc.EmbeddedDataSource;

import com.ecollege.lunit.event.EventCallback;
import com.ecollege.lunit.event.TestEvent;
import com.jigsforjava.string.MapMessageFormat;

/**
 * Stores events in an embedded Apache Derby database.
 * 
 * @author toddf
 * @since Nov 21, 2008
 */
public class DerbyEventRepository
implements EventRepository
{
	// SECTION: CONSTANTS

	private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE events "
	    + "("
	    + "tester VARCHAR(512) NOT NULL,"
	    + "event_type VARCHAR(512) NOT NULL,"
	    + "originator VARCHAR(512) NOT NULL,"
	    + "occurred_at TIMESTAMP NOT NULL,"
	    + "started_at TIMESTAMP,"
	    + "message VARCHAR(512),"
	    + "throwable VARCHAR(512)"
	    + ")";

	private static final String INSERT_TEMPLATE_STATEMENT = "INSERT INTO events "
		+ "(tester, event_type, originator, occurred_at, started_at, message, throwable) "
		+ "VALUES ('{tester}', '{eventType}', '{originator}', '{occurredAt}', '{startedAt}', '{message}', '{throwable}')";

	private static final String DROP_TABLE_STATEMENT = "DROP TABLE events";
	private static final String CLEAN_TABLE_STATEMENT = "DELETE FROM events";


	// SECTION: INSTANCE VARIABLES

	private EmbeddedDataSource dataSource;
    private String testerId;


    // SECTION: CONSTRUCTOR

	public DerbyEventRepository(EmbeddedDataSource dataSource, String testerId)
	{
		super();
        this.testerId = testerId;
        setDataSource(dataSource);
		initialize();
	}


	// SECTION: ACCESSORS/MUTATORS

	private void setDataSource(EmbeddedDataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	// SECTION: FINALIZATION

	@Override
	public void finalize()
	{
		try
		{
			dataSource.setShutdownDatabase("shutdown");
			dataSource.getConnection();
		}
		catch (SQLException e)
		{
			if (((e.getErrorCode() == 50000) 
					&& ("XJ015".equals(e.getSQLState()))))
			{
				// we got the expected exception
				System.out.println("Derby shut down normally");
				// Note that for single database shutdown, the expected
				// SQL state is "08006", and the error code is 45000.
			}
			else
			{
				// if the error code or SQLState is different, we have
				// an unexpected exception (shutdown failed)
				System.err.println("Derby did not shut down normally");
				printSQLException(e);
			}
		}
	}

	// SECTION: INITIALIZATION

	protected void initialize()
	{
		createDataTable();
	}

	private void createDataTable()
	{
		try
		{
			executeUpdate(CREATE_TABLE_STATEMENT);
		}
		catch (SQLException e)
		{
			printSQLException(e);
		}
	}
	
	private void dropDataTable()
	{
		try
        {
	        executeUpdate(DROP_TABLE_STATEMENT);
        }
        catch (SQLException e)
        {
        	printSQLException(e);
        }
	}


	// SECTION: UTILITY

	private void executeUpdate(String sql)
	throws SQLException
	{
		Connection connection = null;
		try
		{
			connection = dataSource.getConnection();
//			connection.setAutoCommit(true);
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		}
		finally
		{
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					printSQLException(e);
				}
			}
		}
	}

	private void printSQLException(SQLException e)
	{
		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null)
		{
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			// for stack traces, refer to derby.log or uncomment this:
			e.printStackTrace(System.err);
			e = e.getNextException();
		}
	}


	// SECTION: SUB-CLASS RESPONSIBILITIES

	@Override
	public void insert(TestEvent event) throws RepositoryException
	{
		Map<String, String> values = new HashMap<String, String>();
		event.getValuesInto(values);

        values.put("tester", testerId);

        String sql = formatSql(values);
		
		try
        {
	        executeUpdate(sql);
        }
        catch (SQLException e)
        {
        	printSQLException(e);
        	throw new RepositoryException(e);
        }
	}
	
	public void iterate(EventCallback callback)
	{
		// TODO: implement iterate(EventCallback);
	}

	public void drop()
	{
		dropDataTable();
	}
	
	public void clean()
	{
		try
        {
	        executeUpdate(CLEAN_TABLE_STATEMENT);
        }
        catch (SQLException e)
        {
        	printSQLException(e);
        }
	}

	private String formatSql(Map<String, String> values)
	{
		MapMessageFormat formatter = new MapMessageFormat();
		String sql = formatter.format(INSERT_TEMPLATE_STATEMENT, values);
		sql = sql.replaceAll("'\\{.*\\}'", "null");
		sql = sql.replace("''", "null");
		sql = sql.replace("'\\(null\\)'", "null");
		return sql;
	}
}
