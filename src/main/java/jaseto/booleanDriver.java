package jaseto;

final class booleanDriver extends PrimitiveDriver
{

	@Override
	public Object toObject(String s)
	{
		return Boolean.valueOf(s);
	}

	@Override
	public Class getHandledType()
	{
		return boolean.class;
	}
	
	@Override
	protected void adapt(AttributeMap attr)
	{
		attr.put("class", "boolean[]");
	}
}