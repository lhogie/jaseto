package jaseto.old;

import jaseto.Registry;
import jaseto.StringNode;

public class doubleDriver extends StringNode {

	public doubleDriver(Object o, Registry registry) {
		super(o, registry);
	}

	@Override
	public Object toObject(String s) {
		return Double.valueOf(s);
	}

}