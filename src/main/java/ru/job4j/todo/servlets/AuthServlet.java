package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import ru.job4j.todo.model.User;
import ru.job4j.todo.stores.HibernateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("auth.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = HibernateStore.instOf().findUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else if (user != null && !user.getPassword().equals(password)) {
            req.setAttribute("error", "Неверный пароль");
            req.getRequestDispatcher("/auth.do").forward(req, resp);
        } else {
            req.setAttribute("error", "Пользователя не существует");
            req.getRequestDispatcher("/reg.html").forward(req, resp);
        }
    }
}
