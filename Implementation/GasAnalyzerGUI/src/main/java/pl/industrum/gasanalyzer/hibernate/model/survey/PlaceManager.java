package pl.industrum.gasanalyzer.hibernate.model.survey;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.Place;

public abstract class PlaceManager
{
	public static Integer addPlace( String name, String city, String postCode, String address )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Create place object
		Place place = new Place();
		place.setName( name );
		place.setCity( city );
		place.setPostCode( postCode );
		place.setAddress( address );
		//Save place and commit transaction
		Integer id = ( ( Place ) session.save( place ) ).getId();
		session.getTransaction().commit();
		
		return id;
	}
	
	public static void deletePlace( Integer placeID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Delete place and commit transaction
		session.delete( ( Place  ) session.createQuery( "from survey where id='" + placeID.toString() + "'" ).list().get( 0 ) );
		session.getTransaction().commit();
	}
	
	public static void updatePlace()
	{
		//TODO
	}
	
	public static Place getPlace( Integer placeID )
	{
		//Create session and return place
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		return ( Place  ) session.createQuery( "from place where id='" + placeID.toString() + "'" ).list().get( 0 );
	}
	
	@SuppressWarnings( "unchecked" )
	public static Iterable<Place> getAllPlaces()
	{
		//Create session and return place collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		return ( Iterable<Place>  ) session.createQuery( "from place" ).list().iterator();
	}
}
