package jaseto;

import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;
import toools.reflect.Clazz;
import toools.reflect.Introspector;
import toools.reflect.Introspector.FF;

public class IntrospectingDriver extends Driver
{

	@Override
	public void forEachChildOf(Object parent, Consumer c)
	{
		for (FF field : Introspector.getIntrospector(parent.getClass()).getFields()
				.values())
		{
			if ( ! field.is(Modifier.STATIC) && ! field.is(Modifier.TRANSIENT))
			{
				Object child = field.get(parent);
				AttributeMap attr = new AttributeMap();
				attr.put("field", field.getName());
				c.accept(child);
			}
		}
	}

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		Clazz.setFieldValue(parent, child.fieldName, child.object);
	}

	@Override
	public String toString(Object o)
	{
		return null;
	}

}
