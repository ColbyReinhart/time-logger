package com.colbyreinhart.timelogger.template.lib;

public class Button extends FormElement
{
	public enum Type { BUTTON, SUBMIT, RESET }

	public Button()
	{
		super("button");
	}

	public Button disabled(final boolean disabled)
	{
		attr("disabled", disabled ? "true": "false");
		return this;
	}

	public Button type(final Type type)
	{
		attr("type", type.name().toLowerCase());
		return this;
	}
}
