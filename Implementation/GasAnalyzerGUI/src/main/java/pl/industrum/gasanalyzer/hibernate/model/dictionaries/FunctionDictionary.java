package pl.industrum.gasanalyzer.hibernate.model.dictionaries;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.Function;

public abstract class FunctionDictionary
{
	public static Integer add( String name )
	{
		Function function = new Function();
		function.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( function );
		session.getTransaction().commit();
		//TODO reindexing table
		return function.getId();
	}
	
	public static void delete( Integer id )
	{
		Function function = FunctionDictionary.get( id );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( function );
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static Integer update( Integer id, String name )
	{
		Function function = FunctionDictionary.get( id );
		if( function == null )
		{
			return null;
		}
		else
		{
			function.setName( name );
	
			Session session = Hibernate.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update( function );
			session.getTransaction().commit();
			//TODO reindexing table
			return function.getId();
		}
	}
	
	public static Function get( Integer functionID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try
		{
			Function function = ( Function ) session.createQuery( "from Function where id='" + functionID.toString() + "'" ).list().get( 0 );
			session.getTransaction().commit();
			return function;
		}
		catch( Exception e )
		{
			session.getTransaction().rollback();
			return null;
		}
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<Function> getAll()
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Function> functions = ( List<Function> ) session.createQuery( "from Function" ).list();
		session.getTransaction().commit();
		return functions;
	}
}
