package jaseto;

final class floatDriver extends PrimitiveDriver
{

	@Override
	public Object toObject(String s)
	{
		return Float.valueOf(s);
	}

	@Override
	public Class getHandledType()
	{
		return float.class;
	}

}