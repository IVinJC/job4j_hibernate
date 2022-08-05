package ru.job4j.tasktomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRunCars {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarModel carModel1 = new CarModel("1 series");
            CarModel carModel2 = new CarModel("6 series GT");
            CarModel carModel3 = new CarModel("2 series active tourer");
            CarModel carModel4 = new CarModel("4 series cabrio");
            CarModel carModel5 = new CarModel("M2");
            session.save(carModel1);
            session.save(carModel2);
            session.save(carModel3);
            session.save(carModel4);
            session.save(carModel5);

            Car carBmw = new Car("BMW");
            for (int i = 1; i <= 5; i++) {
                carBmw.addModel(session.load(CarModel.class, i));
            }
            session.save(carBmw);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
