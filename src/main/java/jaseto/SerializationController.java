package jaseto;

import java.util.Map;

import toools.reflect.Introspector.JastoField;

public interface SerializationController {
	String fieldName(JastoField field);

	void alterMap(Map<String, Node> keys, Object from);

	String className(Class<? extends Object> c);

	String toString(Object o);

	public String getClassNameKey();

	Object substitute(Object o);
}
