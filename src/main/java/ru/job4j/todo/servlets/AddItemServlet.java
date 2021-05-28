package ru.job4j.todo.servlets;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.stores.HibernateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String[] categoriesId = req.getParameter("categoryIds").split(",");
        User user = (User) req.getSession().getAttribute("user");
        HibernateStore.instOf().addItem(new Item(req.getParameter("desc")), categoriesId, String.valueOf(user.getId()));
    }
}
