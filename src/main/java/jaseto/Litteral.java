package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

import toools.text.TextUtilities;

public class Litteral extends Node {
	String value;

	public Litteral(Object o, String name, String value, Jaseto serializer) {
		super(o, name, serializer);
		this.value = o == null ? null : value;
	}

	public Litteral(Object o, String name, Jaseto serializer) {
		this(o, name, o.toString(), serializer);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		if (value == null) {
			w.print("null");
		} else if (TextUtilities.isDouble(value) || TextUtilities.isLong(value) || value.equals("true")
				|| value.equals("false")) {
			w.print(value);
		} else {
			w.print('"');
			w.print(value);
			w.print('"');
		}
	}
}
