package pl.industrum.gasanalyzer.elan.types;

public class ELANCollectiveChannelState
{
	private boolean ERROR;
	private boolean MAINTENANCE_REQUEST;
	private boolean NOT_READY; //not measure
	private boolean MAINTENANCE_SWITCH_ON;
	private boolean FUNCTION_CHECK_ON;
	private boolean COMMAND_NOT_ACCEPTED;
	private boolean LIMIT_ALARM;
	
	public ELANCollectiveChannelState( Integer collectiveChannelState )
	{
		if( ( collectiveChannelState & 0x00000001 ) == 1 )
		{
			ERROR = true;
		}
		else
		{
			ERROR = false;
		}
		
		if( ( ( collectiveChannelState >> 1 ) & 0x00000001 ) == 1 )
		{
			MAINTENANCE_REQUEST = true;
		}
		else
		{
			MAINTENANCE_REQUEST = false;
		}
		
		if( ( ( collectiveChannelState >> 2 ) & 0x00000001 ) == 1 )
		{
			NOT_READY = true;
		}
		else
		{
			NOT_READY = false;
		}
		
		if( ( ( collectiveChannelState >> 3 ) & 0x00000001 ) == 1 )
		{
			MAINTENANCE_SWITCH_ON = true;
		}
		else
		{
			MAINTENANCE_SWITCH_ON = false;
		}
		
		if( ( ( collectiveChannelState >> 4 ) & 0x00000001 ) == 1 )
		{
			FUNCTION_CHECK_ON = true;
		}
		else
		{
			FUNCTION_CHECK_ON = false;
		}
		
		if( ( ( collectiveChannelState >> 5 ) & 0x00000001 ) == 1 )
		{
			COMMAND_NOT_ACCEPTED = true;
		}
		else
		{
			COMMAND_NOT_ACCEPTED = false;
		}
		
		if( ( ( collectiveChannelState >> 6 ) & 0x00000001 ) == 1 )
		{
			LIMIT_ALARM = true;
		}
		else
		{
			LIMIT_ALARM = false;
		}
	}
	
	public boolean getERROR() {
		return ERROR;
	}

	public boolean getMAINTENANCE_REQUEST() {
		return MAINTENANCE_REQUEST;
	}

	public boolean getNOT_READY() {
		return NOT_READY;
	}

	public boolean getMAINTENANCE_SWITCH_ON() {
		return MAINTENANCE_SWITCH_ON;
	}

	public boolean getFUNCTION_CHECK_ON() {
		return FUNCTION_CHECK_ON;
	}

	public boolean getCOMMAND_NOT_ACCEPTED() {
		return COMMAND_NOT_ACCEPTED;
	}

	public boolean getLIMIT_ALARM() {
		return LIMIT_ALARM;
	}
}
