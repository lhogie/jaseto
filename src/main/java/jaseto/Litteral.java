package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import toools.text.TextUtilities;

public class Litteral extends Node {
	String value;

	 static Set<Class> toStringable = Set.of(Boolean.class, Byte.class, Character.class, Short.class,
				Integer.class, Long.class, Float.class, Double.class, String.class, StringBuffer.class);

	public Litteral(Object o, String value, Jaseto serializer) {
		super(o, serializer);
		this.value = o == null ? null : value;
	}

	public Litteral(Object o, Jaseto serializer) {
		this(o, o.toString(), serializer);
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
