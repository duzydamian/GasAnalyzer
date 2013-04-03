package pl.industrum.gasanalyzer.elan.communication;

import java.util.Queue;

public class ELANFrame { 
	private Integer CRC;
	
	public ELANFrame( Queue<Integer> frame ) {
		this.countCRC( frame );
	}
	
	public void countCRC( Queue<Integer> frame ) {
		Integer CRC;
		
		//count CRC
		CRC = 0;
		
		this.CRC = CRC;
	}
	
	public Integer getCRC() {
		return this.CRC;
	}
}
