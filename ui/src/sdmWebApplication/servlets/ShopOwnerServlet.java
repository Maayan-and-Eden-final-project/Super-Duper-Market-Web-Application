package sdmWebApplication.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
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
        String outMessage = null;
        try {
            if(action.equals("addStore")) {
                outMessage =  ServletUtils.getUserManager(getServletContext()).addNewStore(area,storeId, storeName,xLocation,yLocation,ppk,itemIdToItemPrice,usernameFromSession);
            }

            out.println(outMessage);
            out.flush();
        } catch (Exception e) {
            out.println(e.getMessage());
            out.flush();
        }

    }
}
