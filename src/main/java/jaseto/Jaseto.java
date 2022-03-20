package jaseto;

import java.util.HashMap;
import java.util.Map;

import toools.io.Cout;
import toools.reflect.Clazz;

public class Jaseto {
	private static final Map<Class<?>, Class<? extends Node>> classDrivers = new HashMap<>();

	static {
		registerNode(int.class, intDriver.class);
		registerNode(boolean.class, booleanDriver.class);
		registerNode(byte.class, byteDriver.class);
		registerNode(char.class, charDriver.class);
		registerNode(short.class, shortDriver.class);
		registerNode(long.class, longDriver.class);
		registerNode(float.class, floatDriver.class);
		registerNode(double.class, doubleDriver.class);

		registerNode(Integer.class, intDriver.class);
		registerNode(Boolean.class, booleanDriver.class);
		registerNode(Byte.class, byteDriver.class);
		registerNode(Character.class, charDriver.class);
		registerNode(Short.class, shortDriver.class);
		registerNode(Long.class, longDriver.class);
		registerNode(Float.class, floatDriver.class);
		registerNode(Double.class, doubleDriver.class);

		registerNode(String.class, StringDriver.class);
	}

	public static void registerNode(Class targetClass, Class<? extends Node> driver) {
		classDrivers.put(targetClass, driver);
	}

	public static Class<? extends Node> lookupNodeClass(Class c) {
		if (c == null)
			return nullDriver.class;

		var driver = classDrivers.get(c);

		if (driver != null) {
			return driver;
		} else if (c.isArray()) {
			return ArrayNode.class;
		} else {
			return IntrospectingMapNode.class;
		}
	}

	public static ObjectNode toNode(Object o) {
		return (ObjectNode) toNode(o, lookupNodeClass(o.getClass()), new Registry());
	}

	static Node toNode(Object o, Class<? extends Node> nodeClass, Registry registry) {
		Cout.debug(o.getClass() + "   " + nodeClass);

		if (MuttableNode.class.isAssignableFrom(nodeClass)) {
			var newNode = Clazz.makeInstance(nodeClass);
			newNode.fill(o, registry);
			return newNode;
		} else {
			int id = registry.id(o);
			var alreadyInNode = registry.getNode(id);

			if (alreadyInNode == null) {
				var node = (ObjectNode) Clazz.makeInstance(nodeClass);
				node.fill(o, registry);
				return node;
			} else {
				var newNode = (ObjectNode) Clazz.makeInstance(nodeClass);
				newNode.fill(o, registry);
				newNode.isReferenceNode = alreadyInNode.isReferringNode = true;
				return newNode;
			}
		}

	}
}
