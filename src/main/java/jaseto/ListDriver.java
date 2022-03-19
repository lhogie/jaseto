package jaseto;

import java.util.List;
import java.util.function.Consumer;

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
	public void forEachChildOf(Object parent, Consumer c)
	{
		for (Object child : (List) parent)
		{
			c.accept(child);
		}
	}

	@Override
	public Class getHandledType()
	{
		return List.class;
	}

	@Override
	public String toString(Object o)
	{
		return null;
	}

}
