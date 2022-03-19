package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Test
{
	public static void main(String[] args)
			throws SAXException, ParserConfigurationException, IOException
	{
		StringWriter sw = new StringWriter();
		DemoType startObject = new DemoType();
		Jaseto.print(startObject, new PrintWriter(sw));
		String xml = sw.getBuffer().toString();

		Object o = Jaseto.toObject(new StringReader(xml));

		if (startObject.equals(o))
		{
			System.out.println("Test ok! :)");
		}
		else
		{
			System.out.println("Test failed! :(");
			System.out.println("Original object:\t " + startObject);
			System.out.println("XML: " + xml);
			System.out.println("Deserialized object:\t " + o);
		}
	}
}
