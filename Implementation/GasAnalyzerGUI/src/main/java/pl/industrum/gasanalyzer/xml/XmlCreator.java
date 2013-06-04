/**
 * 
 */
package pl.industrum.gasanalyzer.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

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
	public XmlCreator(Vector<Device> devicesList)
	{
		xml = DocumentHelper.createDocument();
		xml.setXMLEncoding("UTF-8");
		Element devices = xml.addElement( "devices" );		
		
		for( Device device: devicesList )
		{
			Element deviceElement = devices.addElement( "device" );
			deviceElement.addAttribute( "id", String.valueOf( device.getId() ) );
			deviceElement.addAttribute( "name", device.getName() );
			deviceElement.addAttribute( "address", String.valueOf( device.getAddress() ) );
			deviceElement.addAttribute( "type", device.getDeviceType().getType() );
			
			//TODO add precision definition
			for( String key: device.getMeasurementPrecisionMap().keySet() )
			{
				Element measured_value = deviceElement.addElement( "measured_value" );
				measured_value.addAttribute( "name", key );
				measured_value.addAttribute( "precision", device.getMeasurementPrecisionMap().get( key ).toString() );
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
