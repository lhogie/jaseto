package jaseto;

import java.util.Collection;

public class CollectionNode extends ObjectNode {

	public CollectionNode(Object o, String name, Jaseto sc) {
		super(o, name, sc);
		setProperty("#class", sc.toJSON(o.getClass().getName()));
		setProperty("size", sc.toJSON(((Collection) o).size()));
		setProperty("elements", sc.toJSON(((Collection) o).toArray()));
	}
}
