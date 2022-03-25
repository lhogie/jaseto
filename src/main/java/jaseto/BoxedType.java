package jaseto;

public class BoxedType extends ObjectNode {

	public BoxedType(Object o, Jaseto serializer) {
		super(o, serializer);
		var n = o == null ? new NullNode() : new StringNode(o.toString(), serializer);
		map.put("value", n);
	}
}
