package jaseto;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

public abstract class MapNode extends ObjectNode {
	public final Map<String, Node> children = new TreeMap<>();

	public MapNode(Object o, Registry registry, SerializationController sc) {
		super(o, registry, sc);
	}

	@Override
	public void toJSON(Writer w) throws IOException {
		w.write("{");
		super.toJSON(w);
		w.write(", ");

		var i = children.entrySet().iterator();

		while (i.hasNext()) {
			var e = i.next();
			w.write("\"" + e.getKey() + "\": ");
			System.err.println(e.getKey());
			System.err.println(e.getValue().getClass());
			e.getValue().toJSON(w);

			if (i.hasNext()) {
				w.write(", ");
			}
		}

		w.write("}");
	}
}
