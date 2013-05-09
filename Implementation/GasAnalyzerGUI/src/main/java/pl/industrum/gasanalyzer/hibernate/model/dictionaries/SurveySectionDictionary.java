package pl.industrum.gasanalyzer.hibernate.model.dictionaries;

import java.util.List;

import org.hibernate.Session;

import pl.industrum.gasanalyzer.hibernate.Hibernate;
import pl.industrum.gasanalyzer.model.SurveySection;

public abstract class SurveySectionDictionary
{
	public static Integer add( String name )
	{
		SurveySection section = new SurveySection();
		section.setName( name );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save( section );
		session.getTransaction().commit();
		//TODO reindexing table
		return section.getId();
	}
	
	public static void delete( Integer id )
	{
		SurveySection section = SurveySectionDictionary.get( id );
		
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete( section );
		//TODO reindexing table
		session.getTransaction().commit();
	}
	
	public static SurveySection get( Integer sectionID )
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SurveySection section = ( SurveySection ) session.createQuery( "from SurveySection where id='" + sectionID.toString() + "'" ).list().get( 0 );
		session.getTransaction().commit();
		return section;
	}
	
	@SuppressWarnings( "unchecked" )
	public static List<SurveySection> getAll()
	{
		Session session = Hibernate.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<SurveySection> sections = ( List<SurveySection> ) session.createQuery( "from SurveySection" ).list();
		session.getTransaction().commit();
		return sections;
	}
}
