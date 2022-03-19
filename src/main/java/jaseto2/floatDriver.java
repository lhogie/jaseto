package jaseto2;

final class floatDriver extends StringableDriver
{

	@Override
	public Object toObject(String s)
	{
		return Float.valueOf(s);
	}

	@Override
	protected String getClassName()
	{
		return "float";
	}
}