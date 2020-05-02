package jaseto;

public class intDriver extends PrimitiveDriver
{

	@Override
	public Object toObject(String s)
	{
		return Integer.valueOf(s);
	}

	
	@Override
	public Class getHandledType()
	{
		return int.class;
	}


}