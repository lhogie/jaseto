package jaseto;

final class byteDriver extends PrimitiveDriver
{
	@Override
	public Object toObject(String s)
	{
		return Byte.parseByte(s);
	}

	@Override
	public Class getHandledType()
	{
		return byte.class;
	}

}