package jaseto;

public class BoxedType extends MapBasedNode {

	public BoxedType(Object o, Jaseto serializer) {
		super(o, serializer);
		var n = o == null ? new NullNode() : new StringNode(o.toString(), serializer);
		map.put("value", n);
	}
}
