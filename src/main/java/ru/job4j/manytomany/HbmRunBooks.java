package ru.job4j.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRunBooks {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Author author1 = new Author("Aleksandr Pushkin");
            Author author2 = new Author("Michael Williams");
            Author author3 = new Author("Robin Young");
            Author author4 = new Author("Albert Camus");
            Author author5 = new Author("Antoine de Saint-Exupery");
            session.save(author1);
            session.save(author2);
            session.save(author3);
            session.save(author4);
            session.save(author5);
            Book book1 = new Book("Brave heart");
            Book book2 = new Book("Captain's daughter");
            Book book3 = new Book("Plague");
            Book book4 = new Book("Misunderstanding");
            Book book5 = new Book("The little Prince");
            Book book6 = new Book("planet of people");
            Book book7 = new Book("Southern postal");

            book1.getAuthorSet().add(author2);
            book1.getAuthorSet().add(author3);
            book2.getAuthorSet().add(author1);
            book3.getAuthorSet().add(author4);
            book4.getAuthorSet().add(author4);
            book5.getAuthorSet().add(author5);
            book6.getAuthorSet().add(author5);
            book7.getAuthorSet().add(author5);
            session.save(book1);
            session.save(book2);
            session.save(book3);
            session.save(book4);
            session.save(book5);
            session.save(book6);
            session.save(book7);

            Book book = session.get(Book.class, 7);
            session.remove(book);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
