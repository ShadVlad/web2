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

public class LoginServlet extends HttpServlet {

    UserService userService = UserService.instance();
//    private final UserService userService;
//
//    public LoginServlet(UserService userService) {
//        this.userService = userService;
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        String login = req.getParameter("email");
        String pass = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        if (login == "" || pass == "") {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User profile = userService.getUser(login);


        if (profile == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(PageGenerator.instance().getPage("authPage.html", new HashMap<>()));
            return;
        }

        if (userService.isExistsThisUser(profile) && profile.getPassword().intern() == pass.intern()){
            resp.setStatus(HttpServletResponse.SC_OK);
            if (!userService.isAuthUser(profile)) userService.authUser(profile);
            //resp.getWriter().println(PageGenerator.instance().getPage("authPage.html", new HashMap<>()));
            //resp.getWriter().print(profile);
        } else  {
             resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //resp.getWriter().print("Unautorized");
        }

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        resp.getWriter().println(PageGenerator.instance().getPage("authPage.html", new HashMap<>()));
    }
}
