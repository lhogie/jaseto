package jaseto;

import java.io.IOException;
import java.io.Writer;

public class IDedNode extends Node {
	private int id;
	boolean showID;

	public IDedNode(Object o, Registry r) {
		this.id = System.identityHashCode(o);
	}

	@Override
	public void toJSON(Writer w) throws IOException {
		if (showID) {
			w.write("id=" + id);
		}
	}
}
