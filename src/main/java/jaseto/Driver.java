package jaseto;

import java.io.PrintWriter;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;
import toools.reflect.Clazz;

public abstract class Driver
{
	public final void printXMLElement(Object o, PrintWriter w, Registry registry,
			AttributeMap attr)
	{
		if (o != null)
		{
			attr.put("id", "" + registry.id(o));
			attr.put("class", o.getClass().getName());
		}
		
		adaptAttributes(attr, o);
		w.print("<" + getElementName() + " " + attr + ">");
	}

	protected abstract void adaptAttributes(AttributeMap attr, Object o);

	protected String getElementName()
	{
		return "object";
	}

	public abstract void printChildren(Object o, PrintWriter w, Registry registry);

	protected void endMark(PrintWriter w)
	{
		w.print("</" + getElementName() + ">");
	}

	public Object instantiate(String qName, Attributes attributes, Stack<E> stack)
	{
		String classname = attributes.getValue("class");
		Class clazz = Clazz.findClassOrFail(classname);
		Object o = Clazz.makeInstanceOrFail(clazz);
		return o;
	}

	public abstract void attachChild(Object parent, E child, Stack<E> stack, int childIndex);
}
