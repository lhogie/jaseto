package jaseto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import toools.reflect.Introspector.JasetoField;

public class Demo2 {

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto();
			var json = jaseto.toJSON(List.of("salut", "luc"));
			jaseto.customizer = new Customizer() {

				@Override
				public String fieldName(JasetoField field, Object value, Object from) {
					if (field.getName().equals("nastyField")) {
						return null;
					}

					return field.getName();
				}

				@Override
				public void alterMap(Map<String, Node> keys, Object from) {
				}

				@Override
				public boolean considerBoxedAsPrimitives() {
					return true;
				}

				@Override
				public String className(Object o) {
					if (o instanceof String) {
						return "string";
					} else if (o instanceof Set) {
						return "set";
					} else if (o instanceof List) {
						return "list";
					}

					return o.getClass().getName();
				}

				@Override
				public String getClassNameKey() {
					return "#class";
				}

				@Override
				public String toString(Object o) {
					return o.toString();
				}

				@Override
				public Object substitute(Object o) {
					return o;
				}
			};

			System.out.println(json);
			Jaseto.validateGSON(json);
		} catch (StackOverflowError e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
