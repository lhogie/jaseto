package jaseto;

import java.util.Map;

import toools.reflect.Introspector.JasetoField;

public class DefaultSerializationController implements Customizer {

	@Override
	public String fieldName(JasetoField field) {
		return field.getName();
	}


	@Override
	public void alterMap(Map<String, Node> keys, Object from) {
	}

	@Override
	public String className(Class<? extends Object> c) {
		return c.getName();
	}

	@Override
	public String toString(Object o) {
		return o.toString();
	}

	@Override
	public String getClassNameKey() {
		return "#class";
	}

	@Override
	public Object substitute(Object o) {
		return o;
	}

}
