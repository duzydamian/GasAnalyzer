package pl.industrum.gasanalyzer.hibernate.model.survey;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.MeasuredObject;
import pl.industrum.gasanalyzer.model.Place;

public abstract class MeasuredObjectManager
{
	public static void addObject( String name, String description, Integer placeID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Create survey object
		MeasuredObject object = new MeasuredObject();
		object.setName( name );
		object.setDescription( description );
		object.setPlace( ( Place ) session.createQuery( "from place where id='" + placeID.toString() + "'" ).list().get( 0 ) );
		//Save survey and commit transaction
		session.save( object );
		session.getTransaction().commit();
	}
	
	public static void deleteObject( Integer objectID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Delete survey and commit transaction
		session.delete( ( MeasuredObject  ) session.createQuery( "from survey where id='" + objectID.toString() + "'" ).list().get( 0 ) );
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
		return ( MeasuredObject  ) session.createQuery( "from object where id='" + objectID.toString() + "'" ).list().get( 0 );
	}
	
	@SuppressWarnings( "unchecked" )
	public static Iterable<MeasuredObject> getAllObjects()
	{
		//Create session and return survey collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		return ( Iterable<MeasuredObject>  ) session.createQuery( "from object" ).list().iterator();
	}
}
