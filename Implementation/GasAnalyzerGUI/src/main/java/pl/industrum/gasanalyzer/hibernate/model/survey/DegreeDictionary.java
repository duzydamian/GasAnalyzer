package pl.industrum.gasanalyzer.hibernate.model.survey;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.Degree;

public abstract class DegreeDictionary
{
	public static Integer add( String name )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Degree degree = new Degree();
		degree.setName( name );
		session.save( degree );
		session.getTransaction().commit();
		//TODO reindexing table
		return degree.getId();
	}
	
	public static void delete( Integer id )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( DegreeDictionary.get( id ) );
		//TODO reindexing table
		session.getTransaction().commit();
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
