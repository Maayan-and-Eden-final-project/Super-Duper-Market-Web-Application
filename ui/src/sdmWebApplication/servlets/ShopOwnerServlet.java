package sdmWebApplication.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import sdmWebApplication.constants.Constants;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import users.UserManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.lang.Object;


public class ShopOwnerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String area = (req.getParameter("areaKey"));
        String storeName = (req.getParameter("storeNameKey"));
        Integer storeId =  Integer.parseInt(req.getParameter("storeIdKey"));
        Integer xLocation = Integer.parseInt(req.getParameter("xLocationKey"));
        Integer yLocation = Integer.parseInt(req.getParameter("yLocationKey"));
        Integer ppk = Integer.parseInt(req.getParameter("ppkKey"));
        String action = (req.getParameter("actionKey"));

        Gson gson = new Gson();
        String mapString = req.getParameter("itemsListKey");
        Type itemsMapType = new TypeToken<Map<Integer, Integer>>() {}.getType();
        Map<Integer,Integer> itemIdToItemPrice = gson.fromJson(mapString,itemsMapType);

        String usernameFromSession = SessionUtils.getUsername(req);
        try {
            if(action.equals("addStore")) {
                ServletUtils.getUserManager(getServletContext()).addNewStore(area,storeId, storeName,xLocation,yLocation,ppk,itemIdToItemPrice,usernameFromSession);
            }

            out.println("Store Added Successfully");
            out.flush();

        } catch (Exception e) {
            resp.setStatus(400);
            out.print(e.getMessage());
            out.flush();


        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(req);

        resp.setContentType("application/json");
        UserManager userManager = ServletUtils.getUserManager(getServletContext());

        if (usernameFromSession == null) {
            resp.sendRedirect(req.getContextPath() + "/index.html");
        }

        int messageVersion = ServletUtils.getIntParameter(req, Constants.MESSAGE_VERSION_PARAMETER);
        if (messageVersion == Constants.INT_PARAMETER_ERROR) {
            return;
        }

        int userMessageVersion = 0;
        List<String> messages;
        synchronized (getServletContext()) {
            userMessageVersion = userManager.getUserMessageVersion(usernameFromSession);
            messages = userManager.getUserMessages(usernameFromSession,userMessageVersion);
        }
        MessagesAndVersion messagesAndVersion = new MessagesAndVersion(messages, userMessageVersion);
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(messagesAndVersion);

        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }
    private static class MessagesAndVersion {

        final private List<String> entries;
        final private int version;

        public MessagesAndVersion(List<String> entries, int version) {
            this.entries = entries;
            this.version = version;
        }
    }
}
