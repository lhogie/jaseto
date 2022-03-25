package jaseto;

import java.util.HashMap;
import java.util.Map;

public class Registry {

	public final Map<Integer, ObjectNode> map = new HashMap<>();

	public Registry() {
	}

	public void add(Object o, ObjectNode n) {
//		Cout.debug("adding " + o + " in " + n);
		map.put(id(o), n);
	}

	public ObjectNode getNode(Object o) {
		return map.get(id(o));
	}

	public int id(Object o) {
		return System.identityHashCode(o);
	}
}
