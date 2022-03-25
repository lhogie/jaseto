package jaseto;

import java.util.Map;
import java.util.Map.Entry;

public class MapNode extends ObjectNode {

	public MapNode(Object o, Jaseto sc) {
		super(o, sc);
		var map = (Map<Object, Object>) o;

		for (Entry<Object, Object> e : map.entrySet()) {
			var v = e.getValue();
			addKey(e.getKey().toString(), sc.toNode(v, sc.lookupNodeClass(v.getClass())));
		}

	}
}
