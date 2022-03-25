package jaseto;

import java.lang.reflect.Array;

public class ArrayObjectNode extends ObjectNode {

	public ArrayObjectNode(Object o, Jaseto serializer) {
		super(o, serializer);
		addKey("size", new StringNode(Array.getLength(o), serializer) );
		addKey("elements", new ArrayNode(o, serializer));
	}

}
