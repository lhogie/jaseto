package jaseto2;

final class longDriver extends StringableDriver
{

	@Override
	public Object toObject(String s)
	{
		return Long.valueOf(s);
	}

	@Override
	protected String getClassName()
	{
		return "long";
	}
}