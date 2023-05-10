package jaseto;

import toools.reflect.Introspector.AField;

public interface Customizer {
	/*
	 * Enable to substitute any object by another one. This helps the serialization
	 * of problematic objects.
	 */
	Object substitute(Object o);

	/*
	 * Allow a specific to be excluded from the serialization process. This helps
	 * the serialization of problematic fields.
	 */
	boolean accept(AField field, Object value, Object from);

	boolean treatBoxedAsPrimitives();

	/*
	 * Once an object is turned to a node, this method is called so as to enable the
	 * user to adapt the node to its specific requirements.
	 */
	Node alter(Node n);

	boolean enableLinksTo(Node n);
}
