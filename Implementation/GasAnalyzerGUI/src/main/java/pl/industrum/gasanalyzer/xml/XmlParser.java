/**
 * 
 */
package pl.industrum.gasanalyzer.xml;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import pl.industrum.gasanalyzer.model.Device;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class XmlParser
{

	private Document xml;
	Vector<Device> devicesFromDatabaseWithPrecision;

	/**
	 * 
	 */
	public XmlParser(Vector<Device> devicesFromDatabase)
	{		
		devicesFromDatabaseWithPrecision = devicesFromDatabase;
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
	        	int id = 0;
	            Element device = (Element) i.next();
	            for( Iterator<?> i2 = device.attributeIterator(); i2.hasNext(); )
				{
					Attribute deviceAattribute = ( Attribute ) i2.next();					
					if ( deviceAattribute.getName().equalsIgnoreCase( "id" ) )
					{
						id = Integer.valueOf( deviceAattribute.getValue() );
					}
				}
	            
	            for ( Iterator<?> i3 = device.elementIterator(); i3.hasNext(); )
	            {
	            	HashMap<String, Integer> measurementPrecision = new HashMap<String, Integer>();
	            	Element measuredValue = (Element) i3.next();
	            	for( Iterator<?> i4 = measuredValue.attributeIterator(); i4.hasNext(); )
					{
						Attribute measuredValueAttributeName = ( Attribute ) i4.next();
						Attribute measuredValueAttributeValue = ( Attribute ) i4.next();						
						measurementPrecision.put( measuredValueAttributeName.getValue(), Integer.valueOf( measuredValueAttributeValue.getValue() ) );
					}

	            	for( Device device2: devicesFromDatabaseWithPrecision )
					{						
						if ( device2.getId() == id )
						{
							device2.setMeasurementPrecisionMap( measurementPrecision );
						}
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

	/**
	 * @return the devicesFromDatabaseWithPrecision
	 */
	public Vector<Device> getDevicesFromDatabaseWithPrecision()
	{
		return devicesFromDatabaseWithPrecision;
	}

}
