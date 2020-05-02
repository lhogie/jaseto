package jaseto;

import toools.reflect.Clazz;

public abstract class ClassDriver extends Driver
{
	public boolean support(Class c)
	{
		// both are primitive
		if (c.isPrimitive() && getHandledType().isPrimitive())
		{
			return c == getHandledType();
		}
		else if (c.isPrimitive())
		{
			return c == Clazz.class_primitives.get(getHandledType());
		}
		else if (getHandledType().isPrimitive())
		{
			return getHandledType() == Clazz.class_primitives.get(c);
		}
		// both are NON-primitve
		else
		{
			return getHandledType().isAssignableFrom(c);
		}
	}

	public abstract Class getHandledType();
}
