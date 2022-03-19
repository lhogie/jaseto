package jaseto;

import java.io.PrintWriter;
import java.util.function.Consumer;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;

public abstract class StringableDriver extends ClassDriver
{

	public abstract Object toObject(String s);

	@Override
	public String toString(Object o)
	{
		return o.toString();
	}

	@Override
	public Object instantiate(String qName, Attributes attributes, Stack<E> stack)
	{
		return toObject(attributes.getValue("value"));
	}

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		throw new IllegalStateException(stack.top().object + " shouldn't have child");
	}

	@Override
	public void forEachChildOf(Object o, Consumer c)
	{
	}
}
