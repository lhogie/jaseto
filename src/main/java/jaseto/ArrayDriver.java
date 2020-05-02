package jaseto;

import java.io.PrintWriter;
import java.lang.reflect.Array;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import jaseto.Jaseto.E;
import toools.reflect.Clazz;

class ArrayDriver extends Driver
{
	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
		attr.put("class", o.getClass().getComponentType().getName() + "[]");
		attr.put("length", "" + Array.getLength(o));
	}

	@Override
	public void printChildren(Object array, PrintWriter w, Registry registry)
	{
		Class componentType = array.getClass().getComponentType();
		Driver containedTypeDriver = Jaseto.getDriver(componentType);
		int len = Array.getLength(array);

		if (containedTypeDriver instanceof StringableDriver)
		{
			StringableDriver sdriver = ((StringableDriver) containedTypeDriver);
			String value = "";

			for (int i = 0; i < len; ++i)
			{
				Object e = Array.get(array, i);

				if (e == null || e.getClass() != componentType)
					throw new IllegalStateException("not possible: " + e);

				value += sdriver.toString(e);
				
				if (i < len - 1)
				{
					value += ", ";
				}
			}

			w.print(" value='" + value + "'");
		}
		else
		{
			for (int i = 0; i < len; ++i)
			{
				Object e = Array.get(array, i);
				Jaseto.print(e, w, registry, new AttributeMap());
			}
		}

	}

	@Override
	public Object instantiate(String qName, Attributes attributes, Stack<E> stack)
	{
		String classname = attributes.getValue("class");
		Class componentType = Clazz
				.findClass(classname.substring(0, classname.length() - 2));
		int length = Integer.valueOf(attributes.getValue("length"));
		return Array.newInstance(componentType, length);
	}


	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		Array.set(parent, childIndex, child.object);
	}

}
