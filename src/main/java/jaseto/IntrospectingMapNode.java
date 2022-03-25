package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.JasetoField;

public class IntrospectingMapNode extends ObjectNode {

	public IntrospectingMapNode(Object o, Jaseto serializer) {
		super(o, serializer);

		for (JasetoField field : Introspector.getIntrospector(o.getClass(), w -> System.err.println(w.getMessage()))
				.getFields()) {
			if (!field.isStatic() && !field.isTransient()) {
				var value = field.get(o);
				String newFieldName = serializer.customizer.fieldName(field, value, o);

				var t = field.getType().isPrimitive() || value == null ? field.getType() : value.getClass();

				if (newFieldName != null) {
					Class<? extends Node> fieldNodeClass = serializer.lookupNodeClass(t);
					var childNode = serializer.toNode(value, fieldNodeClass);
					addKey(field.getName(), childNode);
				}
			}
		}

		serializer.customizer.alterMap(map, o);
	}

}
