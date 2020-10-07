package sdmWebApplication.servlets;

import com.google.gson.Gson;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import systemInfoContainers.webContainers.AccountActionsContainer;
import users.SingleUser;
import users.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Float amount = Float.parseFloat(req.getParameter("amountKey"));
        String date = req.getParameter("dateKey");
        String usernameFromSession = SessionUtils.getUsername(req);
        ServletUtils.getUserManager(getServletContext()).addFundsToWallet(amount,date,usernameFromSession);
        out.println(amount.toString() + " was added to your wallet");
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            UserManager userManager = ServletUtils.getUserManager(getServletContext());
            AccountActionsContainer actions = userManager.getUserActions(SessionUtils.getUsername(req));
            String json = gson.toJson(actions);
            out.println(json);
            out.flush();
        }
    }
}
