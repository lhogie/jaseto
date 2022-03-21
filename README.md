# Jaseto

Jaseto is JSON serializer for Java objects. 

Other such tools to do that include [Jackson serializer](https://www.baeldung.com/jackson-custom-serialization) and GSON. These were made to enable the interoperation of heterogenous applications. To this purpose they provide both serialization and deseriliation. However, a prime application of serializer is to expose object models to Javascript client applications, using JSON encoding. 

Unlike these tools, Jasto:
- provides a comprehensive API
- focuses on exposing an OO system to a text
- does not support deserizaliation
- support cyclic aggregation, making it able to serialize graphs
- is highly customizable


Jaseto is being developed in the context of Research experiments at [I3S laboratory](https://www.i3s.unice.fr/) of [Universté Côte d'Azur](https://univ-cotedazur.eu/).



# Usage
```java
String json = Jaseto.toNode(anObject).toJSON();
```

## Customization of the JSON text
The SerializationController interface enables deep customization of the output JSON text. A default implementation is provided.

```java
String json = Jaseto.toNode(new TestType(), new DefaultSerializationController();
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

### Renaming a class name
This prints pretty type names for usual Java types.
```java
@Override
public String classNameAlias(Class<? extends Object> class1) {
	if (class1 == String.class) {
		return "string";
	} else if (class1 instanceof Set.class) {
		return "set";
	} else if (class1 instanceof List.class) {
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

### Not showing the class name
Serialization only considers fields. Jackson also considers getter methods, assuming that they have no side effect. Jaseto does not take that risk.
But sometimes the set of fields is not enough to completely describe an object. Jaseto enables the users to augment the default description of an object by adding new keys on the fly.
This example describes if an object is visible by comparing its color to the color of its parent graphic environnement.
```java
@Override
public void addKeys(Map<String, Node> keys, Object from) {
	keys.put("visible", from.getColor() != from.getParent().getColor());
}
```
