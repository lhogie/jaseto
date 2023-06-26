package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayNode extends NodeContainer {
	private Class componentType;
	private List<Node> children = new ArrayList<>();

	public ArrayNode(Object o, Jaseto sc) {
		super(o, sc);
		sc.registry.add(o, this);
		int len = Array.getLength(o);
		this.componentType = o.getClass().getComponentType();

		for (int i = 0; i < len; ++i) {
			addChild(sc.toNode(Array.get(o, i)));
		}
	}

	public void addChild(Node node) {
		children.add(node);
		node.parent = this;
	}

	@Override
	public void toJSON(PrintWriter w) throws IOException {
		w.print('[');

		
		boolean oneLine = oneline();


		int len = children.size();

		if (len == 0) {
			w.print(']');
		} else {
			if (oneLine) {
				for (int i = 0; i < len; ++i) {
					children.get(i).toJSON(w);

					if (i < len - 1) {
						w.print(", ");
					}
				}
			}else {
				w.println();
				
				for (int i = 0; i < len; ++i) {
					tab(w);
					w.print('\t');
					children.get(i).toJSON(w);

					if (i < len - 1) {
						w.print(',');
						w.print('\n');
					}
				}

				w.print('\n');
				tab(w);
			}
			

			w.print(']');
		}
	}

	private boolean oneline() {
		int len = 0;
		
		for (var c : children) {
			if (!(c instanceof Litteral)) {
				return false;
			}
			
			len += ((Litteral) c).value.length();
		}
		return depth() * 4 + len < 80;
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
