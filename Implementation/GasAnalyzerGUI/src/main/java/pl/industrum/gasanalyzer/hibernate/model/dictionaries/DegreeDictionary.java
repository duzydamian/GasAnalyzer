package pl.industrum.gasanalyzer.hibernate.model.dictionaries;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.Degree;

public abstract class DegreeDictionary
{
	public static Integer add( String name )
	{
		Degree degree = new Degree();
		degree.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( degree );
		session.getTransaction().commit();
		//TODO reindexing table
		return degree.getId();
	}
	
	public static void delete( Integer id )
	{
		Degree degree = DegreeDictionary.get( id );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( degree );
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static Integer update(  Integer id, String name )
	{
		try
		{
			Degree degree = DegreeDictionary.get( id );
			degree.setName( name );
	
			Session session = Hibernate.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update( degree );
			session.getTransaction().commit();
			//TODO reindexing table
			return degree.getId();
		}
		catch( Exception e )
		{
			return null;
		}
	}
	
	public static Degree get( Integer functionID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Degree degree = ( Degree ) session.createQuery( "from Degree where id='" + functionID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return degree;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<Degree> getAll()
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Degree> functions = ( List<Degree> ) session.createQuery( "from Degree" ).list();
		session.getTransaction().commit();
		return functions;
	}
}
