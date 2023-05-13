package jaseto;

public class ClassNode extends Litteral {

	public ClassNode(Object o, Jaseto jaseto) {
		super(o, jaseto.classname((Class) o), jaseto);
	}
}
