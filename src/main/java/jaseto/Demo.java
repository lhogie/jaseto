package jaseto;

import java.util.Map;

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

	private static class TestType2 {
		boolean b = true;
		String name = "coucou";
	}

	public static void main(String[] args) {
		try {
			System.out.println(Jaseto.toJSON(new TestType(), true, new SerializationController() {

				@Override
				public String fieldName(FF field) {
					return null;
				}

				@Override
				public boolean serializeArrayElement(Object array, int i, Object element) {
					return false;
				}

				@Override
				public void addKeys(Map<String, Node> keys, Object from) {
				}

				@Override
				public String getClassName(Class<? extends Object> class1) {
					return null;
				}

				@Override
				public String getClassNameKey() {
					return "#class";
				}

				@Override
				public String toString(Object o) {
					return null;
				}
			}));
		} catch (StackOverflowError e) {
			System.err.println(e);
//			e.printStackTrace();
		}
	}
}
