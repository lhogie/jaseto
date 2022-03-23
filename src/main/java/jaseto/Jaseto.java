package jaseto;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jaseto.JasetoJSONParser.JSONException;
import toools.text.json.JSONUtils;

public class Jaseto {
	private final Map<Class<?>, Class<? extends Node>> classDrivers = new HashMap<>();
	public Customizer customizer = new DefaultCustomizer();
	Registry registry = new Registry();

	public Jaseto() {
		/*
		 * registerNode(String.class, StringNode.class); registerNode(boolean.class,
		 * StringNode.class); registerNode(byte.class, StringNode.class);
		 * registerNode(char.class, StringNode.class); registerNode(short.class,
		 * StringNode.class); registerNode(int.class, StringNode.class);
		 * registerNode(long.class, StringNode.class); registerNode(float.class,
		 * StringNode.class); registerNode(double.class, StringNode.class);
		 */

	}

	public Class<? extends Node> lookupNodeClass(Class c) {
		if (c == null)
			return NullNode.class;

//		var driver = classDrivers.get(c);

		if (c.isPrimitive() || c == String.class) {
			return StringNode.class;
		} else if (c == Boolean.class || c == Byte.class || c == Character.class || c == Short.class
				|| c == Integer.class || c == Long.class || c == Float.class || c == Double.class) {
			return customizer.considerBoxedAsPrimitives() ? StringNode.class : BoxedType.class;
		} else if (Map.class.isAssignableFrom(c)) {
			return MapNode.class;
		} else if (c.isArray()) {
			return ArrayNode2.class;
		} else if (Collection.class.isAssignableFrom(c)) {
			return CollectionNode.class;
		} else {
			return IntrospectingMapNode.class;
		}
	}

	public String toJSON(Object o) {
		return toJSON(o, this);
	}

	public String toJSON(Object o, Jaseto serializer) {
		var node = toNode(o, lookupNodeClass(o.getClass()));
		return node.toJSON();
	}

	public static void validateJackson(String json) {
		JSONUtils.beautify(json);
	}

	public static void validateGSON(String json) {
		JsonParser.parseReader(new StringReader(json));
	}

	Node toNode(Object o, Class<? extends Node> nodeClass) {
		Object newO = customizer.substitute(o);

		if (newO != o) {
			nodeClass = lookupNodeClass(newO.getClass());
			o = newO;
		}

		if (o == null) {
			return new NullNode();
		}

		if (ObjectNode.class.isAssignableFrom(nodeClass)) {
			var alreadyInNode = (ObjectNode) registry.getNode(o);

			if (alreadyInNode == null) {
				var n = (ObjectNode) createNode(nodeClass, o);
				return n;
			} else {
				var link = new LinkNode(alreadyInNode.id);
				alreadyInNode.showID = true;
				return link;
			}
		} else {
			return createNode(nodeClass, o);
		}
	}

	private Object subst_multipass(Object o, Customizer sc) {
		while (true) {
			var newO = sc.substitute(o);

			if (newO == o || StringNode.class.isAssignableFrom(lookupNodeClass(newO.getClass())))
				return o;

			o = newO;
		}
	}

	private <A extends Node> A createNode(Class<A> nodeClass, Object from) {
		try {
			var constructor = nodeClass.getConstructor(Object.class, Jaseto.class);
			A n = constructor.newInstance(from, this);
			return n;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException(e);
		}
	}

	static Object toObject(String json) throws JSONException, IOException {
		return new JasetoJSONParser(json).obj();
	}

	static void gson_parse(String json) {
		JsonElement e = JsonParser.parseReader(new StringReader(json));
		System.out.println(e.getClass());
	}

}
