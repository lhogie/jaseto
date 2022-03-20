package jaseto;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ArrayNode extends Node {
	public String componentType;
	List<Node> children = new ArrayList<>();

	@Override
	public void toJSON(Writer w) throws IOException {
		super.toJSON(w);
		w.write('[');

		for (int i = 0; i < children.size(); ++i) {
			children.get(i).toJSON(w);

			if (i < children.size() - 1) {
				w.write(", ");
			}
		}

		w.write(']');
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
