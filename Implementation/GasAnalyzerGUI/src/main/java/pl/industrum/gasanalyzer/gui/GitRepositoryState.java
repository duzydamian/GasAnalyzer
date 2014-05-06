/**
 * 
 */
package pl.industrum.gasanalyzer.gui;

import java.io.IOException;
import java.util.Properties;

/**
 * @author duzydamian (Damian Karbowiak)
 *
 */
public class GitRepositoryState
{
	String branch;                  // =${git.branch}
	String describe;                // =${git.commit.id.describe}
	String commitId;                // =${git.commit.id}
	String buildUserName;           // =${git.build.user.name}
	String buildTime;               // =${git.build.time}
	String commitUserName;          // =${git.commit.user.name}
	String commitTime;              // =${git.commit.time}	

	/**
	 * 
	 */
	public GitRepositoryState()
	{
		try
		{
			Properties properties = new Properties();					
			properties.load(getClass().getResourceAsStream( "/pl/industrum/gasanalyzer/git.properties" ));		
	      
			this.branch = properties.get("git.branch").toString();
			this.describe = properties.get("git.commit.id.describe").toString();
			this.commitId = properties.get("git.commit.id").toString();
			this.buildUserName = properties.get("git.build.user.name").toString();
			this.buildTime = properties.get("git.build.time").toString();
			this.commitUserName = properties.get("git.commit.user.name").toString();
			this.commitTime = properties.get("git.commit.time").toString();
		}
		catch ( IOException e )
		{
			this.branch = "";
			this.describe = "";
			this.commitId = "";
			this.buildUserName = "";
			this.buildTime = "";
			this.commitUserName = "";
			this.commitTime = "";
		}
	}

	/**
	 * @return the branch
	 */
	public String getBranch()
	{
		return branch;
	}

	/**
	 * @return the describe
	 */
	public String getDescribe()
	{
		return describe;
	}

	/**
	 * @return the commitId
	 */
	public String getCommitId()
	{
		return commitId;
	}

	/**
	 * @return the buildUserName
	 */
	public String getBuildUserName()
	{
		return buildUserName;
	}

	/**
	 * @return the buildTime
	 */
	public String getBuildTime()
	{
		return buildTime;
	}

	/**
	 * @return the commitUserName
	 */
	public String getCommitUserName()
	{
		return commitUserName;
	}

	/**
	 * @return the commitTime
	 */
	public String getCommitTime()
	{
		return commitTime;
	}
}
