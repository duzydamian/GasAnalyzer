/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

import java.util.LinkedList;
import java.util.Queue;

import pl.industrum.gasanalyzer.elan.communication.ELANCRC16;


/**
 * @author duzydamian(Damian Karbowiak)
 *
 */
public class GasAnalyzerGUI {	
	
	public GasAnalyzerGUI() {
		super();		
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Queue<Integer> data = new LinkedList<Integer>();
			data.add(26);
			
			int crc = ELANCRC16.calculateCRC16(data);			
			String s = Integer.toHexString(crc);
			
			GasAnalyzerMainWindow window = new GasAnalyzerMainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}		       
	}       
}
