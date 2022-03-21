package jaseto.old;

import jaseto.Registry;
import jaseto.StringNode;

public class floatDriver extends StringNode {

	public floatDriver(Object o, Registry registry) {
		super(o, registry);
	}

	@Override
	public Object toObject(String s) {
		return Float.valueOf(s);
	}

}