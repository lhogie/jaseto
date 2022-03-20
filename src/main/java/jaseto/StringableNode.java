package jaseto;

import java.io.IOException;
import java.io.Writer;

public abstract class StringableNode extends Node {
	private char[] value;

	public abstract Object toObject(String s);

	@Override
	public void fill(Object o, Registry registry) {
		super.fill(o, registry);
		this.value = o.toString().toCharArray();
	}

	@Override
	public void toJSON(Writer w) throws IOException {
		w.write('"');
		w.write(value);
		w.write('"');
	}
}
