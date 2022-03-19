package jaseto;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

import jaseto.Parser_CLike.Attr;
import toools.reflect.Clazz;

public abstract class Driver {
	public void print(Object object, PrintWriter w, Registry registry, int depth) {
		for (int i = 0; i < depth; ++i) {
			w.print('\t');
		}

		if (object != null && registry.contains(object)) {
			w.print(registry.id(object));
			w.print('\n');
		}
		else {
			registry.add(object);
			w.print(getTypeName(object));
			w.print(' ');
			w.print(registry.id(object));

			String stringRepresentation = toString(object);

			if (stringRepresentation != null) {
				w.print(' ');
				w.print('=');
				w.print(' ');
				w.print(stringRepresentation);
				w.print('\n');
			}
			else {
				w.print('\n');
				forEachChildOf(object, c -> print(c, w, registry, depth + 1));
			}
		}
	}

	protected String getTypeName(Object o) {
		return o.getClass().getName();
	}

	protected void adapt(AttributeMap attributes) {

	}

	public abstract String toString(Object o);

	public abstract void forEachChildOf(Object o, Consumer c);

	public Object instantiate(String classname, List<Attr> attributes) {
		Class clazz = Clazz.findClassOrFail(classname);
		return Clazz.makeInstanceOrFail(clazz);
	}

	public abstract void attachChild(Object parent, Object child, int childIndex);
}
