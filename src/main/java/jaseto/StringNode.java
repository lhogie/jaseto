package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

import toools.text.TextUtilities;

public  class StringNode extends Node {
	String value;

	public StringNode(Object o, String name, Function<Object, String> f, Jaseto serializer) {
		super(o, name);
		this.value = o == null ? null : f.apply(o);
	}

	public StringNode(Object o, String name, Jaseto serializer) {
		this(o, name, a -> a.toString(), serializer);
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
