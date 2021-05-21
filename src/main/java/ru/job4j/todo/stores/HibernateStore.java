package ru.job4j.todo.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.ArrayList;
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
    public void addItem(Item item, String[] categories) {
        transaction(session -> {
            for (String id : categories) {
                item.addCategory(session.load(Category.class, Integer.parseInt(id)));
            }
            session.save(item);
            return null;
        });
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
        return transaction(session -> {
            List<Item> items;
            items = session.createQuery(
                    "select distinct i from Item i join fetch i.categories"
            ).list();
            return items;
        });
    }

    @Override
    public List<Item> getAllNotDoneItems() {
        return  transaction(session -> {
            List<Item> items;
            items = session.createQuery(
                    "select distinct i from Item i join fetch i.categories where i.done = false"
            ).list();
            return items;
        });
    }

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) transaction(session -> session.createQuery("from Category").list());
    }

    private <T> T transaction(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
