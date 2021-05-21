package ru.job4j.todo.stores;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {

    void addItem(Item item, String[] categories);

    void noteItemAsDone(int id);

    List<Item> getAllItems();

    List<Item> getAllNotDoneItems();

    List<Category> getAllCategories();
}
