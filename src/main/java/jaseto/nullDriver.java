package jaseto;

import java.io.PrintWriter;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;

public class nullDriver extends Driver
{
	@Override
	public Object instantiate(String qName, Attributes attributes, Stack<E> stack)
	{
		return null;
	}

	@Override
	protected String getElementName()
	{
		return "null";
	}
	
	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		throw new IllegalStateException("shouldn't have children");
	}

	@Override
	public void printChildren(Object o, PrintWriter w, Registry registry)
	{
	}

	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
		attr.remove("id");
	}
}
