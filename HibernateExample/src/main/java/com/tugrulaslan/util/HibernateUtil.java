package com.tugrulaslan.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by 90003327 on 7/10/2014.
 */
public class HibernateUtil {

    private static SessionFactory sessionAnnotationFactory;

    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionAnnotationFactory() {
        if (sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }
}
