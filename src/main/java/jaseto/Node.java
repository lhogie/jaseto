package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Node<E> {
	public NodeContainer parent;
	public E value;

	public Node(E value, Jaseto serializer) {
		this.value = value;
	}

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

		return sw.toString();
	}

	public int depth() {
		return parent == null ? 0 : parent.depth() + 1;
	}

	protected void tab(PrintWriter w) {
		for (int i = depth(); i > 0; --i) {
			w.print('\t');
		}
	}

	protected String path() {
		if (parent == null) {
			return ".";
		} else {
			var s = "." + parent.childName(this);

			if (parent.parent != null) {
				s = parent.path() + s;
			}

			return s;
		}
	}
}
