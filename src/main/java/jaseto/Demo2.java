package jaseto;

import java.util.List;

import toools.reflect.Introspector.JasetoField;

public class Demo2 {

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto();
			var json = jaseto.toJSON(List.of("salut", "luc"));
			jaseto.customizer = new Customizer() {

				@Override
				public boolean treatBoxedAsPrimitives() {
					return true;
				}

					@Override
				public Object substitute(Object o) {
					return o;
				}

				@Override
				public boolean accept(JasetoField field, Object value, Object from) {
					return true;
				}

				@Override
				public void alter(ObjectNode n) {
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
