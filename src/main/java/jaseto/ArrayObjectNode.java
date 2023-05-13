package jaseto;

import java.lang.reflect.Array;

public class ArrayObjectNode extends ObjectNode {

	public ArrayObjectNode(Object o, Jaseto serializer) {
		super(o, serializer);
		var len = Array.getLength(o);

		setProperty("size", new Litteral(len, serializer));

		if (len > 0) {
			setProperty("elements", new ArrayNode(o, serializer));
		}
	}
}
