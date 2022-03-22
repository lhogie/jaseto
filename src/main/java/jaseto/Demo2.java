package jaseto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import toools.reflect.Introspector.JastoField;

public class Demo2 {

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto();
			var json = jaseto.toJSON(List.of("salut", "luc"));
			jaseto.customizer= new SerializationController() {

				@Override
				public String fieldName(JastoField field) {
					if (field.getName().equals("nastyField")) {
						return null;
					}

					return field.getName();
				}

				@Override
				public void alterMap(Map<String, Node> keys, Object from) {
				}

				@Override
				public String className(Class<? extends Object> class1) {
					if (class1 == String.class) {
						return "string";
					} else if (class1 == Set.class) {
						return "set";
					} else if (class1 == List.class) {
						return "list";
					}

					return class1.getName();
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
