package jaseto;

public  class doubleDriver extends StringableNode
{

	@Override
	public Object toObject(String s)
	{
		return Double.valueOf(s);
	}

}