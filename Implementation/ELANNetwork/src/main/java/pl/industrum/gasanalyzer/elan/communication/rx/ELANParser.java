package pl.industrum.gasanalyzer.elan.communication.rx;

import java.util.Queue;

public interface ELANParser
{
	public Queue<Integer> trimData( Queue<Integer> data );
}
