package pl.industrum.gasanalyzer.hibernate.model.dictionaries;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.MeasurementDimension;

public abstract class MeasurementDimensionDictionary
{
	public static Integer add( String name )
	{
		MeasurementDimension dimension = new MeasurementDimension();
		dimension.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( dimension );
		session.getTransaction().commit();
		//TODO reindexing table
		return dimension.getId();
	}
	
	public static void add( Integer id, String name )
	{
		MeasurementDimension dimension = new MeasurementDimension();
		dimension.setId( id );
		dimension.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( dimension );
		session.getTransaction().commit();
		//TODO reindexing table
	}
	
	public static Integer update( Integer id, String name )
	{
		MeasurementDimension dimension = MeasurementDimensionDictionary.get( id );
		if( dimension == null )
		{
			return null;
		}
		else
		{
			dimension.setName( name );
			
			Session session = Hibernate.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update( dimension );
			session.getTransaction().commit();
			//TODO reindexing table
			return dimension.getId();
		}
	}
	
	public static void delete( Integer id )
	{
		MeasurementDimension dimension = MeasurementDimensionDictionary.get( id );
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( dimension );
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static void deleteAll()
	{		
		List<MeasurementDimension> all = getAll();
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		for( MeasurementDimension dimension: all )
		{
			session.delete( dimension );
		}
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static MeasurementDimension get( Integer dimensionID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		MeasurementDimension dimension = ( MeasurementDimension ) session.createQuery( "from MeasurementDimension where id='" + dimensionID.toString() + "'" ).list().get( 0 );
		try
		{
			session.getTransaction().commit();
			return dimension;
		}
		catch( Exception e )
		{
			session.getTransaction().rollback();
			return null;
		}
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<MeasurementDimension> getAll()
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<MeasurementDimension> dimensions = ( List<MeasurementDimension> ) session.createQuery( "from MeasurementDimension" ).list();
		session.getTransaction().commit();
		return dimensions;
	}
}
