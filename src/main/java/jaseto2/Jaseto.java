package jaseto2;

import java.util.HashMap;
import java.util.Map;

import toools.text.xml.DNode;

public class Jaseto
{
	private static final Map<Class, Driver> classDrivers = new HashMap<>();

	static
	{
		registerClassDriver(int.class, new intDriver());
		registerClassDriver(boolean.class, new booleanDriver());
		registerClassDriver(byte.class, new byteDriver());
		registerClassDriver(char.class, new charDriver());
		registerClassDriver(short.class, new shortDriver());
		registerClassDriver(long.class, new longDriver());
		registerClassDriver(float.class, new floatDriver());
		registerClassDriver(double.class, new doubleDriver());

		registerClassDriver(Integer.class, new intDriver());
		registerClassDriver(Boolean.class, new booleanDriver());
		registerClassDriver(Byte.class, new byteDriver());
		registerClassDriver(Character.class, new charDriver());
		registerClassDriver(Short.class, new shortDriver());
		registerClassDriver(Long.class, new longDriver());
		registerClassDriver(Float.class, new floatDriver());
		registerClassDriver(Double.class, new doubleDriver());

		registerClassDriver(String.class, new StringDriver());
	}

	public static void registerClassDriver(Class targetClass, Driver driver)
	{
		classDrivers.put(targetClass, driver);
	}

	static Driver getDriver(Object o)
	{
		if (o ==null)
			return new nullDriver();
		
		Driver driver = classDrivers.get(o.getClass());

		if (driver != null)
		{
			return driver;
		}
		else if (o.getClass().isArray())
		{
			return new ArrayDriver();
		}
		else
		{
			return new IntrospectingDriver();
		}
	}

	public static DNode toNode(Object o)
	{
		return toNode(o, new Registry());
	}

	static ObjectNode toNode(Object o, Registry registry)
	{
		if (registry == null)
			registry = new Registry();

		if (registry.contains(o))
		{
			registry.getNode(o).setID(registry.id(o));
			ObjectNode refNode = new ObjectNode("object", o, registry);
			refNode.setID(registry.id(o));
			return refNode;
		}

		return getDriver(o).toNode(o, registry);
	}

}
