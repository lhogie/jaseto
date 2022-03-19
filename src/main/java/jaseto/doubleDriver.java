package jaseto;

final class doubleDriver extends PrimitiveDriver
{

	@Override
	public Object toObject(String s)
	{
		return Double.valueOf(s);
	}

	@Override
	public Class getHandledType()
	{
		return double.class;
	}

}