package jaseto;

import toools.text.TextUtilities;

public class ClassNode extends Litteral {

	public ClassNode(Object o, String name, Jaseto jaseto) {
		super(o, name, TextUtilities.getNiceClassName((Class) o), jaseto);
	}
}
