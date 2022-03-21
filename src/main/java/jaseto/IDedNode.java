package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

public class IDedNode extends Node {
	private int id;
	boolean showID;

	public IDedNode(Object o, Registry r) {
		this.id = System.identityHashCode(o);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		if (showID) {
			w.print("\"#ID\": ");
			w.print('"');
			w.print(id);
			w.print('"');
		}
	}
}
