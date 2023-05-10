package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

public class NullNode extends Node {

	public NullNode(String name, Jaseto sc) {
		super(null, name, sc);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("null");
	}
}
