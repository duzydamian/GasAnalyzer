package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
	
	/**
	 * Get all measurement sets between fromID and toID from specific device and survey.
	 * @param timestamp
	 * @param surveyID
	 * @param deviceID
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	public static List<MeasurementSet> getAllMeasurementSets( Date timestamp, Integer surveyID, Integer deviceID, Integer limit )
	{
		//Create session and return survey collection
		Timestamp timestamp2 = new java.sql.Timestamp( timestamp.getTime() );
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
//		String query = "from MeasurementSet set where set.timestamp < '" + timestamp2;
//		query += "' and set.device.id = " + deviceID.toString();
//		query += " and set.measurementSnapshot.survey.id = " + surveyID.toString();
//		query += " LIMIT " + limit.toString();
		String query = "from MeasurementSet where timestamp < '" + timestamp2;
		query += "' and device.id = " + deviceID.toString();
		query += " and measurementSnapshot.survey.id = " + surveyID.toString();
		//query += " LIMIT " + limit.toString();
		List<MeasurementSet> sets = ( List<MeasurementSet> )session.createQuery( query ).list();
		session.getTransaction().commit();
		return sets;
	}
}
