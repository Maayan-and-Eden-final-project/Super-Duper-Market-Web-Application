package sdmWebApplication.servlets;

import com.google.gson.Gson;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import systemInfoContainers.ItemsContainer;
import systemInfoContainers.webContainers.AccountActionsContainer;
import systemInfoContainers.webContainers.SingleStoreContainer;
import users.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static sdmWebApplication.constants.Constants.*;


public class ItemsAndStoresServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionType = req.getParameter(ACTION_TYPE);
        String areanameFromSession = SessionUtils.getAreaName(req);
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            String json = null;
            if (actionType.equals(ITEMS)) {
                ItemsContainer items = ServletUtils.getUserManager(getServletContext()).getAreaItems(areanameFromSession);
                json = gson.toJson(items);

            } else if (actionType.equals(STORES)) {
                List<SingleStoreContainer> stores = ServletUtils.getUserManager(getServletContext()).getAreaStores(areanameFromSession);
                json = gson.toJson(stores);
            }

            out.println(json);
            out.flush();
        } catch (CloneNotSupportedException e) {

        }
    }
}
