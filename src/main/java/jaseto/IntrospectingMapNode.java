package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.AField;

public class IntrospectingMapNode extends ObjectNode {

	public IntrospectingMapNode(Object o, Jaseto j) {
		super(o, j);

		var allFields = Introspector.getIntrospector(o.getClass(), w -> System.err.println(w.getMessage())).getFields();

		for (AField field : allFields) {
			if (!field.isStatic() && !field.isTransient()) {
				var value = field.get(o);

				if (j.accept(o, field, value)) {
					setProperty(field.getName(), j.toNode(value));
				}
			}
		}
	}

}
