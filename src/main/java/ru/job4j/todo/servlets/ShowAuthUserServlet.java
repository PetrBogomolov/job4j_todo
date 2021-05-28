package ru.job4j.todo.servlets;

import com.google.gson.Gson;
import ru.job4j.todo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAuthUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String json = new Gson().toJson(new User("Anonymous", "", ""));
        if (user != null) {
            json = new Gson().toJson(user);
        }
        resp.setContentType("json");
        resp.getWriter().write(json);
    }
}
