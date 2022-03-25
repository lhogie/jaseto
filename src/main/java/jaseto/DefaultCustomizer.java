package jaseto;

import toools.reflect.Introspector.JasetoField;

public class DefaultCustomizer implements Customizer {

	@Override
	public boolean accept(JasetoField field, Object value, Object from) {
		// accepts all fields
		return true;
	}

	@Override
	public Object substitute(Object o) {
		// no substitution
		return o;
	}

	@Override
	public boolean treatBoxedAsPrimitives() {
		return true;
	}

	@Override
	public void alter(ObjectNode n) {
		// don't change anything		
	}

}
