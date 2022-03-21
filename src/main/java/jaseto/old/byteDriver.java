package jaseto.old;

import jaseto.Registry;
import jaseto.StringNode;

public final class byteDriver extends StringNode {
	public byteDriver(Object o, Registry registry) {
		super(o, registry);
	}

	@Override
	public Object toObject(String s) {
		return Byte.parseByte(s);
	}
}