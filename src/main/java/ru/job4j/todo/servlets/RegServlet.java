package ru.job4j.todo.servlets;

import ru.job4j.todo.model.User;
import ru.job4j.todo.stores.HibernateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User save = HibernateStore.instOf().findUserByLogin(login);
        if (save != null) {
            resp.sendRedirect(req.getContextPath() + "/reg.do");
        } else {
            HibernateStore.instOf().addUser(new User(name, login, password));
            resp.sendRedirect(req.getContextPath() + "/auth.do");
        }
    }
}
