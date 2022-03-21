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



#Usage
```java=
String json = Jaseto.toNode(anObject).toJSON();
```

##customization of the JSON text
The SerializationController interface enables deep customization of the output JSON text. A default implementation is provided.

```java=
String json = Jaseto.toNode(new TestType(), new DefaultSerializationController();
```
				

### renaming a particular field
This example convert all field names to upper case.
```java=
				@Override
				public String fieldName(FF field) {
					return field.getName().toUpperCase();
				}
```


### exluding a particular field
```java=
				@Override
				public String fieldName(FF field) {
					return null;
				}
````				
				@Override
				public String classNameAlias(Class<? extends Object> class1) {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void addKeys(Map<String, Node> keys, Object from) {
					// TODO Auto-generated method stub
					
				}
			};```
