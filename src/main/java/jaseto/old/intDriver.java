package jaseto.old;

import jaseto.Registry;
import jaseto.StringNode;

public class intDriver extends StringNode {

	public intDriver(Object o, Registry registry) {
		super(o, registry);
	}

	@Override
	public Object toObject(String s) {
		return Integer.parseInt(s);
	}

}