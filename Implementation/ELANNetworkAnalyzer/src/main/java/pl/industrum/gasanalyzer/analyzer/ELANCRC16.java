package pl.industrum.gasanalyzer.analyzer;

import java.util.Queue;

/**
 *  A class that can be used to compute the CRC-16 in ELAN Network of a data stream. 
 *  
 * @author duzydamian (Damian Karbowiak)
 * @see
 */
public class ELANCRC16
{
	public static boolean checkCRC16( Queue<Integer> data, int CRC )
	{
		if( countCRC16( data ) == CRC )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static int countCRC16(Queue<Integer> data)
	{		
		int k0 = 0xFFFF;
        for (int id : data)
        {        
        	char tempID = (char)id;
        	char counter;
        	Integer flag;
        	
        	k0 ^= tempID;
        	for (counter = 8; counter > 0; counter--) {
        		flag = k0 & 1;
        		k0 = k0 >> 1;
        		if( flag != 0x0000 ) 
        		{
        			k0 ^= 0xA001;
        		}
        	}                 
        }
        return k0;
	}
}