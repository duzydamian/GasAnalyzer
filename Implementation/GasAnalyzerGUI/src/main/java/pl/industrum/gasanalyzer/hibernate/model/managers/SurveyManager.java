package pl.industrum.gasanalyzer.hibernate.model.managers;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.Survey;

public abstract class SurveyManager
{
	public static Integer addSurvey( String name, String load, String specialConditions, String comment, Integer objectID, Integer userID, Date date )
	{
		Survey survey = new Survey();
		survey.setTimestamp( date );
		survey.setName( name );
		survey.setLoad( load );
		survey.setSpecialConditions( specialConditions );
		survey.setComment( comment );
		survey.setApplicationUser( ApplicationUserManager.getApplicationUser( userID ) );
		survey.setObject( MeasuredObjectManager.getObject( objectID ) );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( survey );
		session.getTransaction().commit();
		
		return survey.getId();
	}
	
	public static void deleteSurvey( Integer surveyID )
	{
		Survey survey = SurveyManager.getSurvey( surveyID );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( survey );
		session.getTransaction().commit();
	}
	
	public static void updateSurvey( Integer surveyID, String name, String load, String specialConditions, String comment, Integer objectID, Integer userID, Date date )
	{
		Survey survey = SurveyManager.getSurvey( surveyID );
		survey.setName( name );
		survey.setLoad( load );
		survey.setSpecialConditions( specialConditions );
		survey.setComment( comment );
		survey.setObject( MeasuredObjectManager.getObject( objectID ) );
		survey.setApplicationUser( ApplicationUserManager.getApplicationUser( userID ) );
		survey.setTimestamp( date );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update( survey );
		session.getTransaction().commit();
	}
	
	public static Survey getSurvey( Integer surveyID )
	{
		//Create session and return survey
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Survey survey = ( Survey  ) session.createQuery( "from Survey where id='" + surveyID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return survey;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<Survey> getAllSurveys()
	{
		//Create session and return survey collection
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Survey> surveys = ( List<Survey> )session.createQuery( "from Survey" ).list();
		session.getTransaction().commit();
		return surveys;
	}
}
