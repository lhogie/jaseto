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
	public Registry registry = new Registry();

	protected Class<? extends Node> lookupNodeClass(Class c) {
				
		if (c == null) {
			return NullNode.class;
		} else if (c.isPrimitive() || c == String.class) {
			return Litteral.class;
		} else if (JustAValueType.knownTypes.contains(c)) {
			return customizer.treatBoxedAsPrimitives() ? Litteral.class : JustAValueType.class;
		} else if (Class.class.isAssignableFrom(c)) {
			return ClassNode.class;
		} else if (Throwable.class.isAssignableFrom(c)) {
			return ThrowableNode.class;
		} else if (Map.class.isAssignableFrom(c)) {
			return String2ObjectMapNode.class;
		} else if (c.isArray()) {
			return ArrayNode.class;
		} else if (Collection.class.isAssignableFrom(c)) {
			return CollectionNode.class;
		} else {
			return IntrospectingMapNode.class;
		}
	}

	public Node toJSON(Object o) {
		var node = toNode(o, ".", lookupNodeClass(o.getClass()));

		for (var n : registry.nodes()) {
			customizer.alter(n);
		}
		/*
		 * for (var e : registry.map.entrySet()) { Node a = e.getValue(); Node b =
		 * customizer.alter(a);
		 * 
		 * if (a != b) { e.setValue(b);
		 * 
		 * if (a.parent instanceof NotLeaf) { ((NotLeaf) a.parent).replace(a, b); } } }
		 */

		return node;
	}

	public static void validateByJackson(String json) {
		JSONUtils.beautify(json);
	}

	public static void validateByGSON(String json) {
		JsonParser.parseReader(new StringReader(json));
	}

	Node toNode(Object o, String name, Class<? extends Node> nodeClass) {
		Object newO = customizer.substitute(o);

		if (newO != o) {
			nodeClass = lookupNodeClass(newO.getClass());
			o = newO;
		}

		if (o == null) {
			return new NullNode(name, this);
		} else {
			var alreadyInNode = registry.getNode(o);

			if (alreadyInNode != null && customizer.enableLinksTo(alreadyInNode)) {
				return new LinkNode(alreadyInNode, name, this);
			} else {
				return createNode(nodeClass, o, name);
			}
		}
	}

	private <A extends Node> A createNode(Class<A> nodeClass, Object from, String name) {
		try {
			return nodeClass.getConstructor(Object.class, String.class, Jaseto.class).newInstance(from, name, this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException(e);
		}
	}

	static Object toObject(String json) throws JSONException, IOException {
		return new JasetoJSONParser(json).obj();
	}

	public static void gson_parse(String json) {
		JsonElement e = JsonParser.parseReader(new StringReader(json));
	}

}
