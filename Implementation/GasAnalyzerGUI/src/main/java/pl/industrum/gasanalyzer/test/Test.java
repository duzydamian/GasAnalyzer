/**
 * 
 */
package pl.industrum.gasanalyzer.test;

/**
 * @author duzydamian (Damian Karbowiak)
 * 
 */
public class Test
{

	private String name;
	private boolean passed;

	/**
	 * 
	 */
	public Test()
	{
		setPassed( false );
	}

	/**
	 * 
	 */
	public Test( String testName )
	{
		setName( testName );
		setPassed( false );
	}

	public void test()
	{

	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public boolean isPassed()
	{
		return passed;
	}

	public void setPassed( boolean passed )
	{
		this.passed = passed;
	}

}
