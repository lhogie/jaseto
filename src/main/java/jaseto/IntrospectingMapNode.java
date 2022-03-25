package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.JasetoField;

public class IntrospectingMapNode extends ObjectNode {

	public IntrospectingMapNode(Object o, String name, Jaseto serializer) {
		super(o, name, serializer);

		for (JasetoField field : Introspector.getIntrospector(o.getClass(), w -> System.err.println(w.getMessage()))
				.getFields()) {
			if (!field.isStatic() && !field.isTransient()) {
				var value = field.get(o);

				if (serializer.customizer.accept(field, value, o)) {
					var t = field.getType().isPrimitive() || value == null ? field.getType() : value.getClass();
					Class<? extends Node> fieldNodeClass = serializer.lookupNodeClass(t);
					var childNode = serializer.toNode(value, field.getName(), fieldNodeClass);
					putKey(field.getName(), childNode);
				}
			}
		}
	}

}
