package com.colbyreinhart.timelogger.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import com.colbyreinhart.timelogger.exception.ConflictingResourceException;
import com.colbyreinhart.timelogger.model.Task;

public class TaskDAO extends DAO
{
	public TaskDAO(final Connection connection)
	{
		super(connection);
	}

	public Optional<Task> lookupByName(final String name)
	throws SQLException
	{
		return querySingleResult
		(
			"SELECT * FROM TASK WHERE NAME=?",
			stmt -> stmt.setString(1, name),
			TaskDAO::constructEntity
		);
	}

	public void newTask(final String name)
	throws SQLException, ConflictingResourceException
	{
		if (lookupByName(name).isPresent())
		{
			throw new ConflictingResourceException();
		}
		update
		(
			"INSERT INTO TASK VALUES (?,?)",
			stmt ->
			{
				stmt.setBytes(1, UUIDToBytes(UUID.randomUUID()));
				stmt.setString(2, name);
			}
		);
	}

	protected static Task constructEntity(final ResultSet results)
	throws SQLException
	{
		return new Task
		(
			bytesToUUID(results.getBytes(1)),
			results.getString(2)
		);
	}
}
