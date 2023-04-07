package jaseto;

import toools.reflect.Introspector.JasetoField;

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
	boolean accept(JasetoField field, Object value, Object from);

	/*
	 * Do not show object counterparts of primitive types as objects.
	 */
	boolean treatBoxedAsPrimitives();

	/*
	 * Once an object is turned to a node, this method is called so as to enable the
	 * user to adapt the node to its specific requirements.
	 */
	Node alter(Node n);
}
