package jaseto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import toools.text.xml.XML;

public class Deserialize
{
	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException
	{
		StringWriter sw = new StringWriter();
		Jaseto.print(new DemoType(), new PrintWriter(sw));
		String xml = sw.getBuffer().toString();
		
		System.out.println(XML.parseXML(xml, false));
		Object o = Jaseto.toObject(new StringReader(xml));
		System.out.println(o);
		
		if (o instanceof SubstitutionObject)
		{
			System.out.println(((SubstitutionObject) o).toJava());
			System.out.println(((SubstitutionObject) o).toClass());
			System.out.println(((SubstitutionObject) o).toRealObject());
		}
	}
}
