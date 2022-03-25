package jaseto;

import java.util.Collection;

public class CollectionNode extends ArrayObjectNode {

	public CollectionNode(Object o, Jaseto sc) {
		super(((Collection) o).toArray(), sc);
	}
}
