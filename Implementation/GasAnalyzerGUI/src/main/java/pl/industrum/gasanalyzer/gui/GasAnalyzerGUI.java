/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

import pl.industrum.gasanalyzer.elan.communication.ELANConnection;

/**
 * @author duzydamian
 *
 */
public class GasAnalyzerGUI {

	
	
	public GasAnalyzerGUI() {
		super();		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Gas Analyzer");
        
		ELANConnection elanConnection;
		elanConnection = new ELANConnection();
		
		elanConnection.listPorts();
        
        try
        {
        	elanConnection.connect("/dev/ttyUSB0");
        	//elanConnection.connect("/dev/ttyS0");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}       
}
