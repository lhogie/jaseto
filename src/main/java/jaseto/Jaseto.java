package jaseto;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import toools.text.json.JSONUtils;

public class Jaseto {
	private static final Map<Class<?>, Class<? extends Node>> classDrivers = new HashMap<>();

	static {
		/*
		 * registerNode(int.class, intDriver.class); registerNode(boolean.class,
		 * booleanDriver.class); registerNode(byte.class, byteDriver.class);
		 * registerNode(char.class, charDriver.class); registerNode(short.class,
		 * shortDriver.class); registerNode(long.class, longDriver.class);
		 * registerNode(float.class, floatDriver.class); registerNode(double.class,
		 * doubleDriver.class);
		 * 
		 * registerNode(Integer.class, intDriver.class); registerNode(Boolean.class,
		 * booleanDriver.class); registerNode(Byte.class, byteDriver.class);
		 * registerNode(Character.class, charDriver.class); registerNode(Short.class,
		 * shortDriver.class); registerNode(Long.class, longDriver.class);
		 * registerNode(Float.class, floatDriver.class); registerNode(Double.class,
		 * doubleDriver.class);
		 */

		registerNode(String.class, StringNode.class);
		registerNode(boolean.class, StringNode.class);
		registerNode(byte.class, StringNode.class);
		registerNode(char.class, StringNode.class);
		registerNode(short.class, StringNode.class);
		registerNode(int.class, StringNode.class);
		registerNode(long.class, StringNode.class);
		registerNode(float.class, StringNode.class);
		registerNode(double.class, StringNode.class);

	}

	public static void registerNode(Class targetClass, Class<? extends Node> driver) {
		classDrivers.put(targetClass, driver);
	}

	public static Class<? extends Node> lookupNodeClass(Class c) {
		if (c == null)
			return NullNode.class;

		var driver = classDrivers.get(c);

		if (driver != null) {
			return driver;
		} else if (c.isArray()) {
			return ArrayNode.class;
		} else {
			return IntrospectingMapNode.class;
		}
	}

	public static String toJSON(Object o) {
		return toJSON(o, new DefaultSerializationController());
	}

	public static String toJSON(Object o, SerializationController sc) {
		var node = (ObjectNode) toNode(o, lookupNodeClass(o.getClass()), new Registry(), sc);
		return node.toJSON();
	}

	public static void validate(String json) {
		JSONUtils.beautify(json);
	}

	static Node toNode(Object o, Class<? extends Node> nodeClass, Registry registry, SerializationController sc) {
		if (o == null) {
			return new NullNode();
		}

		if (ObjectNode.class.isAssignableFrom(nodeClass)) {
			var alreadyInNode = (ObjectNode) registry.getNode(o);

			if (alreadyInNode == null) {
				var n = (ObjectNode) createNode(nodeClass, o, registry, sc);
				return n;
			} else {
				var link = new LinkNode(alreadyInNode.id);
				alreadyInNode.showID = true;
				return link;
			}
		} else {
			return createNode(nodeClass, o, registry, sc);
		}
	}

	private static <A extends Node> A createNode(Class<A> nodeClass, Object from, Registry registry,
			SerializationController sc) {
		try {
			var constructor = nodeClass.getConstructor(Object.class, Registry.class, SerializationController.class);
			A n = constructor.newInstance(from, registry, sc);
			return n;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException(e);
		}
	}
}
