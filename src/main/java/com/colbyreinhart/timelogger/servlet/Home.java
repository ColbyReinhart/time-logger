package com.colbyreinhart.timelogger.servlet;

import java.io.IOException;

import com.colbyreinhart.timelogger.template.lib.Paragraph;
import com.colbyreinhart.timelogger.template.lib.TemplateEngine;
import com.colbyreinhart.timelogger.template.lib.Text;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class Home extends HttpServlet
{
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse res)
	throws IOException, ServletException
	{
		final Paragraph paragraph = new Paragraph().body(new Text("testing!"));
		new TemplateEngine()
			.register("test-component", paragraph)
			.render(getServletContext(), res, "/WEB-INF/template/home.html");
	}
}
