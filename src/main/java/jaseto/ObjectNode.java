package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public abstract class ObjectNode extends Node {
	public final Map<String, Node> map = new TreeMap<>();
	int id;
	boolean showID;

	public ObjectNode(Object o, Jaseto serializer) {
		this.id = System.identityHashCode(o);
		serializer.registry.add(o, this);
		map.put("#class", new StringNode(serializer.customizer.className(o), serializer));
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

		if (showID) {
			tab(w);
			w.print("\t\"#ID\": ");
			w.print(id);
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
