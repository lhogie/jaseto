package jaseto;

public class ArrayObjectNode extends ObjectNode {

	public ArrayObjectNode(Object o, String name, Jaseto serializer) {
		super(o, name, serializer);
		putKey("elements", new ArrayNode(o, name, serializer));
	}

}
