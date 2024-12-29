package com.colbyreinhart.timelogger.servlet;

import java.io.IOException;
import java.sql.Connection;

import com.colbyreinhart.timelogger.exception.ConflictingResourceException;
import com.colbyreinhart.timelogger.sql.DbContext;
import com.colbyreinhart.timelogger.sql.TaskDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/newTask")
public class NewTask extends HttpServlet
{
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse res)
	throws IOException, ServletException
	{
		final String taskName = req.getParameter("name");
		if (taskName == null)
		{
			res.sendError(400, "Illegal arguments");
		}

		try (final Connection connection = DbContext.get(getServletContext()))
		{
			new TaskDAO(connection).newTask(taskName);
		}
		catch (final ConflictingResourceException e)
		{
			res.sendError(400, "A task with this name already exists");
		}
		catch (final Throwable e)
		{
			e.printStackTrace();
			res.sendError(500);
		}
	}
}
