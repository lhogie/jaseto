package jaseto;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import toools.reflect.Introspector;
import toools.reflect.Introspector.FF;

public class ArrayNode extends IDedNode {
	private Class componentType;
	private List<Node> children = new ArrayList<>();

	public ArrayNode(Object o, Registry r, SerializationController sc) {
		super(o, r);
		
		int len = Array.getLength(o);
		this.componentType = o.getClass().getComponentType();
		Class<? extends Node> fieldNodeClass = Jaseto.lookupNodeClass(componentType);
		
		for (int i = 0; i < len; ++i) {
			var e = Array.get(o, i);
			
			if (sc.serializeArrayElement(o, i, e)) {
				children.add(Jaseto.toNode(e, fieldNodeClass, r, sc));
			}
		}
	}

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
