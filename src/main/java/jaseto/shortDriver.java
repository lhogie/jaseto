package jaseto;

final class shortDriver extends StringableDriver
{

	@Override
	public Object toObject(String s)
	{
		return Short.valueOf(s);
	}

	@Override
	protected String getClassName()
	{
		return "short";
	}
}