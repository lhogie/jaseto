package jaseto;

final class byteDriver extends StringableDriver
{
	@Override
	public Object toObject(String s)
	{
		return Byte.parseByte(s);
	}

	@Override
	protected String getClassName()
	{
		return "byte";
	}
}