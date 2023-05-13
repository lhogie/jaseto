package jaseto;

import java.util.Set;

public class JustAValueType extends ObjectNode {

	public JustAValueType(Object o, Jaseto jaseto) {
		super(o, jaseto);
		setProperty("value", jaseto.toNode(o.toString()));
	}
}
