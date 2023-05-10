package jaseto.demo;

import java.util.List;

import jaseto.DefaultCustomizer;
import jaseto.Jaseto;
import jaseto.Node;
import toools.reflect.Introspector.AField;

public class Demo2 {

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto();
			var json = jaseto.toJSON(List.of("salut", "luc")).toJSON();
			jaseto.customizer = new DefaultCustomizer() {

				@Override
				public boolean treatBoxedAsPrimitives() {
					return true;
				}

				@Override
				public Object substitute(Object o) {
					return o;
				}

				@Override
				public boolean accept(AField field, Object value, Object from) {
					return true;
				}

				@Override
				public Node alter(Node n) {
					return n;
				}
			};

			System.out.println(json);
			Jaseto.validateByGSON(json);
		} catch (StackOverflowError e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
