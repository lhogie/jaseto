package jaseto;

final class charDriver extends PrimitiveDriver
{
	@Override
	public Object toObject(String s)
	{
		return s.charAt(0);
	}

	@Override
	public Class getHandledType()
	{
		return char.class;
	}

}