package com.colbyreinhart.timelogger.template.lib;

import java.net.URL;

public class HyperLink extends HtmlNode<HyperLink>
{
	public HyperLink()
	{
		super("a");
	}

	public HyperLink href(final URL url)
	{
		attr("href", url.toString());
		return this;
	}

	public HyperLink text(final String text)
	{
		body(new Text(text));
		return this;
	}
}
