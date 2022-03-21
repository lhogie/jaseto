package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public abstract class MapNode extends ObjectNode {
	public final Map<String, Node> children = new TreeMap<>();

	public MapNode(Object o, Registry registry, SerializationController sc) {
		super(o, registry, sc);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("{");
		super.toJSON(w);
		w.print(", ");

		var i = children.entrySet().iterator();

		while (i.hasNext()) {
			var e = i.next();
			w.print("\"" + e.getKey() + "\": ");
			e.getValue().toJSON(w);

			if (i.hasNext()) {
				w.print(", ");
			}
		}

		w.print("}");
	}
}
