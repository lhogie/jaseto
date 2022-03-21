package jaseto.old;

import jaseto.Registry;
import jaseto.StringNode;

public final class charDriver extends StringNode {
	public charDriver(Object o, Registry registry) {
		super(o, registry);
	}

	@Override
	public Object toObject(String s) {
		return s.charAt(0);
	}

}