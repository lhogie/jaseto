package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayNode extends Node {
	private Class componentType;
	private List<Node> children = new ArrayList<>();
	boolean stringed;

	public ArrayNode(Object o, Jaseto sc) {
		int len = Array.getLength(o);
		this.componentType = o.getClass().getComponentType();
		Class<? extends Node> componentTypeNodeClass = sc.lookupNodeClass(componentType);

		stringed = StringNode.class.isAssignableFrom(componentTypeNodeClass);

		for (int i = 0; i < len; ++i) {
			var e = Array.get(o, i);

			// children.add(Jaseto.toNode(e, fieldNodeClass, r, sc));
			var nc = stringed ? componentTypeNodeClass : sc.lookupNodeClass(e.getClass());
			add(sc.toNode(e, nc));
		}
	}

	private void add(Node node) {
		children.add(node);
		node.parent = this;
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.println('[');

		for (int i = 0; i < children.size(); ++i) {
			tab(w);
			w.print('\t');
			children.get(i).toJSON(w);

			if (i < children.size() - 1) {
				w.print(',');
				w.print('\n');
			}
		}

		if (stringed) {
			w.print(']');
		} else {
			w.print('\n');
			tab(w);
			w.print(']');
		}
	}

	/*
	 * 
	 * @Override public ArrayNode toNode(Object o, Registry registry) { var n = new
	 * ArrayNode(); Class type = o.getClass().getComponentType(); n.componentType =
	 * type.getName(); Driver componentTypeDriver = Jaseto.getDriver(type); int len
	 * = Array.getLength(o);
	 * 
	 * if (componentTypeDriver instanceof StringableDriver) { StringableDriver
	 * sdriver = ((StringableDriver) componentTypeDriver); String value = "";
	 * 
	 * for (int i = 0; i < len; ++i) { Object e = Array.get(o, i);
	 * 
	 * if (e == null || e.getClass() != type) throw new IllegalStateException("" +
	 * e);
	 * 
	 * value += sdriver.toString(e);
	 * 
	 * if (i < len - 1) { value += ", "; } }
	 * 
	 * n.getAttributes().put("value", value);
	 * 
	 * } else { for (int i = 0; i < len; ++i) { Object e = Array.get(o, i);
	 * Jaseto.toNode(e, registry).setParent(n); } }
	 * 
	 * for (int i = 0; i < len; ++i) { Object e = Array.get(o, i); Jaseto.toNode(e,
	 * registry).setParent(n); }
	 * 
	 * return n; }
	 */

}
