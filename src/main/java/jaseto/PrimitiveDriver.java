package jaseto;

public abstract class PrimitiveDriver extends StringableDriver
{

	@Override
	protected String getElementName()
	{
		return getHandledType().getName();
	}

	@Override
	protected void adaptAttributes(AttributeMap attr, Object o)
	{
		super.adaptAttributes(attr, o);
	
		// because the type is stored as the name of the element
		attr.remove("class");

		// they are immutable, so no ID
		attr.remove("id");
	}
}
