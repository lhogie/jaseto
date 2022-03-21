package jaseto;

import java.io.IOException;
import java.io.PrintWriter;

public class ObjectNode extends IDedNode {
	private String className;
	private String classNameKey;

	public ObjectNode(Object o, Registry registry, SerializationController sc) {
		super(o, registry);
		this.className = sc.getClassName(o.getClass());
		this.classNameKey = sc.getClassNameKey();
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		super.toJSON(w);

		if (className != null) {
			if (showID) {
				w.print(", ");
			}

			w.print('"');
			w.print(classNameKey);
			w.print('"');
			w.print(':');
			w.print(' ');
			w.print('"');
			w.print(className);
			w.print('"');
		}
	}
}
