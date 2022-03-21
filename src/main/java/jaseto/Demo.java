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
	
	private static class TestType2 {
		boolean b = true;
		String name = "coucou";
	}


	public static void main(String[] args) {
		try {
			System.out.println(Jaseto.toNode(new TestType(), new DefaultSerializationController()));

			System.out.println(Jaseto.toNode(new TestType(), new SerializationController() {
				
				@Override
				public String toString(Object o) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public boolean serializeArrayElement(Object array, int i, Object element) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public String fieldName(FF field) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String classNameAlias(Class<? extends Object> class1) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void addKeys(Map<String, Node> keys, Object from) {
					// TODO Auto-generated method stub
					
				}
			};()));
}
		catch (StackOverflowError e) {
			System.err.println(e);
//			e.printStackTrace();
		}
	}
}
