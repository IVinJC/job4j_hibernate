package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRunCars {
    public static void main(String[] args) {
        List<Mark> carMarks = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Mark carMark = new Mark("BMW");
            Model carModel1 = new Model("1 series", carMark);
            Model carModel2 = new Model("6 series GT", carMark);
            Model carModel3 = new Model("2 series active tourer", carMark);
            Model carModel4 = new Model("4 series cabrio", carMark);
            Model carModel5 = new Model("M2", carMark);
            session.save(carModel1);
            session.save(carModel2);
            session.save(carModel3);
            session.save(carModel4);
            session.save(carModel5);

            carMark.getModelList().add(carModel1);
            carMark.getModelList().add(carModel2);
            carMark.getModelList().add(carModel3);
            carMark.getModelList().add(carModel4);
            carMark.getModelList().add(carModel5);
            session.save(carMark);

            carMarks = session.createQuery("from Mark").list();
            for (Mark mark : carMarks) {
                for (Model model : carMark.getModelList()) {
                    System.out.println(model);
                }
            }

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
