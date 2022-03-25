package jaseto;

import java.util.Map;
import java.util.Map.Entry;

public class MapNode extends ObjectNode {

	public MapNode(Object o, String name, Jaseto sc) {
		super(o, name, sc);
		var map = (Map<Object, Object>) o;

		for (Entry<Object, Object> e : map.entrySet()) {
			var v = e.getValue();
			putKey(e.getKey().toString(), sc.toNode(v, e.getKey().toString(),  sc.lookupNodeClass(v.getClass())));
		}

	}
}
