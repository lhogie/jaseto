package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

public class IDedNode extends Node {
	int id;
	boolean showID;

	public IDedNode(Object o, Registry r) {
		this.id = System.identityHashCode(o);
		r.add(o, this);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		if (showID) {
			w.print("\"#ID\": ");
			w.print(id);
		}
	}
}
