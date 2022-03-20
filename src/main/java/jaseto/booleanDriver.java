package jaseto;

public final class booleanDriver extends StringableNode implements MuttableNode {

	@Override
	public Object toObject(String s) {
		return Boolean.valueOf(s);
	}
}