package pl.industrum.gasanalyzer.hibernate.model.survey;

import java.util.List;

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
		session.save( place );
		session.getTransaction().commit();
		
		return place.getId();
	}
	
	public static void deletePlace( Integer placeID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Delete place and commit transaction
		session.delete( PlaceManager.getPlace( placeID ) );
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
		session.beginTransaction();
		Place place = ( Place ) session.createQuery( "from Place where id='" + placeID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return place;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<Place> getAllPlaces()
	{
		//Create session and return place collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Place> places = ( List<Place>  ) session.createQuery( "from Place" ).list();
		session.getTransaction().commit();
		return places;
	}
}
