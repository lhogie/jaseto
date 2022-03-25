package jaseto;

import java.lang.reflect.Array;

public class ObjectizedArrayNode extends ObjectNode {

	public ObjectizedArrayNode(Object o, Jaseto serializer) {
		super(o, serializer);
		map.put("size", new StringNode(Array.getLength(o), serializer) );
		map.put("elements", new ArrayNode(o, serializer));
	}

}
