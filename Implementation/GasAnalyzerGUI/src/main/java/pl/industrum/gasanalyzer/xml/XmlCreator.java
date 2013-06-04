/**
 * 
 */
package pl.industrum.gasanalyzer.xml;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.swt.widgets.TableItem;

import pl.industrum.gasanalyzer.model.Device;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class XmlCreator
{

	private Document xml;

	/**
	 * @param tableItems 
	 * 
	 */
	public XmlCreator(Device devicesList)
	{
		xml = DocumentHelper.createDocument();
		xml.setXMLEncoding("UTF-8");
		Element devices = xml.addElement( "devices" );		
		
		//for( Device device: devicesList )
		{
//			Element deviceElement = devices.addElement( "device" );		
//			deviceElement.addAttribute( "name", device.getName() );
//			deviceElement.addAttribute( "address", String.valueOf( device.getAddress() ) );
//			deviceElement.addAttribute( "type", device.getDeviceType().getType() );
			
			//TODO add precision definition
			//for( iterable_type iterable_element: device.get )
			{
//				Element measured_value = deviceElement.addElement( "measured_value" );
//				measured_value.addAttribute( "name", device );
//				measured_value.addAttribute( "precision", device );
			}			
		}
		
		try{
			OutputFormat format = OutputFormat.createPrettyPrint();
			
			// lets write to a file
			XMLWriter writer = new XMLWriter( new FileWriter(
					"devicesConfiguration.xml" ), format );
			writer.write( xml );
			writer.close();
        
			// Pretty print the document to System.out			
			writer = new XMLWriter( System.out, format );
			writer.write( xml );
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * @return the xml
	 */
	public Document getXml()
	{
		return xml;
	}	
}
