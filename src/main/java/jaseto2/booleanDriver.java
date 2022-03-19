package jaseto2;

final class booleanDriver extends StringableDriver implements ClassDriver
{

	@Override
	public Class getSupportedClass()
	{
		return boolean.class;
	}
	
	@Override
	public Object toObject(String s)
	{
		return Boolean.valueOf(s);
	}

	@Override
	protected String getClassName()
	{
		return "boolean";
	}

}