package pl.industrum.gasanalyzer.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Hibernate
{
	private static final SessionFactory sessionFactory;
	 
	static
	{
		try
		{
			Configuration config = new Configuration().configure();
			
			ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
			srBuilder.applySettings( config.getProperties() );
			
			ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
			
			sessionFactory = config.buildSessionFactory( serviceRegistry );
		} catch ( Throwable ex )
		{
			System.err.println( "Initial SessionFactory creation failed." + ex );
			throw new ExceptionInInitializerError(ex);
		}
	}
	 
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}
