package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.FF;
import toools.text.xml.DNode;

public class IntrospectingDriver extends Driver {
	@Override
	public ObjectNode toNode(Object o, Registry registry) {
		ObjectNode n = new ObjectNode("object", o, registry);
		n.setClass(o.getClass());

		for (FF field : Introspector.getIntrospector(o.getClass()).getFields()) {
			if (!field.isStatic() && !field.isTransient()) {
				Driver driver = Jaseto.getDriver(field.getType());
				Object value = field.get(o);

				// if the object is stringable and its real class can be
				// guessed out of the field type
				if (driver instanceof StringableDriver
						&& (field.getType().isPrimitive() || value.getClass() == field.getType())) {
					ObjectNode fn = new ObjectNode(field.getName(), value,
							field.getType().isPrimitive() ? null : registry);
					fn.getAttributes().put("value", ((StringableDriver) driver).toString(value));
					fn.setParent(n);
				} else {
					ObjectNode fn = Jaseto.toNode(value, registry);
					fn.setParent(n);
					fn.setName(field.getName());

					if (value != null && fn.getClassName() == null && value.getClass() != field.getType()) {
						fn.setClass(value.getClass());
					}
				}
			}
		}

		return n;

	}

	@Override
	public Object toObject(DNode n) {
		return n.getAttributes().get("value");
	}

}
