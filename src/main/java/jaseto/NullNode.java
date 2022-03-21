package jaseto;

import java.io.IOException;
import java.io.Writer;

public class NullNode extends Node {

	
	@Override
	public void toJSON(Writer w) throws IOException {
		w.write("{}");
	}
}
