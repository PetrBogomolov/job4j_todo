package ru.job4j.todo.stores;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

public interface Store {

    void addItem(Item item, String[] categories, String userId);

    User addUser(User user);

    User findUserByLogin(String login);

    void noteItemAsDone(int id);

    List<Item> getAllItems();

    List<Item> getAllNotDoneItems();

    List<Category> getAllCategories();
}
