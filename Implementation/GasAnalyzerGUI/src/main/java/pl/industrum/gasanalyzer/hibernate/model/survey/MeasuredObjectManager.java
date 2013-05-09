package pl.industrum.gasanalyzer.hibernate.model.survey;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.MeasuredObject;

public abstract class MeasuredObjectManager
{
	public static Integer addObject( String name, String description, Integer placeID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Create survey object
		MeasuredObject object = new MeasuredObject();
		object.setName( name );
		object.setDescription( description );
		object.setPlace( PlaceManager.getPlace( placeID ) );
		//Save survey and commit transaction
		session.save( object );
		session.getTransaction().commit();
		
		return object.getId();
	}
	
	public static void deleteObject( Integer objectID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Delete survey and commit transaction
		session.delete( MeasuredObjectManager.getObject( objectID ) );
		session.getTransaction().commit();
	}
	
	public static void updateObject()
	{
		//TODO
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
}
