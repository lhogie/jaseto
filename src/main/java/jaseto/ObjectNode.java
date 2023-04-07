package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public abstract class ObjectNode extends Node implements NotLeaf {
	public final Map<String, Node> map = new TreeMap<>();

	public ObjectNode(Object o, String name, Jaseto serializer) {
		super(o, name);
		serializer.registry.add(o, this);
		putKey("#class", new StringNode(className(o), "#class", serializer));
	}

	@Override
	public void replace(Node a, Node b) {
		for (var e : map.entrySet()) {
			if (e.getValue() == a) {
				e.setValue(b);
				return;
			}
		}
	}

	public static String className(Object o) {
		if (o.getClass().isArray()) {
			return o.getClass().getComponentType().getName() + "[]";
		}

		return o.getClass().getName();
	}

	public void putKey(String name, Node childNode) {
		map.put(name, childNode);
		childNode.parent = this;
	}

	public void putKey(String name, String value) {
		var n = map.get(name);

		if (n instanceof StringNode) {
			var sn = (StringNode) n;
			sn.value = value;
		} else {
			throw new IllegalStateException();
		}
	}

	public void renameKey(String oldName, String newName) {
		map.put(newName, map.remove(oldName));
	}

	public void removeKey(String key) {
		map.remove(key);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("{\n");

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
