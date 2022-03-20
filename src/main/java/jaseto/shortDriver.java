package jaseto;

public class shortDriver extends StringableNode {

	@Override
	public Object toObject(String s) {
		return Short.valueOf(s);
	}

}