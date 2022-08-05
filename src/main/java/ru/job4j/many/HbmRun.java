package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            User one = User.of("Petr");
            User two = User.of("Andrei");
            session.save(one);
            session.save(two);

            Role admin = Role.of("ADMIN");
            /**
             * NO Best practice
             * admin.getUsers().add(one);
             * admin.getUsers().add(two);
             */
            admin.addUser(session.load(User.class, 1));
            admin.addUser(session.load(User.class, 2));
            session.save(admin);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}