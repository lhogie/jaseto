package jaseto;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JasetoJSONParser {
	final BReader r;

	public JasetoJSONParser(String json) {
		this.r = new BReader(new StringReader(json));
	}

	public Object obj() throws JSONException, IOException {
		expect('{');
		int i = skipSpaces("} expected");

		if (i == '}') { // end of object
			return null;
		} else if (i == '"') { // new key
			var key = readUntil(c -> c == '"', "\" expected");
			i = skipSpaces(": expected");
			r.rewind(i);
			expect(':');
			i = skipSpaces(": expected");

			var value = i == '"' ? readUntil(c -> c == '"', "\" expected")
					: readUntil(c -> Character.isWhitespace(c) || c == ',' || c == '}', "space, ' or } expected");
		}
		return null;
	}

	void expect(char e) throws JSONException, IOException {
		int c = r.read();

		if (e != c)
			throw new JSONException("expecting { but got " + c);
	}

	public static interface P {
		boolean test(int i);
	}

	public String readUntil(P p, String err) throws IOException, JSONException {
		var b = new StringBuilder();

		while (true) {
			int i = r.read();

			if (i == -1)
				throw new JSONException(err);

			if (p.test(i)) {
				return b.toString();
			}

			b.append(i);
		}
	}

	public int skipSpaces(String eof) throws IOException, JSONException {
		while (true) {
			int i = r.read();

			if (i == -1)
				throw new JSONException(eof);

			if (!Character.isWhitespace(i)) {
				return i;
			}
		}

	}

	public static class BReader {
		Reader r;
		private int last = -2;

		BReader(Reader r) {
			this.r = r;
		}

		public void rewind(int i) {
			if (last > -2)
				throw new IllegalStateException("cannot rewind more than 1 char");

			last = i;
		}

		int read() throws IOException {
			if (last == -2) {
				return r.read();
			} else {
				var r = last;
				last = -2;
				return r;
			}
		}

	}

	public static class JSONException extends Exception {
		public JSONException(String msg) {
			super(msg);
		}

	}

}
