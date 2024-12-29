package com.colbyreinhart.timelogger.sql;

import java.sql.Connection;

public class LogDAO extends DAO
{
	public LogDAO(final Connection connection)
	{
		super(connection);
	}
}
