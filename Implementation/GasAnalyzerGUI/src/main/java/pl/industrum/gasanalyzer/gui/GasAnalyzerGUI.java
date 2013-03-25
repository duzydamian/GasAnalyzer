/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

import pl.industrum.gasanalyzer.elan.ELANConnection;

/**
 * @author duzydamian
 *
 */
public class GasAnalyzerGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Gas Analyzer");
        showList();
        
        listPorts();
        
        try
        {
            //(new TwoWaySerialComm()).connect("/dev/ttyUSB0");
        	(new ELANConnection()).connect("/dev/ttyS0");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    public static void showList()
    {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();   

        while(ports.hasMoreElements())
            System.out.println(((CommPortIdentifier)ports.nextElement()).getName());
    }
    
    static void listPorts()
    {
        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) 
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
        }        
    }
    
    static String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
}
