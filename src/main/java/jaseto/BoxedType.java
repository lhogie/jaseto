package jaseto;

public class BoxedType extends ObjectNode {

	public BoxedType(Object o, String name, Jaseto serializer) {
		super(o, name, serializer);
		map.put("value", new StringNode(o.toString(), "value", serializer));
	}

}
