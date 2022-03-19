package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import jaseto.Parser.H;
import toools.reflect.Clazz;

public class Jaseto
{
	private static final Map<Class, ClassDriver> classDrivers = new HashMap<>();

	static
	{
		registerClassDriver(int.class, new intDriver());
		registerClassDriver(Integer.class, new intDriver());

		registerClassDriver(boolean.class, new booleanDriver());
		registerClassDriver(Boolean.class, new booleanDriver());

		registerClassDriver(byte.class, new byteDriver());
		registerClassDriver(Byte.class, new byteDriver());

		registerClassDriver(char.class, new charDriver());
		registerClassDriver(Character.class, new charDriver());

		registerClassDriver(short.class, new shortDriver());
		registerClassDriver(Short.class, new shortDriver());

		registerClassDriver(long.class, new longDriver());
		registerClassDriver(Long.class, new longDriver());

		registerClassDriver(float.class, new floatDriver());
		registerClassDriver(Float.class, new floatDriver());

		registerClassDriver(double.class, new doubleDriver());
		registerClassDriver(Double.class, new doubleDriver());

		registerClassDriver(String.class, new StringDriver());
		registerClassDriver(char[].class, new CharArrayDriver());
		registerClassDriver(List.class, new ListDriver());
		registerClassDriver(SubstitutionObject.class, new SubstitutionObjectDriver());
	}

	public static void registerClassDriver(Class c, ClassDriver d)
	{
		classDrivers.put(c, d);
	}

	static final Driver arrayDriver = new ArrayDriver();
	static final Driver introspectingDriver = new IntrospectingDriver();

	static Driver getDriver(Object o)
	{
		if (o == null)
			return new nullDriver();

		return getDriver(o.getClass());
	}

	static Driver getDriver(Class c)
	{
		if (c.isPrimitive())
			return classDrivers.get(c);

		Class primitiveEquivalent = Clazz.class_primitives.get(c);

		if (primitiveEquivalent != null)
			return classDrivers.get(primitiveEquivalent);

		// some specific driver ?
		for (ClassDriver cd : classDrivers.values())
		{
			if (cd.support(c))
			{
				return cd;
			}
		}

		if (c.isArray())
		{
			return arrayDriver;
		}
		else
		{
			// no specific driver. Introspect then.
			return introspectingDriver;
		}
	}

	static Driver getDriverFor(String classname)
	{
		if (classname.equals("null"))
		{
			return new nullDriver();
		}
		else
		{
			Class c = Clazz.findClass(classname);

			if (c == null)
			{
				c = SubstitutionObject.class;
			}

			for (ClassDriver d : classDrivers.values())
			{
				if (d.support(c))
				{
					return d;
				}
			}

			if (classname.endsWith("[]"))
			{
				return arrayDriver;
			}

			return introspectingDriver;
		}
	}

	public static void print(Object o, PrintWriter os)
	{
		getDriver(o).print(o, os, new Registry(), 0);
	}

	public static class E
	{
		Driver driver;
		Object object;
		String name;
		String fieldName;
		int nbChildren;
	}

	public static Object toObject(Reader os)
			throws ParserConfigurationException, SAXException, IOException
	{
		Parser saxParser = new Parser();

		Stack<E> stack = new ObjectArrayList<>();
		Registry registry = new Registry();
		Object[] r = new Object[1];

		saxParser.parse(os, new H()
		{
			@Override
			public void start(String line)
			{
				E element = new E();
				

				// just the ID or null
				if (a.length == 1)
				{
					if (a[0].equals("null"))
					{
						element.object = null;
					}
					else
					{
						int id = Integer.valueOf(a[0]);
						element.object = registry.get(id);
					}
				}
				else
				{
					if (stack.isEmpty())
					{
						String classname = a[0];
						int id = Integer.valueOf(a[1]);
						element.driver = getDriverFor(classname);
						element.object = element.driver.instantiate(classname, stack);
						stack.push(element);
					}
					else
					{
						E parent = stack.top();
						parent.driver.attachChild(parent.object, element, stack,
								parent.nbChildren);
						parent.nbChildren++;
					}
				}
			}

			@Override
			public void end()
			{
				r[0] = stack.pop().object;
			}

		});

		return r[0];
	}

	public static Class forName(String fullQualifiedName, boolean allowSubtitutionTypes)
	{
		Class c = Clazz.findClass(fullQualifiedName);

		// the class was found
		if (c != null)
			return c;

		// it was not found but cannot being substituted
		if ( ! allowSubtitutionTypes)
			return null;

		String className, packageName;
		int p = fullQualifiedName.lastIndexOf('.');

		if (p == - 1)
		{
			packageName = null;
			className = fullQualifiedName;
		}
		else
		{
			packageName = fullQualifiedName.substring(0, p);
			className = fullQualifiedName.substring(p);
		}

		String java = "";

		if (packageName != null)
		{
			java += "package " + packageName + ";\n\n";
		}

		java += "public class " + className + " extends "
				+ SubstitutionObject.class.getName() + "{}";

		return Clazz.toClass(java, fullQualifiedName);
	}

}
