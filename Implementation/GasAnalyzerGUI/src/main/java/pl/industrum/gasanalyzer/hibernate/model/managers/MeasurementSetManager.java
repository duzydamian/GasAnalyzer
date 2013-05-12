package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.Date;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.MeasurementSet;

public abstract class MeasurementSetManager
{
	public static Integer addMeasurementSet( Integer deviceID, Integer snapshotID, Date timestamp )
	{
		MeasurementSet set = new MeasurementSet();
		set.setDevice( DeviceManager.getDevice( deviceID ) );
		set.setMeasurementSnapshot( MeasurementSnapshotManager.getMeasurementSnapshot( snapshotID ) );
		set.setTimestamp( timestamp );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( set );
		session.getTransaction().commit();
		
		return set.getId();
	}
	
	public static MeasurementSet get( Integer setID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		MeasurementSet set = ( MeasurementSet  ) session.createQuery( "from MeasurementSet where id='" + setID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return set;
	}
	
	//TODO getAllMeasurementSetFromDevicePerSurvey - to fill history tab item
}
