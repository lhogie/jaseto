package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

import toools.text.TextUtilities;

public class StringNode extends Node {
	private String value;

	public StringNode(Object o, Registry registry, SerializationController sc) {
		this.value = o == null ? null : sc.toString(o);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		if (value == null) {
			w.print("null");
		} else if (TextUtilities.isDouble(value) || TextUtilities.isInt(value) || value.equals("true")
				|| value.equals("false")) {
			w.print(value);
		} else {
			w.print('"');
			w.print(value);
			w.print('"');
		}
	}
}
