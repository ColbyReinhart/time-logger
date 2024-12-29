package com.colbyreinhart.timelogger.template.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public class TemplateEngine
{
	protected final Map<String,HtmlContent> registry;

	public TemplateEngine()
	{
		this.registry = new HashMap<>();
	}

	public TemplateEngine register(final String name, final HtmlContent node)
	{
		registry.put(name, node);
		return this;
	}

	public void render(final ServletContext context, final HttpServletResponse res, final String template)
	throws IOException, IllegalArgumentException
	{
		
		final InputStream inputStream = context.getResourceAsStream(template);
		if (inputStream == null)
		{
			throw new IllegalArgumentException("Resource not found: %s".formatted(template));
		}

		// Definitely not the most efficient way to do this, but I can't be bothered
		String renderedString = IOUtils.toString(inputStream, Charset.defaultCharset());
		for (final Entry<String,HtmlContent> component: registry.entrySet())
		{
			final StringWriter writer = new StringWriter();
			component.getValue().render(new PrintWriter(writer));
			renderedString = renderedString.replace("<%s/>".formatted(component.getKey()), writer.toString());
		}
		IOUtils.write(renderedString, res.getOutputStream(), Charset.defaultCharset());
	}
}
