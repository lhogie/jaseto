package jaseto;

import java.util.HashMap;
import java.util.Map;

import toools.io.Cout;

public class Registry {
	private static class Entry {
		Object o;
		IDedNode n;

		public Entry(Object object, IDedNode node) {
			this.o = object;
			this.n = node;
		}
	}

	private final Map<Integer, Entry> map = new HashMap<>();

	public boolean contains(Object o) {
		return map.containsKey(id(o));
	}

	public void add(Object o, IDedNode n) {
//		Cout.debug("adding " + o + " in " + n);
		map.put(id(o), new Entry(o, n));
	}

	public IDedNode getNode(Object o) {
		int id = id(o);
		var e = map.get(id);
		return e == null ? null : e.n;
	}

	public int id(Object o) {
		return System.identityHashCode(o);
	}
}
