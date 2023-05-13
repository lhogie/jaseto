package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

public class LinkNode extends Node<Node> {

	public LinkNode(Node target,  Jaseto sc) {
		super(target, sc);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("{\"#link_to\": \"" + value.path() + "\"}");
	}
}
