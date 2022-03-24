package jaseto;

import java.util.Map;

import toools.reflect.Introspector.JasetoField;

public interface Customizer {
	Object substitute(Object o);

	String fieldName(JasetoField field, Object value, Object from);

	void alterMap(Map<String, Node> keys, Object from);

	String className(Object o);

	String toString(Object o);

	public String getClassNameKey();

	boolean considerBoxedAsPrimitives();

}
