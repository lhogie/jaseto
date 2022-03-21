package jaseto.old;

import jaseto.Registry;
import jaseto.StringNode;

public class shortDriver extends StringNode {

	public shortDriver(Object o, Registry registry) {
		super(o, registry);
	}

	@Override
	public Object toObject(String s) {
		return Short.valueOf(s);
	}

}