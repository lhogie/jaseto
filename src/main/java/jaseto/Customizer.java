package jaseto;

import toools.reflect.Introspector.JasetoField;

public interface Customizer {
	Object substitute(Object o);

	boolean accept(JasetoField field, Object value, Object from);

	boolean treatBoxedAsPrimitives();

	void alter(ObjectNode n);

}
