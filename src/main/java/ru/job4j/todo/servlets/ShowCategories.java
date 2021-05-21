package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.stores.HibernateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowCategories extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<Category> categories = HibernateStore.instOf().getAllCategories();
        String json = new Gson().toJson(categories);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
