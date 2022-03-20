package jaseto;

public class intDriver extends StringableNode {

	@Override
	public Object toObject(String s) {
		return Integer.parseInt(s);
	}

}