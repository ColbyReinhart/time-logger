package com.colbyreinhart.timelogger.template.lib;

public abstract class FormElement extends HtmlNode<FormElement>
{
	protected FormElement(final String tag)
	{
		super(tag);
	}

	public FormElement name(final String name)
	{
		attr("name", name);
		return this;
	}

	public FormElement value(final String value)
	{
		attr("value", value);
		return this;
	}
}
