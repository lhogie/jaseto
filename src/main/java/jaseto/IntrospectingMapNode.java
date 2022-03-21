package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.FF;

public class IntrospectingMapNode extends MapNode {

	public IntrospectingMapNode(Object o, Registry registry, SerializationController sc) {
		super(o, registry, sc);

		for (FF field : Introspector.getIntrospector(o.getClass()).getFields()) {
			if (!field.isStatic() && !field.isTransient()) {
				String newFieldName = sc.fieldName(field);

				if (newFieldName != null) {
					Class<? extends Node> fieldNodeClass = Jaseto.lookupNodeClass(field.getType());
					var childNode = Jaseto.toNode(field.get(o), fieldNodeClass, registry, sc);
					children.put(field.getName(), childNode);
				}
			}
		}

		sc.addKeys(children, o);
	}
}
