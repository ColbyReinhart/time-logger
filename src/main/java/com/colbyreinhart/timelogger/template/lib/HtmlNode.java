package com.colbyreinhart.timelogger.template.lib;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HtmlNode<T extends HtmlNode<T>> implements HtmlContent
{
	final String tagName;
	final Map<String,String> attributes;
	final List<HtmlContent> children;

	public HtmlNode(final String tagName)
	{
		this.tagName = tagName;
		this.attributes = new LinkedHashMap<>();
		this.children = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	protected T self()
	{
		return (T)this;
	}

	public T attr(final String name, final String value)
	{
		this.attributes.put(name, value);
		return self();
	}

	public T body(final HtmlContent... children)
	{
		this.children.addAll(List.of(children));
		return self();
	}

	public String tagName()
	{
		return tagName;
	}

	@Override
	public void render(final PrintWriter writer)
	{
		writer.print('<');
		writer.print(tagName);

		if (!attributes.isEmpty())
		{
			writer.print(attributes.entrySet()
				.stream()
				.map(entry -> "%s=\"%s\"".formatted(entry.getKey(), entry.getValue()))
				.collect(Collectors.joining(" ", " ", " "))
			);
		}
		writer.print('>');
		children.forEach(child -> child.render(writer));
		writer.printf("</%s>", tagName);
	}
}
