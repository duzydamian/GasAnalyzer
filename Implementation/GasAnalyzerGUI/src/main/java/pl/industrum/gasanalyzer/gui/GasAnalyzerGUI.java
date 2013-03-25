/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

/**
 * @author duzydamian
 *
 */
public class GasAnalyzerGUI {

	ELANConnection elanConnection;
	
	public GasAnalyzerGUI() {
		super();
		elanConnection = new ELANConnection();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Gas Analyzer");
        
		elanConnection.listPorts();
        
        try
        {
            //(new TwoWaySerialComm()).connect("/dev/ttyUSB0");
        	elanConnection.connect("/dev/ttyS0");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}       
}
