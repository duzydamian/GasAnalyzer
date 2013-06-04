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
	public XmlCreator(TableItem[] tableItems)
	{
		xml = DocumentHelper.createDocument();
		xml.setXMLEncoding("UTF-8");
		Element devices = xml.addElement( "devices" );		
		
		for( TableItem item: tableItems )
		{
			Element device = devices.addElement( "device" );		
			device.addAttribute( "name", item.getText( 2 ) );
			device.addAttribute( "address", item.getText( 1 ) );
			device.addAttribute( "type", item.getText( 3 ) );
			
			//TODO add precision definition
			Element measured_value = device.addElement( "measured_value" );
			measured_value.addAttribute( "name", "todo" );
			measured_value.addAttribute( "precision", "todo" );
		}
		
		try{
			// lets write to a file
			XMLWriter writer = new XMLWriter( new FileWriter(
					"devicesConfiguration.xml" ) );
			writer.write( xml );
			writer.close();
        
			// Pretty print the document to System.out
			OutputFormat format = OutputFormat.createPrettyPrint();
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
