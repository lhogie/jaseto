package jaseto;

import java.io.IOException;
import java.io.Writer;

public  class ObjectNode extends Node {
	public boolean isReferenceNode;
	public boolean isReferringNode;
	public int id;
	
	@Override
	public void fill(Object o, Registry registry) {
		super.fill(o, registry);
		this.id = System.identityHashCode(o);
	}

	@Override
	public void toJSON(Writer w) throws IOException {
		super.toJSON(w);

		if (isReferenceNode || isReferringNode) {
			w.write("id=" + id);
		}
	}
}
