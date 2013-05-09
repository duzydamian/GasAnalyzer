package pl.industrum.gasanalyzer.hibernate.model.managers;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.elan.types.ELANMeasurement;
import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementDimensionDictionary;
import pl.industrum.gasanalyzer.hibernate.model.dictionaries.MeasurementVariableDictionary;
import pl.industrum.gasanalyzer.model.Measurement;

public class MeasurementManager
{
	public static Integer addMeasurement( Integer setID, ELANMeasurement elanMeasurement )
	{
		Measurement measurement = new Measurement();
		measurement.setMeasurementDimension( MeasurementDimensionDictionary.get( elanMeasurement.getDimension().ordinal() ) );
		measurement.setMeasurementVariable( MeasurementVariableDictionary.get( elanMeasurement.getMeasuredVariable().ordinal() ) );
		measurement.setValue( elanMeasurement.getValue() );
		measurement.setMeasurementSet( MeasurementSetManager.get( setID ) );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( measurement );
		session.getTransaction().commit();
		
		return measurement.getId();
	}
}
