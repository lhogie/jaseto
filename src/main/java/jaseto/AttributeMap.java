package jaseto;

import java.util.HashMap;
import java.util.Iterator;

public class AttributeMap extends HashMap<String, String>
{
	public String elementName = "object";
	
	@Override
	public String toString()
	{
		String s = "";

		Iterator<Entry<String, String>> i = entrySet().iterator();

		while (i.hasNext())
		{
			Entry<String, String> entry = i.next();
			s += entry.getKey() + "=\"" + entry.getValue() + "\"";

			if (i.hasNext())
			{
				s += " ";
			}
		}

		return s;
	}

}
