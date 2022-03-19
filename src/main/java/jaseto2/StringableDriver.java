package jaseto2;

import toools.text.xml.DNode;

public abstract class StringableDriver extends Driver {
	public String toString(Object o) {
		return o.toString();
	}

	public abstract Object toObject(String s);

	@Override
	public final ObjectNode toNode(Object o, Registry registry) {
		ObjectNode n = new ObjectNode(getClassName(), o, registry);
		n.getAttributes().put("value", toString(o));
		return n;
	}

	protected abstract String getClassName();

	@Override
	public final Object toObject(DNode n) {
		return toObject(n.getAttributes().get("value"));
	}
}
