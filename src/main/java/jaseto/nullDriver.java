package jaseto;

import java.util.function.Consumer;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;

public class nullDriver extends Driver
{
	@Override
	protected String getTypeName(Object o)
	{
		return "null";
	}

	@Override
	public Object instantiate(String qName, Attributes attributes, Stack<E> stack)
	{
		return null;
	}

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		throw new IllegalStateException("shouldn't have children");
	}

	@Override
	public void forEachChildOf(Object o, Consumer c)
	{
	}

	@Override
	public String toString(Object o)
	{
		return null;
	}
}
