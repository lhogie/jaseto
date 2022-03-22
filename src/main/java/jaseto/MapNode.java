package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public abstract class MapNode extends ObjectNode {
	public final Map<String, Node> children = new TreeMap<>();

	public MapNode(Object o, Jaseto serializer) {
		super(o, serializer);
	}

	public void add(String name, Node childNode) {
		children.put(name, childNode);
		childNode.parent = this;
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("{\n");
		super.toJSON(w);

		var i = children.entrySet().iterator();

		if (i.hasNext()) {
			var somethingBefore = showID || (className != null && classNameKey != null);

			if (somethingBefore) {
				w.print(",\n");
			}

			while (i.hasNext()) {
				var e = i.next();
				tab(w);
				w.print("\t\"" + e.getKey() + "\": ");
				e.getValue().toJSON(w);

				if (i.hasNext()) {
					w.print(',');
				}

				w.println();
			}
		} else {
			w.println();
		}

		tab(w);
		w.print("}");
	}
}
