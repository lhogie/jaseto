package jaseto;

import java.io.IOException;
import java.io.Writer;

public class StringNode extends Node {
	private String value;

	public StringNode(Object o, Registry registry, SerializationController sc) {
		this.value = sc.toString(o);
	}

	@Override
	public void toJSON(Writer w) throws IOException {
		if (value.matches("[-A-Z-a-z0-9]+")) {
			w.write(value);
		} else {
			w.write('"');
			w.write(value);
			w.write('"');
		}
	}
}
