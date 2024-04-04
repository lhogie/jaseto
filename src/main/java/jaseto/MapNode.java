package jaseto;

import java.util.Map;

public class MapNode extends ObjectNode {

	public MapNode(Object o, Jaseto sc) {
		super(o, sc);


		setProperty("elements", sc
				.toNode(((Map<String, ?>) o).entrySet().stream().map(e -> new E(e.getKey(), e.getValue())).toArray()));
	}
	
	private static class E {
		Object key, value;

		public E(Object k, Object v) {
			this.key = k;
			this.value = v;
		}
	}

}
