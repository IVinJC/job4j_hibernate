package ru.job4j.hql;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        Candidate rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Vacancy vacancy1 = new Vacancy("Java Junior Developer");
            Vacancy vacancy2 = new Vacancy("Java Middle Developer");
            Vacancy vacancy3 = new Vacancy("Java Senior Developer");
            session.save(vacancy1);
            session.save(vacancy2);
            session.save(vacancy3);

            VacancyBase vacancyBase = new VacancyBase("Base of Vacancies");
            vacancyBase.getVacancies().add(vacancy1);
            vacancyBase.getVacancies().add(vacancy2);
            vacancyBase.getVacancies().add(vacancy3);
            session.save(vacancyBase);

            Candidate candidateFirst = new Candidate("Jack", 2, 100000.0D, vacancyBase);
            session.save(candidateFirst);

            Query sessionQuery = session.createQuery("from Candidate");
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

            /**session.createQuery("delete Candidate k where k.id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();*/

            rsl = session.createQuery("select distinct c from Candidate c "
                            + "join fetch c.vacancyBase v "
                            + "join fetch v.vacancies where c.id = :fId", Candidate.class)
                    .setParameter("fId", 1)
                    .uniqueResult();

            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }
}
