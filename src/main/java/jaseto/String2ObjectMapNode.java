package jaseto;

import java.util.Map;

public class String2ObjectMapNode extends ObjectNode {

	public String2ObjectMapNode(Object o, Jaseto sc) {
		super(o, sc);
		((Map<String, ?>) o).entrySet().forEach(e -> setProperty(e.getKey(), sc.toNode(e.getValue())));
	}
}
