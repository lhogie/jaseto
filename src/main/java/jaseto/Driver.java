package jaseto;

import toools.text.xml.DNode;

public abstract class Driver
{

	public abstract ObjectNode toNode(Object o, Registry registry);

	public abstract Object toObject(DNode n);

}
