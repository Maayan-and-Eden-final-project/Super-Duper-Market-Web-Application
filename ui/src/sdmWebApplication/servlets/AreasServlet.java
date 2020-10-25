package sdmWebApplication.servlets;

import com.google.gson.Gson;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import systemInfoContainers.webContainers.AreaContainer;
import systemInfoContainers.webContainers.StoreIdAndNameContainer;
import users.SingleUser;
import users.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import static sdmWebApplication.constants.Constants.*;

public class AreasServlet extends HttpServlet {
    private final String SIGN_UP_URL = "../login/login.html";
    private final String SETTING_PAGE_URL = "settingPage.html";
    private final String AREA_MAIN_PAGE_URL = "../areaMainPage/areaMainPage.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String actionType = req.getParameter(ACTION_TYPE);
        try (PrintWriter out = resp.getWriter()) {
            String json = null;
            Gson gson = new Gson();
            UserManager userManager = ServletUtils.getUserManager(getServletContext());
           if(actionType.equals(STORES_FOR_ADD_ITEM)) {
               String areaNameFromSession = SessionUtils.getAreaName(req);
               String usernameFromSession = SessionUtils.getUsername(req);
               List<StoreIdAndNameContainer> areaOwnerStores = userManager.getAreaOwnerStores(areaNameFromSession,usernameFromSession);
               json = gson.toJson(areaOwnerStores);
           } else if(actionType.equals(GET_AREAS)) {
               List<AreaContainer> areasList = userManager.getAreas();
               json = gson.toJson(areasList);
           }

            out.println(json);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String areaName = req.getParameter(AREA_KEY);
        areaName = areaName.replaceAll("-"," ");
        String areaNameFromSession = SessionUtils.getAreaName(req);
        String usernameFromSession = SessionUtils.getUsername(req);

        if (usernameFromSession == null) {
            resp.sendRedirect(SIGN_UP_URL);
        }else if (usernameFromSession != null && areaNameFromSession == null) {
            if (areaName == null) {
                resp.sendRedirect(SETTING_PAGE_URL);
            }
        }

        req.getSession(true).setAttribute(AREANAME, areaName);
        resp.sendRedirect(AREA_MAIN_PAGE_URL);
        return;
    }
}
