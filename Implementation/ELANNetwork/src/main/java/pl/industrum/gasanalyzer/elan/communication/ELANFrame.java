package pl.industrum.gasanalyzer.elan.communication;

import java.util.LinkedList;
import java.util.Queue;

public class ELANFrame { 
	private int CRC16;
	private Queue<Integer> data = new LinkedList<Integer>();
	
	public ELANFrame( Queue<Integer> frame ) 
	{
		this.countCRC( frame );
	}
	
	public ELANFrame(ELANFrame frame) 
	{
		this.data = frame.data;
		this.CRC16 = frame.CRC16;
	}
	
	public ELANFrame() {
		super();
	}
	
	public void countCRC( Queue<Integer> frame ) 
	{
		Integer CRC;
		
		//count CRC
		CRC = 0;
		
		this.CRC16 = CRC;
	}

	public boolean checkCRC( int readCrc ) 
	{
		int crcCount = ELANCRC16.countCRC16(data);
	
		return (crcCount == readCrc);
	}
	
	public void add(int character)
	{
		this.data.add(character);
	}
	
	public int getCRC16() 
	{
		return CRC16;
	}

	public void setCRC16(int crc16) 
	{
		CRC16 = crc16;
	}
	
}
