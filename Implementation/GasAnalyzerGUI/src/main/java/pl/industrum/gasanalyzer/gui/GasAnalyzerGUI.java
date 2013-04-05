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
			data.add(0x10);
			data.add(0x1);
			data.add(0xF0);
			data.add(0x20);
			data.add(0x18);
			data.add(0x4);
			data.add(0x6B);
			data.add(0x2);
			data.add(0x30);
			data.add(0x0);
			data.add(0x2);
			data.add(0x0);
			data.add(0x2);
			data.add(0x0);
			data.add(0x30);
			data.add(0x0);
			data.add(0x2);
			data.add(0x0);
			data.add(0x7);
			data.add(0x0);
			data.add(0x30);
			data.add(0x2E);
			data.add(0x30);
			data.add(0x30);
			data.add(0x0);
			data.add(0xA);
			data.add(0x0);
			data.add(0x3);
			data.add(0x0);
			data.add(0x32);
			data.add(0x30);
			data.add(0x2E);
			data.add(0x39);
			data.add(0x39);
			data.add(0x0);
			data.add(0xA);
			data.add(0x0);
			data.add(0xC);
			data.add(0x0);
			data.add(0x39);
			data.add(0x38);
			data.add(0x32);
			data.add(0x0);
			data.add(0x23);
			data.add(0x0);
			data.add(0x64);
			data.add(0x0);
			data.add(0x10);
			data.add(0x3);

			int crc = ELANCRC16.countCRC16(data);      
			String s = Integer.toHexString(crc); 
			System.out.println("CRC16 ramki testowej: "+crc+" ("+s+")");
			
			GasAnalyzerMainWindow window = new GasAnalyzerMainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}		       
	}       
}
