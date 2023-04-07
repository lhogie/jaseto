package jaseto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import toools.reflect.Introspector.JasetoField;

public class Demo {

	private static class DemoType {
		boolean loveIsAll = true;
		double pi = Math.PI;
		long maxLongValue = Long.MAX_VALUE;
		Object myself = this;
		Boolean aBooleanObject = false;
		Object aNullReference = null;
		Object[] anArray = new Object[] { "Java", true, 9.8 };
		Collection aList = new ArrayList<>(List.of("Hello", "you", anArray));
		Map aMap = Map.of("key1", "value1", "key2", "value2");
	}

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto();
			jaseto.customizer = new Customizer() {

				@Override
				public boolean treatBoxedAsPrimitives() {
					return true;
				}

				@Override
				public Node alter(Node n) {
					return n;
//					n.renameKey("#class", "###type");
				}

			
				@Override
				public Object substitute(Object o) {
					//if (o != null&&o.getClass().isArray())return new ArrayList();
					return o;
				}

				@Override
				public boolean accept(JasetoField field, Object value, Object from) {
					return true;
				}
			};

			var json = jaseto.toJSON(new DemoType());
			Jaseto.gson_parse(json);
			System.out.println(json);
		} catch (StackOverflowError e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
