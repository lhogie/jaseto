package jaseto;

import java.util.Map;

import toools.reflect.Introspector.FF;

public class DefaultSerializationController implements SerializationController {

	@Override
	public String fieldName(FF field) {
		return field.getName();
	}

	@Override
	public boolean serializeArrayElement(Object array, int i, Object element) {
		return true;
	}

	@Override
	public void addKeys(Map<String, Node> keys, Object from) {
	}

	@Override
	public String classNameAlias(Class<? extends Object> c) {
		return c.getName();
	}

	@Override
	public String toString(Object o) {
		return o.toString();
	}

}
