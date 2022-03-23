package jaseto;

import java.util.Map;

import toools.reflect.Introspector.JasetoField;

public class DefaultCustomizer implements Customizer {

	@Override
	public String fieldName(JasetoField field, Object from) {
		return field.getName();
	}


	@Override
	public void alterMap(Map<String, Node> keys, Object from) {
	}

	@Override
	public String className(Object o) {
		return o.getClass().getName();
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


	@Override
	public boolean considerBoxedAsPrimitives() {
		return true;
	}

}
