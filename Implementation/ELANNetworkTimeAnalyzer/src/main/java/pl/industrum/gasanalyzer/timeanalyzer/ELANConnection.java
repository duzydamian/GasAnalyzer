package pl.industrum.gasanalyzer.timeanalyzer;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.RXTXVersion;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import pl.industrum.gasanalyzer.elan.types.ELANConnectionState;

/**
 * Class of connection.
 * 
 * @author duzydamian (Damian Karbowiak)
 * @see gnu.io.CommPort
 * @see gnu.io.SerialPort
 */
public class ELANConnection
{
	/**
	 * Interface Parameters
	 * Level RS485
	 */	
	/**
	 * ELAN Baud rate
	 */
	static int ELAN_BAUD_RATE = 9600;
	
	/**
	 * ELAN Data bits
	 */
	static int ELAN_DATA_BITS = SerialPort.DATABITS_8;
	
	/**
	 * ELAN Stop bit
	 */
	static int ELAN_STOP_BITS = SerialPort.STOPBITS_1;
	
	/**
	 * ELAN Parity
	 */
	static int ELAN_PARITY = SerialPort.PARITY_NONE;

	/**
	 * Input stream
	 */
	InputStream is;
	
	/**
	 * Output stream
	 */
	PrintStream os;
	
	/**
	 * Object stores information about port you connect to.
	 */
	SerialPort serialPort;
	
	/**
	 * Object stores information about port you connect to.
	 */
	CommPort commPort;

			
	/**
	 * Default constructor
	 */
	public ELANConnection()
	{
		super();
		this.is = null;
		this.os = null;
		
		listPorts();
	}
	
	/**
	 * Connects to port given by portName.
	 * 
	 * @param portName name of port which you want to connect
	 * @throws Exception
	 */
	public ELANConnectionState connect(String portName) throws Exception
	{
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned())
		{
			System.out.println("Error: Port is currently in use");
			return ELANConnectionState.CURRENTLY_IN_USE;
		}
		else
		{
			commPort = portIdentifier.open(this.getClass().getName(), 2000);
			Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
			Set<Thread> keySet = allStackTraces.keySet();
			for( Thread thread: keySet )
			{
				if ( thread.getClass().getCanonicalName().equalsIgnoreCase( "gnu.io.RXTXPort.MonitorThread" ) )
				{
					thread.setName( "COMM_PORT OPEN[" + portName + "]" );
				}				
			}
			
			if (commPort instanceof SerialPort)
			{
				//System.out.println("Port " + portName + " successfull open.");
				serialPort = (SerialPort) commPort;

				serialPort.setSerialPortParams(ELAN_BAUD_RATE, ELAN_DATA_BITS, ELAN_STOP_BITS,
												ELAN_PARITY);

				try
				{
					// Assign input stream from port to variable
					is = serialPort.getInputStream();
				}
				catch (IOException e)
				{
					System.err.println("Can't open input stream: write-only");
					is = null;
					return ELANConnectionState.WRITE_ONLY;
				}
				
				// Assign output stream from port to variable
				os = new PrintStream(serialPort.getOutputStream(), true);							
            	
            	return ELANConnectionState.CONNECTED;
			}
			else
			{
				return ELANConnectionState.NO_SERIAL_PORT;
			}
		}
	}

	/**
	 * Disconnects from port. Close connected streams. 
	 */
	public synchronized void disconnect()
	{
		try
		{			
			if (commPort != null)
				commPort.close();
			if (serialPort != null)
			{
				serialPort.removeEventListener();
				serialPort.close();
			}			
			if (is != null)
				is.close();
			if (os != null)
				os.close();			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	/**
	 * Read one char from input.
	 * 
	 * @return The character read, as an integer in the range 0 to 65535 (0x00-0xffff), or -1 if the end of the stream has been reached
	 * @see BufferedReader 
	 */
	public int read()
	{		
		try 
		{
			return is.read();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return -2;
	}

	/**
	 * Write one char to stream.
	 *  
	 * @param byteToWrite Value need to be send
	 */
	public void write(int byteToWrite)
	{
		os.write(byteToWrite);
	}	
	
	@SuppressWarnings("unchecked")
	public static Vector<String> vectorPorts()
	{
		Vector<String> ports = new Vector<String>();
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements())
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			ports.add(portIdentifier.getName());
		}
		return ports;
	}
	
	@SuppressWarnings("unchecked")
	public static Vector<String> vectorPortsOnlySerial()
	{
		Vector<String> ports = new Vector<String>();
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements())
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			if( portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL )
				ports.add(portIdentifier.getName());
		}
		return ports;
	}
	
	@SuppressWarnings("unchecked")
	public static void listPorts()
	{		
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements())
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			System.out.println(portIdentifier.getName() + " - "	+ getPortTypeName(portIdentifier.getPortType()));
		}
	}
	    
	static String getPortTypeName(int portType)
	{
		switch (portType)
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
			return "Unknown type";
		}
	}
	
	public static String getRXTXVersion()
	{
		return RXTXVersion.getVersion();
	}
	
	public static String getNativeRXTXVersion()
	{
		return RXTXVersion.nativeGetVersion();
	}
}
