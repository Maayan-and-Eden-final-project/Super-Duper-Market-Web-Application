package sdmWebApplication.servlets;

import com.google.gson.Gson;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import systemInfoContainers.webContainers.SingleUserContainer;
import users.SingleUser;
import users.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String userName = SessionUtils.getUsername(req);

        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            UserManager userManager = ServletUtils.getUserManager(getServletContext());
            Map<String, SingleUserContainer> usersList = userManager.getUsersInfo(userName);
            String json = gson.toJson(usersList);
            out.println(json);
            out.flush();
        }
    }
}
