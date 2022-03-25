package jaseto;

import java.util.Collection;

public class CollectionNode extends ArrayObjectNode {

	public CollectionNode(Object o, String name, Jaseto sc) {
		super(((Collection) o).toArray(), name, sc);
	}
}
