package jaseto;

public class floatDriver extends StringableNode {

	@Override
	public Object toObject(String s) {
		return Float.valueOf(s);
	}

}