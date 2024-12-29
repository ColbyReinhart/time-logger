package com.colbyreinhart.timelogger.template.lib;

import java.io.PrintWriter;

public record Text(String value) implements HtmlContent
{
	@Override
	public void render(final PrintWriter writer)
	{
		writer.print(value);
	}
}
