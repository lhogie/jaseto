package jaseto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import toools.reflect.Introspector.FF;

public class Demo {
	private static class TestType {
		String name = "coucou";
		boolean b = true;
		byte bb = 0x7;
		char c = 'c';
		float f = 0.1f;
		double d = Math.PI;
		short s = 4;
		int number = -1;
		long l = Long.MAX_VALUE;
		Object testNull = null;
		Object cycle = this;
		Object[] array = new Object[] { "Luc", true, this };
		boolean[] arrayBoolean = new boolean[] { true, false };
		String[] arrayString = new String[] { "Luc", "Elisa" };
	}

	private static class DemoType {
		String foo = "bar";
		boolean bool = true;
		Boolean boolObject = true;
		double pi = Math.PI;
		long l = Long.MAX_VALUE;
		Object aNullReference = null;
		Object aCyclicReference = this;
		Object[] array = new Object[] { "Java", true, this };
	}

	public static void main(String[] args) {
		try {
			System.out.println(Jaseto.toJSON(new TestType(), true, new SerializationController() {

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
					
					return class1.getName();				}

				@Override
				public String getClassNameKey() {
					return "#class";
				}

				@Override
				public String toString(Object o) {
					return o.toString();
				}
			}));
		} catch (StackOverflowError e) {
			System.err.println(e);
//			e.printStackTrace();
		}
	}
}
