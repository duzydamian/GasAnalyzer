package pl.industrum.gasanalyzer.hibernate.model.dictionaries;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.DeviceType;

public abstract class DeviceTypeDictionary
{
	public static Integer add( String type, byte[] document )
	{
		DeviceType deviceType = new DeviceType();
		deviceType.setType( type );
		deviceType.setDocument( document );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( deviceType );
		session.getTransaction().commit();
		//TODO reindexing table
		return deviceType.getId();
	}
	
	public static Integer add( String type )
	{
		DeviceType deviceType = new DeviceType();
		deviceType.setType( type );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( deviceType );
		session.getTransaction().commit();
		//TODO reindexing table
		return deviceType.getId();
	}
	
	public static Integer add( Integer id, String type, byte[] document )
	{
		DeviceType deviceType = new DeviceType();
		deviceType.setId( id );
		deviceType.setType( type );
		deviceType.setDocument( document );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( deviceType );
		session.getTransaction().commit();
		//TODO reindexing table
		return deviceType.getId();
	}
	
	public static Integer update( Integer id, String type, byte[] document )
	{
		DeviceType deviceType = new DeviceType();
		deviceType.setId( id );
		deviceType.setType( type );
		deviceType.setDocument( document );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate( deviceType );
		session.getTransaction().commit();
		//TODO reindexing table
		return deviceType.getId();
	}
	
	public static void addDocument( Integer id )
	{
		//TODO complete mehod
	}
	
	public static void delete( Integer id )
	{
		DeviceType type = DeviceTypeDictionary.get( id );
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( type );
		//TODO reindexing table
		session.getTransaction().commit();
	}

	public static void deleteAll()
	{
		List<DeviceType> all = getAll();
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		for( DeviceType deviceType: all )
		{
			session.delete( deviceType );
		}		
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static DeviceType get( Integer typeID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		DeviceType function = ( DeviceType ) session.createQuery( "from DeviceType where id='" + typeID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return function;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<DeviceType> getAll()
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<DeviceType> types = ( List<DeviceType> ) session.createQuery( "from DeviceType" ).list();
		session.getTransaction().commit();
		return types;
	}
}
