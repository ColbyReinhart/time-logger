package com.colbyreinhart.timelogger.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultConstructor <T>
{
	public T construct(final ResultSet results) throws SQLException;
}
