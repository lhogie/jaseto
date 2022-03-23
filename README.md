# Jaseto

Jaseto is JSON serializer for Java objects. It can be confronted to [Jackson serializer](https://www.baeldung.com/jackson-custom-serialization) and [GSON serializer](https://github.com/google/gson) but it tackles a different problem.

Jackson and GSON, just like most serialization tools on the market enable the interoperation of heterogenous applications, by providing both serialization and deserialization. They are complex librairies which also suffer from limitations like the impossibility to work in the presence of cyclic aggregation, and limited ability to customize the output JSON.

Jaseto focuses on a specific (yet very frequent) application of serializers: exposing object models to Web applications written in Javascript, using JSON encoding. 
This its particular purpose, Jaseto:
- features __programmatic customization__ API (many other tools annotation-based descriptive approaches)
- has highly __flexible and deeper customization__ abilities
- __supports cyclic aggregation__, making it able to __serialize graphs__
- does not provide deserialization (__yet__)
Also the API of Jaseto has been designed to be as simple as possible.

Jaseto is being developed in the context of Research experiments at [I3S laboratory](https://www.i3s.unice.fr/) of [Universté Côte d'Azur](https://univ-cotedazur.eu/). In particular it powers the Web interface of the 
[Idawi](https://github.com/lhogie/idawi) decentralized component system.

# Usage
Serializing an object of following type:
```java
class DemoType {
		String aString = "bar";
		boolean loveIsAll = true;
		Boolean aBooleanObject = false;
		double pi = Math.PI;
		long maxLongValue = Long.MAX_VALUE;
		Object aNullReference = null;
		Object[] anArrayOfObjects = new Object[] { "Java", true, 9.8 };
		char[] anArrayOfPrimitiveValues = "abcdef".toCharArray();
		Object myself = this;
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
	"#ID": 2111991224,
	"#class": "jaseto.Demo$DemoType",
	"aBooleanObject": {
		"#class": "java.lang.Boolean",
		"value": false
	},
	"aNullReference": null,
	"aString": "bar",
	"anArrayOfObjects": ["Java", {
			"#class": "java.lang.Boolean",
			"value": true
		}, {
			"#class": "java.lang.Double",
			"value": 9.8
		}, {"#type": "link", "src": 2111991224}
	],
	"anArrayOfPrimitiveValues": ["a", "b", "c", "d", "e", "f"],
	"loveIsAll": true,
	"maxLongValue": 9223372036854775807,
	"myself": {"#type": "link", "src": 2111991224},
	"pi": 3.141592653589793
}
```

## Customization of the JSON text
The SerializationController interface enables deep customization of the output JSON text. A default implementation is provided.
It provides a set of methods that can be implemented so as to match as much as possible the requirements of the client application.
```java
public interface Customizer {
	Object substitute(Object o);

	String fieldName(JasetoField field, Object from);

	void alterMap(Map<String, Node> keys, Object from);

	String className(Object o);

	String toString(Object o);

	public String getClassNameKey();

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

This can be used to other purpose. For example if you want to prevent non-serializable objects to be refused, you do something like this:
```java
@Override
public Object substitute(Object o) {
	if (o instanceof Serializable) {
		return o;
	}
	
	throw new NotSerializableException("class " + o.getClass() + " is not serializable");	
}
```


### Renaming a particular field
This example convert all field names to upper case.
```java
@Override
public String fieldName(Field field) {
	return field.getName().toUpperCase();
}
```


### Exluding a particular field
When the new name is set to null, the field is dropped.
```java
@Override
public String fieldName(Field field) {
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
public void alterMap(Map<String, Node> keys, Object from) {
	keys.put("visible", from.getColor() != from.getParent().getColor());
}
```

### Renaming a class name
This prints pretty type names for usual Java types.
```java
@Override
public String className(Class<? extends Object> class1) {
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


### Not showing the class name
When the class name is set to null, it is not shown.
```java
@Override
public String className(Class<? extends Object> class1) {
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

