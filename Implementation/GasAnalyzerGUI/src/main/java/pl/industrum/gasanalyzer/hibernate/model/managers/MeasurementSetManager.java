package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
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
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		try{
			//Create session and return survey collection
			session.beginTransaction();
			String queryString = "from MeasurementSet measurementSetObject where measurementSetObject.timestamp < '" + timestamp;
			queryString += "' and measurementSetObject.device.id = " + deviceID.toString();
			queryString += " and measurementSetObject.measurementSnapshot.survey.id = " + surveyID.toString();
			queryString += " order by measurementSetObject.timestamp desc";
			Query query = session.createQuery( queryString );
			query.setMaxResults( limit );
			List<MeasurementSet> sets = ( List<MeasurementSet> )query.list();
			session.getTransaction().commit();
			return sets;
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
