package jaseto2;

import java.lang.reflect.Array;

import toools.text.xml.DNode;

class ArrayDriver extends Driver
{

	@Override
	public ObjectNode toNode(Object o, Registry registry)
	{
		ObjectNode n = new ObjectNode("array", o, registry);

		Class type = o.getClass().getComponentType();
		n.getAttributes().put("componentType", type.getName());
		Driver driver = Jaseto.getDriver(type);
		int len = Array.getLength(o);

		if (driver instanceof StringableDriver)
		{
			StringableDriver sdriver = ((StringableDriver) driver);
			String value = "";

			for (int i = 0; i < len; ++i)
			{
				Object e = Array.get(o, i);

				if (e == null || e.getClass() != type)
					throw new IllegalStateException("" + e);

				value += sdriver.toString(e);

				if (i < len - 1)
				{
					value += ", ";
				}
			}

			n.getAttributes().put("value", value);

		}
		else
		{
			for (int i = 0; i < len; ++i)
			{
				Object e = Array.get(o, i);
				Jaseto.toNode(e, registry).setParent(n);
			}
		}

		return n;
	}

	@Override
	public Object toObject(DNode n)
	{
		return n.getAttributes().get("value");
	}

}
