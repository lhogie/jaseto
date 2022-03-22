package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.JastoField;

public class IntrospectingMapNode extends MapNode {

	public IntrospectingMapNode(Object o, Jaseto serializer) {
		super(o, serializer);

		for (JastoField field : Introspector.getIntrospector(o.getClass()).getFields()) {
			if (!field.isStatic() && !field.isTransient()) {
				String newFieldName = serializer.customizer.fieldName(field);

				if (newFieldName != null) {
					Class<? extends Node> fieldNodeClass = serializer.lookupNodeClass(field.getType());
					var childNode = serializer.toNode(field.get(o), fieldNodeClass);
					add(field.getName(), childNode);
				}
			}
		}

		serializer.customizer.alterMap(children, o);
	}

}
