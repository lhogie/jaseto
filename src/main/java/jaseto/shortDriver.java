package jaseto;

final class shortDriver extends PrimitiveDriver
{

	@Override
	public Object toObject(String s)
	{
		return Short.valueOf(s);
	}

	

	@Override
	public Class getHandledType()
	{
		return short.class;
	}

}