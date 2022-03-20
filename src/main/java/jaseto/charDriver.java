package jaseto;

public final class charDriver extends StringableNode {
	@Override
	public Object toObject(String s) {
		return s.charAt(0);
	}

}