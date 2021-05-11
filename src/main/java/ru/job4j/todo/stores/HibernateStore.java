package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;

public class HibernateStore implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static Store instOf() {
        return Lazy.INST;
    }

    private static final class Lazy {
        private static final Store INST = new HibernateStore();
    }

    @Override
    public void addItem(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
    }

    @Override
    public void noteItemAsDone(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item itemdb = session.get(Item.class, id);
            if (itemdb != null) {
                itemdb.setDone(true);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Item> getAllItems() {
        List items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Item").list();
            session.getTransaction().commit();
        }
        return items;
    }

    @Override
    public List<Item> getAllNotDoneItems() {
        List items;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            items = session.createQuery("from Item i where i.done = false").list();
            session.getTransaction().commit();
        }
        return items;
    }
}
