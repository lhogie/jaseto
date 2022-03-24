package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.JasetoField;

public class IntrospectingMapNode extends MapBasedNode {

	public IntrospectingMapNode(Object o, Jaseto serializer) {
		super(o, serializer);
System.err.println("introspecting  " + o.getClass());
		for (JasetoField field : Introspector.getIntrospector(o.getClass(), w -> System.err.println(w.getMessage())).getFields()) {
			if (!field.isStatic() && !field.isTransient()) {
				var value = field.get(o);
				String newFieldName = serializer.customizer.fieldName(field, value, o);

				if (newFieldName != null) {
					Class<? extends Node> fieldNodeClass = serializer.lookupNodeClass(value == null ? Object.class : value.getClass());
					var childNode = serializer.toNode(value, fieldNodeClass);
					add(field.getName(), childNode);
				}
			}
		}

		serializer.customizer.alterMap(map, o);
	}

}
