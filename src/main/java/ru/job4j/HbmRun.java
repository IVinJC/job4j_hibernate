package ru.job4j;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Candidate candidateFirst = new Candidate("Jack", 2, 100000.0D);
            Candidate candidateSecond = new Candidate("Pall", 3, 150000.0D);
            Candidate candidateThird = new Candidate("Aleksander", 8, 300000.0D);

            session.save(candidateFirst);
            session.save(candidateSecond);
            session.save(candidateThird);

            Query sessionQuery = session.createQuery("from ru.job4j.Candidate");
            for (Object o : sessionQuery.list()) {
                System.out.println(o);
            }

            Object result = session.createQuery("from Candidate k where k.id = :fId")
                    .setParameter("fId", 2)
                    .uniqueResult();
            System.out.println(result);

            Query query = session.createQuery("from Candidate k where k.name = :fName")
                    .setParameter("fName", "Pall");
            System.out.println(query.getResultList());

            session.createQuery("update Candidate k set k.name = :fName,"
                    + " k.experience = :fExp, k.salary = :fSal where k.id = :fId")
                    .setParameter("fName", "Vadim")
                    .setParameter("fExp", 1)
                    .setParameter("fSal", 800_000.0D)
                    .setParameter("fId", 3).executeUpdate();

            session.createQuery("delete Candidate k where k.id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
