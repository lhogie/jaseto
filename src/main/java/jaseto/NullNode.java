package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

public class NullNode extends Node {

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("null");
	}
}
