package jaseto.old;

public final class booleanDriver extends StringNode {



	@Override
	public Object toObject(String s) {
		return Boolean.valueOf(s);
	}
}