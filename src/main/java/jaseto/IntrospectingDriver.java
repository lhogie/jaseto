package jaseto;

import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;
import toools.reflect.Clazz;
import toools.reflect.Introspector;
import toools.reflect.Introspector.FF;

public class IntrospectingDriver extends Driver
{

	@Override
	public void printChildren(Object o, PrintWriter w, Registry registry)
	{
		for (FF field : fields(o.getClass()))
		{
			Object value = field.get(o);
			AttributeMap attr = new AttributeMap();
			attr.put("field", field.getName());
			Jaseto.print(value, w, registry, attr);
		}
	}

	protected Iterable<FF> fields(Class c)
	{
		List<FF> r = new ArrayList<>();

		for (FF field : Introspector.getIntrospector(c).getFields().values())
		{
			if ( ! field.is(Modifier.STATIC) && ! field.is(Modifier.TRANSIENT))
			{
				r.add(field);
			}
		}

		return r;
	}

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		String fieldName = child.attr.getValue("field");
		Clazz.setFieldValue(parent, fieldName, child.object);
	}

	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
	}

}
