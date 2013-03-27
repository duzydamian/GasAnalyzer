/**
 * 
 */
package pl.industrum.gasanalyzer.elan.communication;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class ELANCommunication {

	ELANConnection elanConnection;
	
	public ELANCommunication() {
		super();
		elanConnection = new ELANConnection();
	}
}
