package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.Device;

public abstract class DeviceManager
{
	public static Integer addDevice( Integer functionID, Integer degreeID, String name, String surname )
	{
		Device device = new Device();
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( device );
		session.getTransaction().commit();
		
		return device.getId();
	}
	
	public static void deleteDevice( Integer deviceID )
	{
		Device device = DeviceManager.getDevice( deviceID );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( device );
		session.getTransaction().commit();
	}
	
	public static void updateDevice()
	{
		//TODO
	}
	
	public static Device getDevice( Integer deviceID )
	{
		//Create session and return survey
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Device device = ( Device  ) session.createQuery( "from Device where id='" + deviceID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return device;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<Device> getAllDevices()
	{
		//Create session and return survey collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Device> devices = ( List<Device> )session.createQuery( "from Device" ).list();
		session.getTransaction().commit();
		return devices;
	}
}
