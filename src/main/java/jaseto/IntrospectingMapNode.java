package jaseto;

import toools.reflect.Introspector;
import toools.reflect.Introspector.FF;

public class IntrospectingMapNode extends MapNode {

	@Override
	public void fill(Object o, Registry registry) {
		super.fill(o, registry);

		for (FF field : Introspector.getIntrospector(o.getClass()).getFields()) {
			if (!field.isStatic() && !field.isTransient()) {
				Class<? extends Node> fieldNode = Jaseto.lookupNodeClass(field.getType());
				children.put(field.getName(), Jaseto.toNode(field.get(o), fieldNode, registry));
			}
		}
	}
}
