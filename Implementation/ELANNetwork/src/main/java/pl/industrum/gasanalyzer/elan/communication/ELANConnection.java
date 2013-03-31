package pl.industrum.gasanalyzer.elan.communication;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Vector;

/**
 * Class of connection.
 * 
 * @author duzydamian (Damian Karbowiak)
 * @see gnu.io.CommPort
 * @see gnu.io.SerialPort
 */
public class ELANConnection {
	
	/**
	 * Instance for singleton
	 */
	private static ELANConnection instance = null;
	
	/**
	 * Interface Parameters
	 * Level RS485
	 */
	static int ELAN_BAUD_RATE = 9600;
	static int ELAN_DATA_BITS = SerialPort.DATABITS_8;
	static int ELAN_STOP_BITS = SerialPort.STOPBITS_1;
	static int ELAN_PARITY = SerialPort.PARITY_NONE;

	BufferedReader is; 
	PrintStream os;
	SerialPort serialPort;
			
	/**
	 * Default constructor
	 */
	protected ELANConnection() {
		super();
		//
		// Open the input Reader and output stream. The choice of a
		// Reader and Stream are arbitrary and need to be adapted to
		// the actual application. Typically one would use Streams in
		// both directions, since they allow for binary data transfer,
		// not only character data transfer.
		//
		is = null; // for demo purposes only. A stream would be more typical.
		os = null;
	}	
	
	/**
	 * @return
	 */
	public synchronized static ELANConnection getInstance() {
		if (instance == null) {
			instance = new ELANConnection();
		}
		return instance;
	}
	
	/**
	 * Connect to port given by portName.
	 * @param portName name of port which you want to connect
	 * @throws Exception
	 */
	public void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);

			if (commPort instanceof SerialPort) {
				System.out.println("Port " + portName + " successfull open.");
				serialPort = (SerialPort) commPort;

				serialPort.setSerialPortParams(ELAN_BAUD_RATE, ELAN_DATA_BITS,
						ELAN_STOP_BITS, ELAN_PARITY);

				try {
					is = new BufferedReader(new InputStreamReader(
							serialPort.getInputStream()));
				} catch (IOException e) {
					System.err.println("Can't open input stream: write-only");
					is = null;
				}

				//
				// New Linux systems rely on Unicode, so it might be necessary
				// to
				// specify the encoding scheme to be used. Typically this should
				// be US-ASCII (7 bit communication), or ISO Latin 1 (8 bit
				// communication), as there is likely no modem out there
				// accepting
				// Unicode for its commands. An example to specify the encoding
				// would look like:
				//
				// os = new PrintStream(port.getOutputStream(), true,
				// "ISO-8859-1");
				//
				os = new PrintStream(serialPort.getOutputStream(), true);

				//
				// Actual data communication would happen here
				// performReadWriteCode();
				//

				// Read the response
				// String response = is.readLine(); // if you sent "AT" then
				// response == "OK"
				// System.out.println(response);

				// System.out.println("Write to port");
				// os.print("test");
			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	public void disconnect() {
		//
		// It is very important to close input and output streams as well
		// as the port. Otherwise Java, driver and OS resources are not
		// released.
		//
		try {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			if (serialPort != null)
				serialPort.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int read() {
		try {
			return is.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -2;
	}

	public void write(int byteToWrite) {
		os.write(byteToWrite);
	}
	
	@SuppressWarnings("unchecked")
	public static Vector<String> vectorPorts() {

		Vector<String> ports = new Vector<String>();
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			ports.add(portIdentifier.getName());
		}
		return ports;
	}
	
	@SuppressWarnings("unchecked")
	public static void listPorts() {
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier
				.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			System.out.println(portIdentifier.getName() + " - "
					+ getPortTypeName(portIdentifier.getPortType()));
		}
	}
	    
	static String getPortTypeName(int portType) {
		switch (portType) {
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
