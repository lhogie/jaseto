package jaseto;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jaseto.JasetoJSONParser.JSONException;
import toools.reflect.Clazz;
import toools.reflect.Introspector.AField;
import toools.text.TextUtilities;
import toools.text.json.JSONUtils;

public class Jaseto {
	private final Map<Class<?>, Class<? extends Node>> classDrivers = new HashMap<>();
	public Registry registry = new Registry();

	protected boolean enableLinksTo(Node n) {
		return n.value.getClass() != String.class;
	}

	public String classname(Class<?> o) {
		if (List.class.isAssignableFrom(o)) {
			return "list";
		} else if (Map.class.isAssignableFrom(o)) {
			return "map";
		} else {
			return TextUtilities.getNiceClassName((Class) o);
		}
	}

	/*
	 * Allow a specific to be excluded from the serialization process. This helps
	 * the serialization of problematic fields.
	 */
	public boolean accept(Object from, AField field, Object value) {
		return true;
	}

	/*
	 * Once an object is turned to a node, this method is called so as to enable the
	 * user to adapt the node to its specific requirements.
	 */
	protected Node alter(Node n) {
		if (n instanceof CollectionNode) {
			return ((CollectionNode) n).get("elements");
		} else {
			return n;
		}
	}

	public final Node toNode(Object o) {
		if (o != null && o.getClass().getEnclosingClass() != null && !Clazz.isStatic(o.getClass()))
			throw new IllegalArgumentException("I won't try to serialize an instance of a non-static inner/anonymous class: " + o.getClass().getName());

		var alreadyInNode = registry.getNode(o);

		if (alreadyInNode != null && enableLinksTo(alreadyInNode)) {
			return new LinkNode(alreadyInNode, this);
		} else {
			return createNode(o);
		}
	}

	public Node createNode(Object o) {
		if (o == null) {
			return new NullNode(this);
		} else if (Litteral.toStringable.contains(o.getClass())) {
			return new Litteral(o, this);
		} else if (o instanceof Class) {
			return new ClassNode(o, this);
		} else if (o instanceof Throwable) {
			return new ThrowableNode(o, this);
		} else if (o instanceof Map) {
			return new MapNode(o, this);
		} else if (o.getClass().isArray()) {
			return new ArrayNode(o, this);
		} else if (o instanceof Collection) {
			return new CollectionNode(o, this);
		} else {
			return new IntrospectingObjectNode(o, this);
		}
	}

	public static void validateByJackson(String json) {
		JSONUtils.beautify(json);
	}

	public static void validateByGSON(String json) {
		JsonParser.parseReader(new StringReader(json));
	}

	static Object toObject(String json) throws JSONException, IOException {
		return new JasetoJSONParser(json).obj();
	}

	public static void gson_parse(String json) {
		JsonElement e = JsonParser.parseReader(new StringReader(json));
	}

}
