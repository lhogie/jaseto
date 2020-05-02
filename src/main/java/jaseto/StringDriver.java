package jaseto;

public class StringDriver extends StringableDriver
{

	@Override
	public String toObject(String s)
	{
		return s;
	}

	@Override
	public Class getHandledType()
	{
		return String.class;
	}

	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
		super.adaptAttributes(attr, o);

		attr.remove("id");
	}
}
