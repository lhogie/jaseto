package jaseto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Registry {

	private final Map<Integer, Node> map = new HashMap<>();

	public Registry() {
	}

	public void add(Object o, Node n) {
		map.put(id(o), n);
	}

	public Node getNode(Object o) {
		return map.get(id(o));
	}

	public int id(Object o) {
		return System.identityHashCode(o);
	}

	public Collection<Node> nodes() {
		return map.values();
	}

	public Collection<Integer> ids() {
		return map.keySet();
	}
}
