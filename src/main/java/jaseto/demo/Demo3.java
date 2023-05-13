package jaseto.demo;

import java.util.List;

import jaseto.Jaseto;
import jaseto.Node;

public class Demo3 {

	public static void main(String[] args) {
		try {
			var jaseto = new Jaseto() {

				@Override
				public Node alter(Node n) {
					return n;
				}
			};

			var json = jaseto.toNode(List.of("salut", "luc")).toJSON();
			System.out.println(json);
			Jaseto.validateByGSON(json);
		} catch (StackOverflowError e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
