package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class RegistrationServlet extends HttpServlet {
    UserService userService = UserService.instance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp.getWriter().println(PageGenerator.instance().getPage("registerPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        String login = req.getParameter("email");
        String pass = req.getParameter("password");
        if (login == null || pass == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //resp.getWriter().println(PageGenerator.instance().getPage("registerPage.html", new HashMap<>()));
            return;
        }

        if (userService.addUser(new User(login, pass))||
                userService.isExistsThisUser(new User(login, pass))){
            resp.setStatus(HttpServletResponse.SC_OK);
            User profile = userService.getUser(login);
            
            //resp.getWriter().print(profile);
            //resp.getWriter().println(PageGenerator.instance().getPage("registerPage.html", new HashMap<>()));
        }
    }
}
