package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public abstract class ObjectNode extends Node implements NotLeaf {
	private final Map<String, Node> name_child = new TreeMap<>();

	public ObjectNode(Object o, String name, Jaseto serializer) {
		super(o, name, serializer);
		serializer.registry.add(o, this);
		setProperty("#class", new ClassNode(o.getClass(), "#class", serializer));
	}

	@Override
	public void replace(Node a, Node b) {
		for (var e : name_child.entrySet()) {
			if (e.getValue() == a) {
				e.setValue(b);
				return;
			}
		}
	}

	public static String className(Object o) {
		if (o.getClass().isArray()) {
			return o.getClass().getComponentType().getName() + "[]";
		} else {
			return o.getClass().getName();
		}
	}

	public void setProperty(String name, Node childNode) {
		name_child.put(name, childNode);
		childNode.parent = this;
	}


	public void renameKey(String oldName, String newName) {
		name_child.put(newName, removeKey(oldName));
	}

	public Node removeKey(String key) {
		return name_child.remove(key);
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print("{\n");

		var i = name_child.entrySet().iterator();

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
