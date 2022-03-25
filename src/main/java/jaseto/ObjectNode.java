package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public abstract class ObjectNode extends Node {
	public final Map<String, Node> map = new TreeMap<>();
	boolean showID;
	int id;

	public ObjectNode(Object o, Jaseto serializer) {
		this.id = System.identityHashCode(o);
		serializer.registry.add(o, this);
		addKey("#class", new StringNode(serializer.customizer.className(o), serializer));
		addKey("#id", new StringNode(id, serializer));
	}

	public void addKey(String name, Node childNode) {
		map.put(name, childNode);
		childNode.parent = this;
	}

	public void renameKey(String oldName, String newName) {
		map.put(newName, map.remove(oldName));
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("{\n");

		if (!showID) {
			map.remove("#id");
		}

		var i = map.entrySet().iterator();

		if (i.hasNext()) {
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
