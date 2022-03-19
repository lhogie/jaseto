package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import toools.text.xml.XML;

public class Serialize
{
	public static void main(String[] args)
			throws SAXException, ParserConfigurationException, IOException
	{
		StringWriter sw = new StringWriter();
		Jaseto.print(new DemoType(), new PrintWriter(sw));
		String xml = sw.getBuffer().toString();
		System.out.println(xml);
		System.out.println(XML.parseXML(xml, false));
	}
}
