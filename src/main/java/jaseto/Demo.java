package jaseto;

import java.util.ArrayList;
import java.util.Collection;
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
	}

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto();
			jaseto.customizer = new Customizer() {

				@Override
				public String fieldName(JasetoField field) {
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

			var json = jaseto.toJSON(new DemoType());
			System.out.println(json);
		} catch (StackOverflowError e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
