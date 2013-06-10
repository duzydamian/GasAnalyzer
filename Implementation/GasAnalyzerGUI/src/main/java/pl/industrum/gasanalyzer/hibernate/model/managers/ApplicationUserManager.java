package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.DegreeDictionary;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.FunctionDictionary;
import pl.industrum.gasanalyzer.model.ApplicationUser;

public abstract class ApplicationUserManager
{
	public static Integer addApplicationUser( Integer functionID, Integer degreeID, String name, String surname )
	{
		ApplicationUser user = new ApplicationUser();
		user.setDegree( DegreeDictionary.get( degreeID ) );
		user.setFunction( FunctionDictionary.get( functionID ) ); 
		user.setName( name );
		user.setSurname( surname );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( user );
		session.getTransaction().commit();
		
		return user.getId();
	}
	
	public static void deleteApplicationUser( Integer userID )
	{
		ApplicationUser user = ApplicationUserManager.getApplicationUser( userID );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( user );
		session.getTransaction().commit();
	}
	
	public static Integer updateApplicationUser( Integer userID, Integer functionID, Integer degreeID, String name, String surname )
	{
		ApplicationUser user = ApplicationUserManager.getApplicationUser( userID );
		user.setDegree( DegreeDictionary.get( degreeID ) );
		user.setFunction( FunctionDictionary.get( functionID ) ); 
		user.setName( name );
		user.setSurname( surname );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update( user );
		session.getTransaction().commit();
		
		return user.getId();
		
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
