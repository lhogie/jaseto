package jaseto;

import java.io.PrintWriter;
import java.util.function.Consumer;

import org.xml.sax.Attributes;

import it.unimi.dsi.fastutil.Stack;
import jaseto.Jaseto.E;

public class SubstitutionObjectDriver extends ClassDriver
{
	@Override
	public Object instantiate(String qName, Attributes attributes, Stack<E> stack)
	{
		return new SubstitutionObject(attributes.getValue("class"));
	}

	@Override
	public void forEachChildOf(Object o, Consumer c)
	{
		SubstitutionObject so = ((SubstitutionObject) o);

		for (String field : so.fields.keySet())
		{
			Object value = so.fields.get(o);
			AttributeMap attr = new AttributeMap();
			attr.put("field", field);
			c.accept(value);
		}
	}

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		String fieldName = child.attr.getValue("field");
		((SubstitutionObject) parent).fields.put(fieldName, child.object);
	}

	@Override
	public Class getHandledType()
	{
		return SubstitutionObject.class;
	}

	@Override
	public String toString(Object o)
	{
		return null;
	}

}
