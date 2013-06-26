package pl.industrum.gasanalyzer.hibernate.model.dictionaries;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.MeasurementVariable;

public abstract class MeasurementVariableDictionary
{
	public static Integer add( String name )
	{
		MeasurementVariable variable = new MeasurementVariable();
		variable.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( variable );
		session.getTransaction().commit();
		//TODO reindexing table
		return variable.getId();
	}
	
	public static void add( Integer id, String name )
	{
		MeasurementVariable variable = new MeasurementVariable();
		variable.setId( id );
		variable.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( variable );
		session.getTransaction().commit();
		//TODO reindexing table
	}
	
	public static Integer update( Integer id, String name )
	{
		MeasurementVariable variable = MeasurementVariableDictionary.get( id );
		variable.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update( variable );
		session.getTransaction().commit();
		//TODO reindexing table
		return variable.getId();
	}
	
	public static void delete( Integer id )
	{
		MeasurementVariable variable = MeasurementVariableDictionary.get( id );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( variable );
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static void deleteAll()
	{
		List<MeasurementVariable> all = getAll();
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();		
		for( MeasurementVariable measurementVariable: all )
		{
			session.delete( measurementVariable );
		}
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static MeasurementVariable get( Integer variableID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		MeasurementVariable variable = ( MeasurementVariable ) session.createQuery( "from MeasurementVariable where id='" + variableID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return variable;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<MeasurementVariable> getAll()
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<MeasurementVariable> variables = ( List<MeasurementVariable> ) session.createQuery( "from MeasurementVariable" ).list();
		session.getTransaction().commit();
		return variables;
	}
}
