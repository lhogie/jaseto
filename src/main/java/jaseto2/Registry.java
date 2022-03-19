package jaseto2;

import java.util.HashMap;
import java.util.Map;

import toools.io.Cout;

public class Registry {
	private static class Entry {
		Object o;
		ObjectNode n;

		public Entry(Object object, ObjectNode node) {
			this.o = object;
			this.n = node;
		}
	}

	private final Map<Integer, Entry> map = new HashMap<>();

	public boolean contains(Object o) {
		return map.containsKey(id(o));
	}

	public void add(Object o, ObjectNode n) {
		Cout.debug("adding " + o + " in " + n);
		map.put(id(o), new Entry(o, n));
	}

	public ObjectNode getNode(Object o) {
		return map.get(id(o)).n;
	}

	public int id(Object o) {
		return System.identityHashCode(o);
	}
}
