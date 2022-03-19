package jaseto;

import toools.text.xml.DNode;

public class ObjectNode extends DNode {

	public ObjectNode(String name, Object o, Registry registry) {
		super(name);

		if (!registry.contains(o))
			registry.add(o, this);
	}

	public void setID(int id) {
		getAttributes().put("id", "" + id);
	}

	public void setClass(Class c) {
		getAttributes().put("class", c.getName());
	}

	public String getClassName() {
		return getAttributes().get("class");
	}
}
