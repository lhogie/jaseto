package jaseto;

final class longDriver extends PrimitiveDriver
{

	@Override
	public Object toObject(String s)
	{
		return Long.valueOf(s);
	}

	@Override
	public Class getHandledType()
	{
		return long.class;
	}
}