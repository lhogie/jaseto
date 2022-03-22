package jaseto;

import java.util.Map;

import toools.reflect.Introspector.FF;

public interface SerializationController {
	String fieldName(FF field);

	boolean serializeArrayElement(Object array, int i, Object element);

	void addKeys(Map<String, Node> keys, Object from);

	String getClassName(Class<? extends Object> class1);

	String toString(Object o);

	public String getClassNameKey();

	Object substitute(Object o);
}
