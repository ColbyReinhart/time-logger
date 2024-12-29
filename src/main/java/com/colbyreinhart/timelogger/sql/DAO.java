package com.colbyreinhart.timelogger.sql;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class DAO
{
	protected final Connection connection;

	protected DAO(final Connection connection)
	{
		this.connection = connection;
	}

	protected <T,U extends Collection<T>> U query
	(
		final String sql,
		final StatementInitializer initializer,
		final ResultConstructor<T> constructor,
		final Supplier<U> resultContainerSupplier
	)
	throws SQLException
	{
		try (final PreparedStatement stmt = this.connection.prepareStatement(sql))
		{
			initializer.initialize(stmt);
			try (final ResultSet results = stmt.executeQuery())
			{
				final U container = resultContainerSupplier.get();
				while (results.next())
				{
					container.add(constructor.construct(results));
				}
				return container;
			}
		}
	}

	protected <T> List<T> query
	(
		final String sql,
		final StatementInitializer initializer,
		final ResultConstructor<T> constructor
	)
	throws SQLException
	{
		return query(sql, initializer, constructor, ArrayList::new);
	}

	protected <T> Optional<T> querySingleResult
	(
		final String sql,
		final StatementInitializer initializer,
		final ResultConstructor<T> constructor
	)
	throws SQLException
	{
		try (final PreparedStatement stmt = this.connection.prepareStatement(sql))
		{
			initializer.initialize(stmt);
			try (final ResultSet results = stmt.executeQuery())
			{
				if (results.next())
				{
					return Optional.of(constructor.construct(results));
				}
				return Optional.empty();
			}
		}
	}

	protected int update
	(
		final String sql,
		final StatementInitializer initializer
	)
	throws SQLException
	{
		try (final PreparedStatement stmt = this.connection.prepareStatement(sql))
		{
			initializer.initialize(stmt);
			return stmt.executeUpdate();
		}
	}

	protected static UUID bytesToUUID(final byte[] bytes)
	{
		final ByteBuffer buffer = ByteBuffer.wrap(bytes);
		return new UUID(buffer.getLong(), buffer.getLong());
	}

	protected static byte[] UUIDToBytes(final UUID uuid)
	{
		final ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
		return buffer.array();
	}
}
