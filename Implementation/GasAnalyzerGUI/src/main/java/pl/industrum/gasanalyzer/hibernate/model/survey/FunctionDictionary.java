package pl.industrum.gasanalyzer.hibernate.model.survey;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.Function;

public abstract class FunctionDictionary
{
	public static Integer add( String name )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Function function = new Function();
		function.setName( name );
		session.save( function );
		session.getTransaction().commit();
		//TODO reindexing table
		return function.getId();
	}
	
	public static void delete( Integer id )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( FunctionDictionary.get( id ) );
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static Function get( Integer functionID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Function function = ( Function ) session.createQuery( "from Function where id='" + functionID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return function;
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
