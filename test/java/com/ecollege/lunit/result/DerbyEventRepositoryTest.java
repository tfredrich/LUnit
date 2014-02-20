/*
 * Copyright 2008, eCollege, Inc.  All rights reserved.
 */
package com.ecollege.lunit.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ecollege.lunit.event.EventType;
import com.ecollege.lunit.event.TestEvent;
import com.ecollege.lunit.fixture.DemoTestFixture;
import com.ecollege.lunit.repository.DerbyEventRepository;

/**
 * @author toddf
 * @since Nov 24, 2008
 */
public class DerbyEventRepositoryTest
{
	private static EmbeddedDataSource ds;
	private static DerbyEventRepository repository;

	@BeforeClass
	public static void createRepository()
	{
		ds = new EmbeddedConnectionPoolDataSource();
		ds.setDatabaseName("build/DerbyResultRepositoryUT");
		ds.setCreateDatabase("create");
		repository = new DerbyEventRepository(ds, "unitTest");
	}
	
	@AfterClass
	public static void deleteRepository()
	throws SQLException
	{
		repository.drop();
	}
	
	@After
	public void emptyRepository()
	throws SQLException
	{
		repository.clean();
	}

	@Test
	public void testInsert()
	throws Exception
	{
		TestEvent event = new TestEvent(EventType.OTHER, this);
		repository.insert(event);
		
		Connection connection = ds.getConnection();
		Statement statement = connection.createStatement();
		assertTrue(statement.execute("SELECT * FROM events"));
		ResultSet results = statement.getResultSet();
		assertTrue("No results returned", results.next());
		assertEquals(event.getType().name(), results.getString(1));
		assertEquals(event.getClass().getName(), results.getString(2));
		assertEquals(event.getOriginatorName(), results.getString(3));
		assertEquals(new Timestamp(event.getOccurredAt().getTime()).toString(), results.getTimestamp(4).toString());
		assertFalse("More-than one row in DB", results.next());
	}

	@Test
	public void testInsertAgain()
	throws Exception
	{
		TestEvent event = new TestEvent(EventType.TEST_SETUP, new DemoTestFixture());
		repository.insert(event);
		
		Connection connection = ds.getConnection();
		Statement statement = connection.createStatement();
		assertTrue(statement.execute("SELECT * FROM events"));
		ResultSet results = statement.getResultSet();
		assertTrue("No results returned", results.next());
		assertEquals(event.getType().name(), results.getString(1));
		assertEquals(event.getClass().getName(), results.getString(2));
		assertEquals(event.getOriginatorName(), results.getString(3));
		assertEquals(new Timestamp(event.getOccurredAt().getTime()).toString(), results.getTimestamp(4).toString());
		assertFalse("More-than one row in DB", results.next());
	}
}
