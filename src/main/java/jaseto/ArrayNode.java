package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayNode extends NodeContainer  {
	private Class componentType;
	public List<Node> children = new ArrayList<>();
	boolean stringed;

	public ArrayNode(Object o, Jaseto sc) {
		super(o, sc);
		sc.registry.add(o, this);
		int len = Array.getLength(o);
		this.componentType = o.getClass().getComponentType();

		for (int i = 0; i < len; ++i) {
			var value = Array.get(o, i);
			var node = sc.toNode(value);
			children.add(node);
			node.parent = this;
		}
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.println('[');

		int len = children.size();

		if (len == 0) {
			w.print(']');
		} else {
			for (int i = 0; i < len; ++i) {
				tab(w);
				w.print('\t');
				children.get(i).toJSON(w);

				if (i < len - 1) {
					w.print(',');
					w.print('\n');
				}
			}

			if (!stringed) {
				w.print('\n');
				tab(w);
			}

			w.print(']');
		}
	}

	@Override
	public void replace(Node a, Node b) {
		var i = children.indexOf(a);
		children.set(i, b);
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

	public String childName(Node node) {
		for (int i = 0; i < children.size(); ++i) {
			if (children.get(i) == node) {
				return "" + i;
			}
		}

		return null;
	}

}
