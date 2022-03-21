package jaseto;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import toools.text.json.JSONUtils;

public abstract class Node {
	public abstract void toJSON(Writer w) throws IOException;

	@Override
	public String toString() {
		return toJSON(true);
	}

	public String toJSON(boolean beautify) {
		var w = new StringWriter();

		try {
			toJSON(w);
		} catch (IOException e) {
			throw new IllegalStateException();
		}

		var s = w.toString();
		return beautify ? JSONUtils.beautify(s) : s;
	}
}
