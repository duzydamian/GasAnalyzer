package pl.industrum.gasanalyzer.hibernate.model.survey;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.ApplicationUser;
import pl.industrum.gasanalyzer.model.MeasuredObject;
import pl.industrum.gasanalyzer.model.Survey;

public abstract class SurveyManager
{
	public static Integer addSurvey( String name, String load, String specialConditions, String comment, Integer objectID, Integer userID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Create survey object
		Survey survey = new Survey();
		survey.setTimestamp( new Date() );
		survey.setName( name );
		survey.setLoad( load );
		survey.setSpecialConditions( specialConditions );
		survey.setComment( comment );
		survey.setApplicationUser( ( ApplicationUser ) session.createQuery( "from application_user where id='" + userID.toString() + "'" ).list().get( 0 ) );
		survey.setObject( ( MeasuredObject ) session.createQuery( "from object where id='" + objectID.toString() + "'" ).list().get( 0 ) );
		//Save survey, commit transaction and return new ID
		Integer id = ( ( Survey ) session.save( survey ) ).getId();
		session.getTransaction().commit();
		
		return id;
	}
	
	public static void deleteSurvey( Integer surveyID )
	{
		//Create session and begin transaction
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Delete survey and commit transaction
		session.delete( ( Survey  ) session.createQuery( "from survey where id='" + surveyID.toString() + "'" ).list().get( 0 ) );
		session.getTransaction().commit();
	}
	
	public static void updateSurvey()
	{
		//TODO
	}
	
	public static Survey getSurvey( Integer surveyID )
	{
		//Create session and return survey
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Survey survey = ( Survey  ) session.createQuery( "from survey where id='" + surveyID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return survey;
	}
	
	@SuppressWarnings( "unchecked" )
	public static Iterator<Survey> getAllSurveys()
	{
		//Create session and return survey collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Survey> surveys = ( List<Survey> )session.createQuery( "from survey" ).list();
		return surveys.iterator();
	}
}
