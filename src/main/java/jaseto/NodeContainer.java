package jaseto;

public abstract class NodeContainer extends Node {

	public NodeContainer(Object value, Jaseto serializer) {
		super(value, serializer);
		// TODO Auto-generated constructor stub
	}

	public abstract String childName(Node node);

	public abstract void replace(Node a, Node b);
}
