package jaseto;

import java.lang.reflect.Array;

public class ArrayObjectNode extends ObjectNode {

	public ArrayObjectNode(Object o, String name, Jaseto serializer) {
		super(o, name, serializer);

		if (Array.getLength(o) > 0) {
			setProperty("elements", new ArrayNode(o, name, serializer));
		}
	}
}
