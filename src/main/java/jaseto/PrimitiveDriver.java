package jaseto;

public abstract class PrimitiveDriver extends StringableDriver
{


	@Override
	protected void adapt(AttributeMap attr)
	{
//		super.adaptAttributes(attr, o);
	
		// because the type is stored as the name of the element
		attr.remove("class");
		attr.elementName = getHandledType().getName();

		// they are immutable, so no ID
		attr.remove("id");
	}
}
