/**
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 30 2e 39 39 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   41 3a 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 30 2e 39 39 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   41 3a 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 31 2e 30 30 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   6c c2 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 30 2e 39 39 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   41 3a 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 31 2e 30 30 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   6c c2 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 31 2e 30 30 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   6c c2 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 31 2e 30 30 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   6c c2 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 30 2e 39 39 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   41 3a 
10 1 f0 20 18 4 6b 2 30 0 2 0 2 0 30 0 2 0 7 0 30 2e 30 30 0 a 0 3 0 32 31 2e 30 30 0 a 0 c 0 39 38 32 0 23 0 64 0 10 3   6c c2
*/
package pl.industrum.gasanalyzer.elan.communication;

import java.util.Queue;

/**
 *  A class that can be used to compute the CRC-16 in ELAN Network of a data stream. 
 *  
 * @author duzydamian (Damian Karbowiak)
 * @see
 */
public class ELANCRC16 {
	
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
