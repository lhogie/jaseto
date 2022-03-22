package jaseto;

import java.util.Map;

import toools.reflect.Introspector.JasetoField;

public interface Customizer {
	String fieldName(JasetoField field);

	void alterMap(Map<String, Node> keys, Object from);

	String className(Class<? extends Object> c);

	String toString(Object o);

	public String getClassNameKey();

	Object substitute(Object o);
}
