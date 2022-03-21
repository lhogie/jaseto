package jaseto;

import java.io.IOException;
import java.io.Writer;

public class ObjectNode extends IDedNode {
	private String clazz;

	public ObjectNode(Object o, Registry registry, SerializationController sc) {
		super(o, registry);
		this.clazz = sc.classNameAlias(o.getClass());
	}

	@Override
	public void toJSON(Writer w) throws IOException {
		super.toJSON(w);
		w.write("\"#class\": ");
		w.write('"');
		w.write(clazz);
		w.write('"');
	}
}
