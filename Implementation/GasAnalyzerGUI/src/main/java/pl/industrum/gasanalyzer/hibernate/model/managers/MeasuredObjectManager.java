package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.MeasuredObject;

public abstract class MeasuredObjectManager
{
	public static Integer addObject( String name, String description, Integer placeID )
	{
		MeasuredObject object = new MeasuredObject();
		object.setName( name );
		object.setDescription( description );
		object.setPlace( PlaceManager.getPlace( placeID ) );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( object );
		session.getTransaction().commit();
		
		return object.getId();
	}
	
	public static void deleteObject( Integer objectID )
	{
		MeasuredObject object = MeasuredObjectManager.getObject( objectID );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( object );
		session.getTransaction().commit();
	}
	
	public static void updateObject()
	{
		//TODO complete mehod
	}
	
	public static MeasuredObject getObject( Integer objectID )
	{
		//Create session and return survey
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		MeasuredObject object =  ( MeasuredObject  ) session.createQuery( "from MeasuredObject where id='" + objectID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return object;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<MeasuredObject> getAllObjects()
	{
		//Create session and return survey collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<MeasuredObject> objects = ( List<MeasuredObject>  ) session.createQuery( "from MeasuredObject" ).list();
		session.getTransaction().commit();
		return objects;
	}

	public static List<MeasuredObject> getObjectsByPlace( int placeID )
	{
		/*
		 * TODO
		 * FIXME
		 * as fast as possible add method to get object from place
		 */
		return null;
	}
}
