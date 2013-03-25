/**
 * 
 */
package pl.industrum.gasanalyzer.communication;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @author duzydamian
 *
 */
public class ELANConnection {
	
	/**
	 * Interface Parameters
	 * Level RS485
	 */
	static int ELAN_BAUD_RATE = 9600;
	static int ELAN_DATA_BITS = SerialPort.DATABITS_8;
	static int ELAN_STOP_BITS = SerialPort.STOPBITS_1;
	static int ELAN_PARITY = SerialPort.PARITY_NONE;

	
	public void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
               // serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                serialPort.setSerialPortParams(
                		ELAN_BAUD_RATE,
                	    ELAN_DATA_BITS,
                	    ELAN_STOP_BITS,
                	    ELAN_PARITY);

                	//
                	// Open the input Reader and output stream. The choice of a
                	// Reader and Stream are arbitrary and need to be adapted to
                	// the actual application. Typically one would use Streams in
                	// both directions, since they allow for binary data transfer,
                	// not only character data transfer.
                	//
                	BufferedReader is = null;  // for demo purposes only. A stream would be more typical.
                	PrintStream    os = null;

                	try {
                	  is = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                	} catch (IOException e) {
                	  System.err.println("Can't open input stream: write-only");
                	  is = null;
                	}

                	//
                	// New Linux systems rely on Unicode, so it might be necessary to
                	// specify the encoding scheme to be used. Typically this should
                	// be US-ASCII (7 bit communication), or ISO Latin 1 (8 bit
                	// communication), as there is likely no modem out there accepting
                	// Unicode for its commands. An example to specify the encoding
                	// would look like:
                	//
//                	     os = new PrintStream(port.getOutputStream(), true, "ISO-8859-1");
                	//
                	os = new PrintStream(serialPort.getOutputStream(), true);

                	  
                	//
                	// Actual data communication would happen here
                	// performReadWriteCode();
                	//

                	// Read the response
                	//String response = is.readLine(); // if you sent "AT" then response == "OK"
                	//System.out.println(response);
                	
                	os.print("test");
                	

                	int test = is.read();
            		System.out.print(Integer.toHexString(test)+"H ");                	
                	while(test !=-1){
                		test = is.read();                		
                		System.out.print(Integer.toHexString(test)+"H ");                	         	            	
                	}
                	
                	
                	//
                	// It is very important to close input and output streams as well
                	// as the port. Otherwise Java, driver and OS resources are not released.
                	//
                	if (is != null) is.close();
                	if (os != null) os.close();
                	if (serialPort != null) serialPort.close();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
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
