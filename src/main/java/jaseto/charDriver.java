package jaseto;

final class charDriver extends StringableDriver
{
	@Override
	public Object toObject(String s)
	{
		return s.charAt(0);
	}

	@Override
	protected String getClassName()
	{
		return "char";
	}
}