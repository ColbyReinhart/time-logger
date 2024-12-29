package com.colbyreinhart.timelogger.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/submit")
public class SubmitLog extends HttpServlet
{
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse res)
	throws IOException, ServletException
	{
		
	}
}
