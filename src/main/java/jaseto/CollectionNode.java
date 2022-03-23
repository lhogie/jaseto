package jaseto;

import java.util.Collection;

public class CollectionNode extends ArrayNode2 {

	public CollectionNode(Object o, Jaseto sc) {
		super(((Collection) o).toArray(), sc);
		className = o.getClass().getName();
	}
}
