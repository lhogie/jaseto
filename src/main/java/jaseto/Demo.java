package jaseto;

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
		}
		catch (StackOverflowError e) {
			System.err.println(e);
//			e.printStackTrace();
		}
	}
}
