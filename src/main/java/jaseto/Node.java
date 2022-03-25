package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Node {
	public Node parent;

	public abstract void toJSON(PrintWriter w) throws IOException;

	@Override
	public String toString() {
		return toJSON();
	}

	public String toJSON() {
		var sw = new StringWriter();
		var w = new PrintWriter(sw);

		try {
			toJSON(w);
		} catch (IOException e) {
			throw new IllegalStateException();
		}

		var s = sw.toString();
		return s;
	}
	
	public int depth() {
		if (parent ==null) {
			return 0;
		}

		return parent.depth() + 1;
	}
	
	protected void tab(PrintWriter w) {
		for (int i = depth(); i > 0; --i) {
			w.print('\t');
		}
	}
}
