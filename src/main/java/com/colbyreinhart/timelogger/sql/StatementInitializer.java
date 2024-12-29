package com.colbyreinhart.timelogger.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementInitializer
{
	public void initialize(final PreparedStatement stmt) throws SQLException;
}
