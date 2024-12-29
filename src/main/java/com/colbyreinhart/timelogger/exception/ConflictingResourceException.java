package com.colbyreinhart.timelogger.exception;

public class ConflictingResourceException extends Exception
{
	public ConflictingResourceException()
	{
		super();
	}

	public ConflictingResourceException(final String message)
	{
		super(message);
	}
}
