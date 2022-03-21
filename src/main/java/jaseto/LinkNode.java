package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

public class LinkNode extends Node {
	int srcID;

	public LinkNode(int id) {
		this.srcID = id;
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("{\"#type\": \"link\", \"src\": " + srcID + "}");
	}
}
