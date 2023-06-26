package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectNode extends NodeContainer {
	private final Map<String, Node> name_child = new LinkedHashMap<>();

	public ObjectNode(Object o, Jaseto serializer) {
		super(o, serializer);
		serializer.registry.add(o, this);
		setProperty("#class", serializer.toNode(o.getClass()));
	}

	@Override
	public void replace(Node a, Node b) {
		for (var e : name_child.entrySet()) {
			if (e.getValue() == a) {
				e.setValue(b);
			}
		}
	}

	public String childName(Node node) {
		for (var e : name_child.entrySet()) {
			if (e.getValue() == node) {
				return e.getKey();
			}
		}

		return null;
	}

	public Node get(String key) {
		return name_child.get(key);
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
