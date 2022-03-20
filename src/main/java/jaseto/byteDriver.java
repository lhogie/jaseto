package jaseto;

public final class byteDriver extends StringableNode {
	@Override
	public Object toObject(String s) {
		return Byte.parseByte(s);
	}
}