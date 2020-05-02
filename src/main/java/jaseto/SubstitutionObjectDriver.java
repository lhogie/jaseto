package jaseto;

import java.io.PrintWriter;

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
	public void printChildren(Object o, PrintWriter w, Registry registry)
	{
		SubstitutionObject so = ((SubstitutionObject) o);

		for (String field : so.fields.keySet())
		{
			Object value = so.fields.get(o);
			AttributeMap attr = new AttributeMap();
			attr.put("field", field);
			Jaseto.print(value, w, registry, attr);
		}
	}

	@Override
	public void attachChild(Object parent, E child, Stack<E> stack, int childIndex)
	{
		String fieldName = child.attr.getValue("field");
		((SubstitutionObject) parent).fields.put(fieldName, child.object);
	}

	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
	}

	@Override
	public Class getHandledType()
	{
		return SubstitutionObject.class;
	}

}
