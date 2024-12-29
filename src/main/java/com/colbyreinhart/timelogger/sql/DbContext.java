package com.colbyreinhart.timelogger.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

import jakarta.servlet.ServletContext;

public class DbContext
{
	private static DbContext instance = null;

	private final Properties properties;
	private final DataSource dataSource;

	private DbContext(final ServletContext context)
	{
		this.properties = new Properties();
		try
		{
			this.properties.load(context.getResourceAsStream("/WEB-INF/application.properties"));
		}
		catch (final IOException e)
		{
			throw new Error("Failed to load application.properties", e);
		}

		dataSource = constructDataSource(properties);
	}

	public static Connection get(final ServletContext context)
	throws SQLException
	{
		if (instance == null)
		{
			instance = new DbContext(context);
		}
		return instance.dataSource.getConnection();
	}

	private static DataSource constructDataSource(final Properties properties)
	{
		final SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:%s".formatted(properties.get("db.path")));
		return ds;
	}
}
