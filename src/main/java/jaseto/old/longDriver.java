package jaseto.old;

import jaseto.Registry;
import jaseto.StringNode;

public class longDriver extends StringNode {

	public longDriver(Object o, Registry registry) {
		super(o, registry);
	}

	@Override
	public Long toObject(String s) {
		return Long.parseLong(s);
	}

}