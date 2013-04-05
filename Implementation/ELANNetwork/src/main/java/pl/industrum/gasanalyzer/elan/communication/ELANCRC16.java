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
	
	char  crc_16;
	char crc_16_modbus;
	int counter;
	
    static char crc_tab16[];
    static char P_16 = 0xA001;
    
    public ELANCRC16() {
		super();
		counter=0;
		 crc_16         = 0;
		 crc_16_modbus  = 0xFFFF;
	     crc_tab16 = new char[256];
	     
	     int i, j;
	     char crc, c;

	     for (i=0; i<256; i++) {

	         crc = 0;
	         c   = (char) i;

	         for (j=0; j<8; j++) {

	             if ( ((crc ^ c) & 0x0001) == 1) crc = (char) (( crc >> 1 ) ^ P_16);
	             else                      crc =   (char) (crc >> 1);

	             c = (char) (c >> 1);
	         }

	         crc_tab16[i] = crc;
	     }
	}
    
	public void countCRC16(Queue<Integer> data)
	{		
        for (int id : data)
        {        
        	update_crc_16((char)id);            
        }
	}
	
	public void update_crc_16(char c)
	{
//	    char tmp, short_c;
	    char tmp_modbus, short_c_modbus;

//	    short_c = (char) (0x00ff & (char) c);
//
//	    tmp =  (char) (crc_16       ^ short_c);
//	    crc_16 = (char) ((crc_16 >> 8) ^ crc_tab16[ tmp & 0xff ]);
	    
	    short_c_modbus = (char) (0x00ff & (char) c);

	    tmp_modbus =  (char) (crc_16_modbus ^ short_c_modbus);
	    crc_16_modbus = (char) ((crc_16_modbus >> 8) ^ crc_tab16[ tmp_modbus & 0xff ]);
	    counter++;
	}

	public String getCrc_16() {
		return Integer.toHexString(crc_16) + "(" + Integer.toBinaryString(crc_16)+")";
	}

	public String getCrc_16_modbus() {
		return Integer.toHexString(crc_16_modbus) + "(" + Integer.toBinaryString(crc_16_modbus)+")";
	}
}
