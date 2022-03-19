package jaseto2;

public class StringDriver extends StringableDriver
{

	@Override
	public Object toObject(String s)
	{
		return s;
	}

	@Override
	protected String getClassName()
	{
		return "string";
	}

}
