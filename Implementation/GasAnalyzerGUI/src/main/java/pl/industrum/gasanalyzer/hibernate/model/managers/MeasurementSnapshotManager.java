package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.elan.communication.network.ELANNetwork;
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;
import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.SurveySectionDictionary;
import pl.industrum.gasanalyzer.model.MeasurementSet;
import pl.industrum.gasanalyzer.model.MeasurementSnapshot;

public abstract class MeasurementSnapshotManager
{
	public static Integer addMeasurementSnapshot( Integer surveyID, Date timestamp, ELANNetwork network, String comment )
	{//FIXME sprawdzić czy wszystkie urządzenia mają jakieś pomiary i dopiero po tym tworzyć wszystkie obikety i ładować je do bazy
		MeasurementSnapshot snapshot = new MeasurementSnapshot();
		snapshot.setComment( comment );
		snapshot.setSurveySection( SurveySectionDictionary.get( SurveySectionDictionary.add( "bb" ) ) );
		snapshot.setSurvey( SurveyManager.getSurvey( surveyID ) );
		snapshot.setTimestamp( new Date() );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( snapshot );
		session.getTransaction().commit();
		
		for( ELANMeasurementDevice elanMeasurementDevice : network )
		{
			Integer addMeasurementSetId = MeasurementSetManager.addMeasurementSet( DeviceManager.getDevice( elanMeasurementDevice.getDeviceInformation().getDeviceIDInDatabase() ).getId(), snapshot.getId(), timestamp );
			
			for( ELANMeasurement elanMeasurement : ( ELANRxBroadcastFrame )elanMeasurementDevice.getSnapshot() )
			{
				MeasurementManager.addMeasurement( addMeasurementSetId, elanMeasurement );				
			}			
		}				
		
		return snapshot.getId();
	}
	
	public static void updateComment( Integer device )
	{
		//TODO complete mehod
	}
	
	public static void deleteMeasurementSnapshot( Integer deviceID )
	{
		//TODO complete mehod
	}
	
	
	public static MeasurementSnapshot getMeasurementSnapshot( Integer snapshotID )
	{
		//Create session and return survey
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		MeasurementSnapshot snapshot = ( MeasurementSnapshot  ) session.createQuery( "from MeasurementSnapshot where id='" + snapshotID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return snapshot;
	}

	@SuppressWarnings( "unchecked" )
	public static Integer getMeasurementSnapshotMeasuredVariableCount( Integer surveyID )
	{
		//Create session and return snapshots collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "from MeasurementSnapshot snapshot where";
		queryString += " snapshot.survey.id = " + surveyID.toString();
		Query query = session.createQuery( queryString );
		query.setMaxResults( 1 );
		List<MeasurementSnapshot> snapshots = ( List<MeasurementSnapshot> )query.list();
		session.getTransaction().commit();
		MeasurementSnapshot measurementSnapshot = snapshots.get( 0 );
		Integer size = 0;
		for( Object set: measurementSnapshot.getMeasurementSets() )
		{
			MeasurementSet measurementSet = ( MeasurementSet )set;
			size += measurementSet.getMeasurements().size()-1;
		}
		
		return size;
	}
	
	/**
	 * Get last measurement snapshot from survey with surveyID.
	 * @param surveyID
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	public static MeasurementSnapshot getLastMeasurementSnapshot( Integer surveyID )
	{
		//Create session and return snapshots collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String queryString = "from MeasurementSnapshot snapshot where";
		queryString += " snapshot.survey.id = " + surveyID.toString();
		Query query = session.createQuery( queryString );
		query.setMaxResults( 1 );
		List<MeasurementSnapshot> snapshots = ( List<MeasurementSnapshot> )query.list();
		session.getTransaction().commit();
		return snapshots.get( 0 );
	}
	
	/**
	 * Get all measurement snapshots between fromID and toID from survey with surveyID.
	 * @param surveyID
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	public static List<MeasurementSnapshot> getAllMeasurementSnapshots( Integer surveyID )
	{
		//Create session and return snapshots collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String query = "from MeasurementSnapshot snapshot where";
		query += " snapshot.survey.id = " + surveyID.toString();
		List<MeasurementSnapshot> snapshots = ( List<MeasurementSnapshot> )session.createQuery( query ).list();
		session.getTransaction().commit();
		return snapshots;
	}
}
