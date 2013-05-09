package pl.industrum.gasanalyzer.hibernate.model.survey;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.ApplicationUser;

public abstract class ApplicationUserManager
{
	public static Integer addApplicationUser( Integer functionID, Integer degreeID, String name, String surname )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Create survey object
		ApplicationUser user = new ApplicationUser();
		user.setDegree( DegreeDictionary.get( degreeID ) );
		user.setFunction( FunctionDictionary.get( functionID ) ); 
		user.setName( name );
		user.setSurname( surname );
		//Save survey, commit transaction and return new ID
		session.save( user );
		session.getTransaction().commit();
		
		return user.getId();
	}
	
	public static void deleteApplicationUser( Integer userID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Delete survey and commit transaction
		session.delete( ApplicationUserManager.getApplicationUser( userID ) );
		session.getTransaction().commit();
	}
	
	public static void updateApplicationUser()
	{
		//TODO
	}
	
	public static ApplicationUser getApplicationUser( Integer userID )
	{
		//Create session and return survey
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ApplicationUser user = ( ApplicationUser  ) session.createQuery( "from ApplicationUser where id='" + userID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return user;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<ApplicationUser> getAllApplicationUsers()
	{
		//Create session and return survey collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<ApplicationUser> users = ( List<ApplicationUser> )session.createQuery( "from ApplicationUser" ).list();
		session.getTransaction().commit();
		return users;
	}
}
