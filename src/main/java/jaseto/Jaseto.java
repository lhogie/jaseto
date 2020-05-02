package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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

		for (ClassDriver c : classDrivers.values())
		{
			if (c.support(o.getClass()))
			{
				return c;
			}
		}

		if (o.getClass().isArray())
		{
			return arrayDriver;
		}
		else
		{
			return introspectingDriver;
		}
	}

	static Driver getDriverFor(String qName, Attributes attr)
	{
		if (qName.equals("null"))
		{
			return new nullDriver();
		}
		else
		{
			String classname = attr.getValue("class");

			if (classname == null)
			{
				classname = qName;
			}

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
		print(o, os, new Registry(), new AttributeMap());
	}

	public static void print(Object o, PrintWriter w, Registry registry,
			AttributeMap attr)
	{
		if (registry == null)
			registry = new Registry();

		if (o != null && registry.contains(o))
		{
			w.print("<object id=\"");
			w.print(registry.id(o));
			w.print("\" ");
			w.print(attr);
			w.print(" />");
		}
		else
		{
			Driver d = getDriver(o);
			d.printXMLElement(o, w, registry, attr);

			if (attr.containsKey("id"))
			{
				registry.add(o);
			}

			d.printChildren(o, w, registry);
			d.endMark(w);
		}
	}

	public static class E
	{
		Driver driver;
		Object object;
		String name;
		Attributes attr;
		int nbChildren;
	}

	public static Object toObject(Reader os)
			throws ParserConfigurationException, SAXException, IOException
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();

		Stack<E> stack = new ObjectArrayList<>();
		Registry registry = new Registry();
		Object[] r = new Object[1];

		saxParser.parse(new InputSource(os), new DefaultHandler()
		{
			@Override
			public void startElement(String uri, String localName, String qName,
					Attributes attributes) throws SAXException
			{
				E element = new E();
				element.name = qName;
				element.attr = attributes;

				if (qName.equals("object") && attributes.getValue("class") == null
						&& attributes.getValue("id") != null)
				{
					int id = Integer.valueOf(attributes.getValue("id"));
					element.object = registry.get(id);
				}
				else
				{
					element.driver = getDriverFor(qName, attributes);
					element.object = element.driver.instantiate(qName, attributes, stack);
				}

				if ( ! stack.isEmpty())
				{
					E parent = stack.top();
					parent.driver.attachChild(parent.object, element, stack,
							parent.nbChildren);
					parent.nbChildren++;
				}

				stack.push(element);
			}

			@Override
			public void endElement(String uri, String localName, String qName)
					throws SAXException
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
