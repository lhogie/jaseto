package jaseto;

import java.util.Set;

public class JustAValueType extends ObjectNode {
	 static Set<Class> knownTypes = Set.of(Boolean.class, Byte.class, Character.class, Short.class,
			Integer.class, Long.class, Float.class, Double.class);

	public JustAValueType(Object o, String name, Jaseto jaseto) {
		super(o, name, jaseto);
		setProperty("value", jaseto.toJSON(o.toString()));
	}
}
