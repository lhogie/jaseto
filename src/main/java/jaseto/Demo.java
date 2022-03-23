package jaseto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import toools.reflect.Introspector.JasetoField;

public class Demo {

	private static class DemoType {
		String aString = "bar";
		boolean loveIsAll = true;
		Boolean aBooleanObject = false;
		double pi = Math.PI;
		long maxLongValue = Long.MAX_VALUE;
		Object aNullReference = null;
		Object[] anArray = new Object[] { "Java", true, 9.8, this };
		Collection aList = new ArrayList<>(List.of("Hello", "you"));
		Object myself = this;
		Map aMap = new HashMap<>();

		DemoType() {
			aMap.put("1+1", "2");
		}
	}

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto();
			jaseto.customizer = new Customizer() {

				@Override
				public String fieldName(JasetoField field, Object from) {
					if (field.getName().equals("nastyField")) {
						return null;
					}

					return field.getName();
				}

				@Override
				public void alterMap(Map<String, Node> keys, Object from) {
				}

				@Override
				public String className(Object o) {
					if (o instanceof String) {
						return "string";
					} else if (o instanceof  Set) {
						return "set";
					} else if (o instanceof  List) {
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

			var json = jaseto.toJSON(new DemoType());
			System.out.println(json);
		} catch (StackOverflowError e) {
			System.err.println(e);
			e.printStackTrace();
		}

		System.out.println(System.getProperty("java.home"));
	}
}
