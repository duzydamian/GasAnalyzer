package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.Date;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.elan.communication.network.ELANMeasurementDevice;
import pl.industrum.gasanalyzer.elan.communication.network.ELANNetwork;
import pl.industrum.gasanalyzer.elan.frames.ELANRxBroadcastFrame;
import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;
import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.SurveySectionDictionary;
import pl.industrum.gasanalyzer.model.MeasurementSnapshot;

public abstract class MeasurementSnapshotManager
{
	public static Integer addMeasurementSnapshot( Integer surveyID, Date timestamp, ELANNetwork network, String comment )
	{
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
			
			for( ELANMeasurement elanMeasurement : ( ELANRxBroadcastFrame )elanMeasurementDevice.pollAndClear() )
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
}
