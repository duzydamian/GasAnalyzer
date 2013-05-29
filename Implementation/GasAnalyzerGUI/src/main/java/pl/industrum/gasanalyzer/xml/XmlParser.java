/**
 * 
 */
package pl.industrum.gasanalyzer.xml;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class XmlParser
{

	private Document xml;

	/**
	 * 
	 */
	public XmlParser()
	{		
        try
		{
        	SAXReader reader = new SAXReader();
			xml = reader.read( "devicesConfiguration.xml" );

			// Pretty print the document to System.out
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter( System.out, format );
			writer.write( xml );
			
			Element devices = xml.getRootElement();
			
			// iterate through child elements of root
	        for ( Iterator<?> i = devices.elementIterator(); i.hasNext(); )
	        {
	            Element device = (Element) i.next();
	            for( Iterator<?> i2 = device.attributeIterator(); i2.hasNext(); )
				{
					Attribute deviceAattribute = ( Attribute ) i2.next();
					deviceAattribute.getName();
					deviceAattribute.getValue();
				}
	            
	            for ( Iterator<?> i3 = device.elementIterator(); i3.hasNext(); )
	            {
	            	Element measuredValue = (Element) i3.next();
	            	for( Iterator<?> i4 = measuredValue.attributeIterator(); i4.hasNext(); )
					{
						Attribute measuredValueAttribute = ( Attribute ) i4.next();
						measuredValueAttribute.getName();
						measuredValueAttribute.getValue();
					}
	            }
	        }
		}
        catch ( DocumentException e )
		{
			e.printStackTrace();
		}
        catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
        catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

}
