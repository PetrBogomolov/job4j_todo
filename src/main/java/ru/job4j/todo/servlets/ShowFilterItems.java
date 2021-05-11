package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.stores.HibernateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowFilterItems extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<Item> items = HibernateStore.instOf().getAllNotDoneItems();
        String json = new Gson().toJson(items);
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
