package jaseto;

import java.io.PrintWriter;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;

public abstract class StringableDriver extends ClassDriver
{

	public abstract Object toObject(String s);

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
	public void printChildren(Object o, PrintWriter w, Registry registry)
	{

	}

	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
		attr.put("value", toString(o));
	}
}
