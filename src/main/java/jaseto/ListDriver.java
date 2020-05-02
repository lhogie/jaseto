package jaseto;

import java.io.PrintWriter;
import java.util.List;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;

public class ListDriver extends ClassDriver
{

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		((List) parent).add(child.object);
	}

	@Override
	public void printChildren(Object parent, PrintWriter w, Registry registry)
	{
		for (Object c : (List) parent)
		{
			Jaseto.print(c, w, registry, new AttributeMap());
		}
	}

	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
	}

	@Override
	public Class getHandledType()
	{
		return List.class;
	}

}
