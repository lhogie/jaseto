package jaseto;

public class CharArrayDriver extends StringableDriver
{

	@Override
	public String toString(Object o)
	{
		return new String((char[]) o);
	}

	@Override
	public char[] toObject(String s)
	{
		return s.toCharArray();
	}

	@Override
	public Class getHandledType()
	{
		return char[].class;
	}
	
	@Override
	protected void adapt(AttributeMap attr)
	{
		attr.put("class", "char[]");
	}
}
