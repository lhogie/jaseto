package jaseto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import toools.reflect.Introspector.FF;

public class Demo {

	private static class DemoType {
		String aString = "bar";
		boolean loveIsAll = true;
		Boolean aBooleanObject = false;
		double pi = Math.PI;
		long maxLongValue = Long.MAX_VALUE;
		Object aNullReference = null;
		Object[] anArrayOfObjects = new Object[] { "Java", true, 9.8 };
		char[] anArrayOfPrimitiveValues = "abcdef".toCharArray();
		Object myself = this;
	}

	public static void main(String[] args) {
		try {
			var json = Jaseto.toJSON(new DemoType(), new SerializationController() {

				@Override
				public String fieldName(FF field) {
					if (field.getName().equals("nastyField")) {
						return null;
					}

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
				public String getClassName(Class<? extends Object> class1) {
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
			});
			
			json  = Jaseto.beautify(json);
			System.out.println(json);
		} catch (StackOverflowError e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
