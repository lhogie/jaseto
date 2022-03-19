package jaseto;

import toools.text.xml.DNode;

public class nullDriver extends Driver
{

	@Override
	public ObjectNode toNode(Object o, Registry registry)
	{
		ObjectNode n = new ObjectNode("null", null, registry);
		return n;
	}

	@Override
	public Object toObject(DNode n)
	{
		return null;
	}

}
