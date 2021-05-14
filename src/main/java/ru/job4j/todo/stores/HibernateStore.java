package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

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
        transaction(session -> session.save(item));
    }

    @Override
    public void noteItemAsDone(int id) {
        transaction(session -> {
            Item itemdb = session.get(Item.class, id);
            if (itemdb != null) {
                itemdb.setDone(true);
            }
            return null;
        });
    }

    @Override
    public List<Item> getAllItems() {
        return (List<Item>) transaction(session -> session.createQuery("from Item").list());
    }

    @Override
    public List<Item> getAllNotDoneItems() {
        return (List<Item>) transaction(session -> session.createQuery("from Item i where i.done = false").list());
    }

    private <T> T transaction(Function<Session, T> command) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            T result = command.apply(session);
            session.getTransaction().commit();
            return result;
        }
    }
}
