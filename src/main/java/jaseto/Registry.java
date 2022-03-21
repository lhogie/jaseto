package jaseto;

import java.util.HashMap;
import java.util.Map;

public class Registry {

	private final Map<Integer, IDedNode> map = new HashMap<>();

	public Registry() {
	}

	public void add(Object o, IDedNode n) {
//		Cout.debug("adding " + o + " in " + n);
		map.put(id(o), n);
	}

	public IDedNode getNode(Object o) {
		return map.get(id(o));
	}

	public int id(Object o) {
		return System.identityHashCode(o);
	}
}
