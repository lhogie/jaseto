package jaseto;

import java.util.Map;

public class String2ObjectMapNode extends ObjectNode {

	public String2ObjectMapNode(Object o, String name, Jaseto sc) {
		super(o, name, sc);
		((Map<String, ?>) o).entrySet().forEach(e -> setProperty(e.getKey(), sc.toJSON(e.getValue())));
	}
}
