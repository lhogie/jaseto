package jaseto;

public class CharArrayDriver extends StringableDriver
{
	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
		super.adaptAttributes(attr, o);
		attr.put("class", "char[]");
	}

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
}
