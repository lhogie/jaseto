package jaseto;

import java.util.Collection;

public class CollectionNode extends ObjectNode {

	public CollectionNode(Object o, Jaseto sc) {
		super(o, sc);
		var c = (Collection) o;
		setProperty("size", sc.toNode(c.size()));
		setProperty("elements", sc.toNode(c.toArray()));
	}
}