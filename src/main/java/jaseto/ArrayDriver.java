package jaseto;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.function.Consumer;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;
import toools.reflect.Clazz;

class ArrayDriver extends Driver
{

	@Override
	public void forEachChildOf(Object array, Consumer c)
	{
		int len = Array.getLength(array);

		for (int i = 0; i < len; ++i)
		{
			Object e = Array.get(array, i);
			c.accept(e);
		}
	}

	@Override
	public String toString(Object array)
	{
		Class componentType = array.getClass().getComponentType();
		Driver driver = Jaseto.getDriver(componentType);

		if ( ! (driver instanceof StringableDriver))
			return null;

		StringableDriver sd = (StringableDriver) driver;
		int len = Array.getLength(array);
		String s = "";

		for (int i = 0; i < len; ++i)
		{
			if (i > 0)
				s += ", ";

			Object child = Array.get(array, i);

			if (child == null)
				return null;

			if (child.getClass() != componentType
					&& Clazz.class_primitives.get(child.getClass()) != componentType)
				return null;

			String cs = sd.toString(child);

			if (cs.contains(","))
				return null;

			s += cs;
		}

		return s;
	}

	@Override
	public Object instantiate(String classname, String value, Stack<E> stack)
	{
		Class componentType = Clazz
				.findClass(classname.substring(0, classname.length() - 2));

		if (value == null)
		{
			int length = Integer.valueOf(attributes.getValue("length"));
			return Array.newInstance(componentType, length);
		}
		else
		{
			StringableDriver d = (StringableDriver) Jaseto.getDriver(componentType);
			String[] ss = value.split(",");
			Object array = Array.newInstance(componentType, ss.length);

			for (int i = 0; i < ss.length; ++i)
			{
				Object child = d.toObject(ss[i]);
				Array.set(array, i, child);
			}

			return array;
		}
	}

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		Array.set(parent, childIndex, child.object);
	}
	
	 

}
