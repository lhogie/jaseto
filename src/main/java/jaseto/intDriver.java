package jaseto;

public class intDriver extends StringableDriver
{

	@Override
	public Object toObject(String s)
	{
		return Integer.valueOf(s);
	}

	@Override
	protected String getClassName()
	{
		return "int";
	}
}