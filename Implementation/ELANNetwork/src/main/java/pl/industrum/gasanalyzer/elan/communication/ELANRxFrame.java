package pl.industrum.gasanalyzer.elan.communication;

import java.util.Date;
import java.util.Queue;

public class ELANRxFrame extends ELANFrame {
	private Date timeStamp;
	
	public ELANRxFrame( Queue<Integer> frame ) {
		super( frame );
		this.timeStamp = new Date();
	}
	
//	public ELANRxFrame( ELANFrame frame ) {
//		super( frame );
//		this.timeStamp = new Date();
//	}
	
	public Date getTimeStamp() {
		return this.timeStamp;
	}

}
