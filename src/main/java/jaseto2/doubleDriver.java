package jaseto2;

final class doubleDriver extends StringableDriver
{

	@Override
	public Object toObject(String s)
	{
		return Double.valueOf(s);
	}

	@Override
	protected String getClassName()
	{
		return "double";
	}
}