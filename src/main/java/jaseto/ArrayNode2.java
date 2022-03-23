package jaseto;

import java.lang.reflect.Array;

public class ArrayNode2 extends MapNode {

	public ArrayNode2(Object o, Jaseto serializer) {
		super(o, serializer);

		this.className = o.getClass().getComponentType().getName() + "[]";
		Class<? extends Node> componentTypeNodeClass = serializer.lookupNodeClass(o.getClass().getComponentType());
		int len = Array.getLength(o);

		boolean stringed = StringNode.class.isAssignableFrom(componentTypeNodeClass);

		for (int i = 0; i < len; ++i) {
			var e = Array.get(o, i);
			var nc = stringed ? componentTypeNodeClass : serializer.lookupNodeClass(e.getClass());
			add("" + i, serializer.toNode(e, nc));
		}
	}

}
