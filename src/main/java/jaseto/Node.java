package jaseto;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import toools.text.json.JSONUtils;

public class Node {
	public Class clazz;

	public void fill(Object o, Registry registry) {
		this.clazz = o.getClass();
	}

	public void toJSON(Writer w) throws IOException {
		w.write("\"#class\": ");
		w.write('"');
		w.write(clazz.getName());
		w.write('"');
	}

	@Override
	public String toString() {
		return toJSON(false);
	}

	public String toJSON(boolean beautify) {
		var w = new StringWriter();

		try {
			toJSON(w);
		} catch (IOException e) {
			throw new IllegalStateException();
		}

		var s = w.toString();
		return beautify? JSONUtils.beautify(s) : s ;
	}
}
