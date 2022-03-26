# Jaseto

Jaseto is JSON serializer for Java objects. It may be tempting to confront it to [Jackson serializer](https://www.baeldung.com/jackson-custom-serialization) and [GSON serializer](https://github.com/google/gson), but it actually tackles a different problem. Jackson and GSON, just like most serialization tools on the market enable the interoperation of heterogenous applications, by providing both serialization and deserialization. They are complex librairies which also suffer from limitations like the impossibility to work in the presence of cyclic aggregation, and a limited ability to customize the output JSON. Jaseto focuses on a specific (yet very frequent) application of serializers: exposing object models to Web applications written in Javascript, using JSON encoding. 
This its particular purpose, Jaseto:
- features __programmatic customization__ API (many other tools annotation-based descriptive approaches), which enables highly __flexible and deeper customization__ abilities
- __supports cyclic aggregation__, making it possible to serialize __graphs__
- does not provide deserialization (__yet__)
Also the API of Jaseto has been designed to be as simple as possible.

Jaseto is being developed in the context of Research experiments at [I3S laboratory](https://www.i3s.unice.fr/) of [Universté Côte d'Azur](https://univ-cotedazur.eu/). In particular it powers the Web interface of the 
[Idawi](https://github.com/lhogie/idawi) decentralized component system.

# Usage
Serializing an object of following type:
```java
class DemoType {
		boolean loveIsAll = true;
		double pi = Math.PI;
		long maxLongValue = Long.MAX_VALUE;
		Object myself = this;
		Boolean aBooleanObject = false;
		Object aNullReference = null;
		Object[] anArray = new Object[] { "Java", true, 9.8, this };
		Collection aList = new ArrayList<>(List.of("Hello", "you"));
		Map aMap = Map.of("key1","value1", "key2", "value2");
	}
```

This simple instruction:
```java
Jaseto jaseto = new Jaseto();
String json = jaseto.toJSON(new DemoType());
```
produces the following JSON text:
```json
{
	"#class": "jaseto.Demo$DemoType",
	"aBooleanObject": false,
	"aList": {
		"#class": "java.util.ArrayList",
		"elements": [
			"Hello",
			"you",
			{"#link_to": .anArray}
		]
	},
	"aMap": {
		"#class": "java.util.ImmutableCollections$MapN",
		"key1": "value1",
		"key2": "value2"
	},
	"aNullReference": null,
	"anArray": {
		"#class": "java.lang.Object[]",
		"elements": [
			"Java",
			true,
			9.8
		]
	},
	"loveIsAll": true,
	"maxLongValue": 9223372036854775807,
	"myself": {"#link_to": .},
	"pi": 3.141592653589793
}
```

## Customization of the JSON text
The SerializationController interface enables deep customization of the output JSON text. A default implementation is provided.
It provides a set of methods that can be implemented so as to match as much as possible the requirements of the client application.
```java
public interface Customizer {
	/*
	 * Enable to substitute any object by another one.
	 * This helps the serialization of problematic objects.
	 */
	Object substitute(Object o);

	/*
	 * Allow a specific to be excluded from the serialization process.
	 * This helps the serialization of problematic fields. 
	 */
	boolean accept(JasetoField field, Object value, Object from);

	/*
	 * Do not show object counterparts of primitive types as objects.
	 */
	boolean treatBoxedAsPrimitives();

	/*
	 * Once an object is turned to a node, this method is called so as to
	 * enable the user to adapt the node to its specific requirements.
	 */
	void alter(ObjectNode n);
}
```

To activate these changes, you can pass it as a parameter, like this:

```java
jaseto.customizer = new Customizer() {
	// implement all methods
};
```

You can also use the default implementation and overrides only what you need
```java
jaseto.customizer = new DefaultCustomizer() {
	// override only what you need
};
```
### Substituting an object on-the-fly
The most common problem when serializing data is the presence of weird objects whose the structure pose problems. To overcome this issue, Jaseto allows the dynamic substitution of any object be encountered.
This example replace all Swing object with something riskless. Other objects remain untouched.
```java
@Override
public Object substitute(Object o) {
	return o instanceof JComponent ? "This is Swing component - let's drop it" : o;
}
```
Note that substitution is single-pass.


### Renaming a particular field
This example convert all field names to upper case.
```java
@Override
public String fieldName(Field field, Object from) {
	return field.getName().toUpperCase();
}
```


### Exluding a particular field
When the new name is set to null, the field is dropped.
```java
@Override
public boolean accept(JasetoField field, Object value, Object from) {
	return !field.getName().equals("nastyField");
}
```

### Changing the keys and values
#### adding a new key
Serialization only considers fields. Jackson also considers getter methods, assuming that they have no side effect. Jaseto does not take that risk.
But sometimes the set of fields is not enough to completely describe an object. Jaseto enables the users to augment the default description of an object by adding new keys on the fly.
This example describes if an object is visible by comparing its color to the color of its parent graphic environnement.
```java
@Override
public void alter(ObjectNode n) {
	if (n.value instanceof GraphicalObject){
		keys.putKey("visible", from.getColor() != from.getParent().getColor());
	}
}
```

#### Removing a key.
This example removes all class information.
```java
@Override
public void alter(ObjectNode n) {
	keys.removeKey("#class");
}
```


#### Renaming a field:
```java
@Override
public void alter(ObjectNode n) {
	if (n.value instanceof GraphicalObject){
		keys.renameKey("parentComponent", "parent");
	}
}
```

#### Changing key value
This prints pretty type names for usual Java types.
```java
@Override
public void alter(ObjectNode n) {
	if (n.value instanceof Set){
		keys.putKey("#class", "set");
	}
	else if (n.value instanceof List){
		keys.putKey("#class", "list");
	}
}
```


### Using a new node class
If you need a special processing for a particular object class, you can write a specific node class for it. Once written, you need to override:
```java
@Override
public Class<? extends Node> lookupNodeClass(Class c) {
	if (c == MySpecialClass){
		return MySpecialClassNode.class
	} else {
		super.lookupNodeClass(c);
	}
}
```

