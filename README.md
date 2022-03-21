# Jaseto

Jaseto is JSON serializer for Java objects. It can be confronted to [Jackson serializer](https://www.baeldung.com/jackson-custom-serialization) and [GSON serializer](https://github.com/google/gson) but it tackles a different problem.

Jackson and GSON, just like most serialization tools on the market enable the interoperation of heterogenous applications, by providing both serialization and deserialization. They are complex librairies which also suffer from limitations like the impossibility to work in the presence of cyclic aggregation, and limited ability to customize the output JSON.

Jaseto focuses on a specific (yet very frequent) application of serializers: exposing object models to Web applications written in Javascript, using JSON encoding. 
This its particular purpose, Jaseto:
- has deeper customization abilities
- supports cyclic aggregation, making it able to serialize graphs
- does not provide deserialization
Also the API of Jaseto has been designed to be as simple as possible.

Jaseto is being developed in the context of Research experiments at [I3S laboratory](https://www.i3s.unice.fr/) of [Universté Côte d'Azur](https://univ-cotedazur.eu/). In particular it powers the Web interface of the 
[Idawi](https://github.com/lhogie/idawi) decentralized component system.

# Usage
Serializing an object of following type:
```java
	class DemoType {
		String foo = "bar";
		boolean bool = true;
		Boolean boolObject = false;
		double pi = Math.PI;
		long maxLongValue = Long.MAX_VALUE;
		Object aNullReference = null;
		Object[] anArrayOfObjects = new Object[] { "Java", true, 9.8 };
		char[] anArrayOfPrimitiveValues = "abcdef".toCharArray();
	}
```

The command 
```java
String json = Jaseto.toJSON(new DemoType());
```
will produce the following JSON text:
```json
{
  "#class" : "jaseto.Demo$DemoType",
  "aNullReference" : null,
  "anArrayOfObjects" : [ "Java", {
    "#class" : "java.lang.Boolean",
    "value" : true
  }, {
    "#class" : "java.lang.Double",
    "value" : 9.8
  } ],
  "anArrayOfPrimitiveValues" : [ "a", "b", "c", "d", "e", "f" ],
  "bool" : true,
  "boolObject" : {
    "#class" : "java.lang.Boolean",
    "value" : false
  },
  "foo" : "bar",
  "maxLongValue" : 9223372036854775807,
  "pi" : 3.141592653589793
}
```

## Customization of the JSON text
The SerializationController interface enables deep customization of the output JSON text. A default implementation is provided.
It provides a set of methods that can be implemented so as to match as much as possible the requirements of the client application.
```java
public interface SerializationController {
	String fieldName(FF field);

	boolean serializeArrayElement(Object array, int i, Object element);

	void addKeys(Map<String, Node> keys, Object from);

	String getClassName(Class<? extends Object> class1);

	String toString(Object o);

	public String getClassNameKey();
}
```

To activate these changes, you can pass it as a parameter, like this:

```java
String json = Jaseto.toJSON(new TestType(), new DefaultSerializationController();
```

### Renaming a particular field
This example convert all field names to upper case.
```java
@Override
public String fieldName(FF field) {
	return field.getName().toUpperCase();
}
```


### Exluding a particular field
When the new name is set to null, the field is dropped.
```java
@Override
public String fieldName(FF field) {
	if (field.getName().equals("nastyField")) {
		return null;
	}
		
	return field.getName();
}
```

### Modifying keys
Serialization only considers fields. Jackson also considers getter methods, assuming that they have no side effect. Jaseto does not take that risk.
But sometimes the set of fields is not enough to completely describe an object. Jaseto enables the users to augment the default description of an object by adding new keys on the fly.
This example describes if an object is visible by comparing its color to the color of its parent graphic environnement.
```java
@Override
public void addKeys(Map<String, Node> keys, Object from) {
	keys.put("visible", from.getColor() != from.getParent().getColor());
}
```

### Renaming a class name
This prints pretty type names for usual Java types.
```java
@Override
public String classNameAlias(Class<? extends Object> class1) {
	if (class1 == String.class) {
		return "string";
	} else if (class1 == Set.class) {
		return "set";
	} else if (class1 == List.class) {
		return "list";
	}
	
	return class1.getName();
}
```

### Not showing the class name
When the class name is set to null, it is not shown.
```java
@Override
public String classNameAlias(Class<? extends Object> class1) {
	return null;
}
```

### Changing the key for the class name
By default, the key exposing the class is #class. It can be changed everywhere by redefining
```java
@Override
public String getClassNameKey() {
	return "class name";
}
```
If set to null, the class name won't be shown.

