package sdmWebApplication.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import systemInfoContainers.webContainers.SingleAreaOptionContainer;
import systemInfoContainers.webContainers.SingleDynamicStoreContainer;
import systemInfoContainers.webContainers.SingleStoreItemContainer;
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

public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionType = req.getParameter("actionType");
        resp.setContentType("application/json");
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String areaNameFromSession = SessionUtils.getAreaName(req);
        PrintWriter out = resp.getWriter();
        try {
            Gson gson = new Gson();
            String jsonResponse = null;
            if (actionType.equals("newOrder")) {
                SingleAreaOptionContainer newOrderOptions = userManager.getNewOrderOptions(areaNameFromSession);
                jsonResponse = gson.toJson(newOrderOptions);

            } else if(actionType.equals("getOrderItemsOption")) {
                String method = req.getParameter("methodKey");
                if(method.equals("Static Order")) {
                    String store = req.getParameter("storeKey");
                    Integer storeId = Integer.parseInt(store.split(":")[0].trim());
                    Integer xLocation = Integer.parseInt(req.getParameter("xLocationKey"));
                    Integer yLocation = Integer.parseInt(req.getParameter("yLocationKey"));

                    List<SingleStoreItemContainer> items = userManager.getAreaStoreItems(areaNameFromSession,storeId,xLocation,yLocation);
                    jsonResponse = gson.toJson(items);

                } else if(method.equals("Dynamic Order")) {
                    getServletContext().getRequestDispatcher("/pages/areaMainPage/itemsAndStores?actionType=items").forward(req, resp);
                }
            } else if(actionType.equals("getDiscounts")) {

                String store = req.getParameter("storeKey");
                String method = req.getParameter("methodKey");
                Integer storeId = Integer.parseInt(store.split(":")[0].trim());
                Integer xLocation = Integer.parseInt(req.getParameter("xLocationKey"));
                Integer yLocation = Integer.parseInt(req.getParameter("yLocationKey"));
                String mapString = req.getParameter("itemIdToAmountKey");
                Type itemsMapType = new TypeToken<Map<Integer, Float>>() {}.getType();
                Map<Integer,Float> itemIdToItemAmount = gson.fromJson(mapString,itemsMapType);


            } else if(actionType.equals("getMinimalCart")) {
                Integer xLocation = Integer.parseInt(req.getParameter("xLocationKey"));
                Integer yLocation = Integer.parseInt(req.getParameter("yLocationKey"));
                String mapString = req.getParameter("itemIdToAmountKey");
                Type itemsMapType = new TypeToken<Map<Integer, Float>>() {}.getType();
                Map<Integer,Float> itemIdToItemAmount = gson.fromJson(mapString,itemsMapType);

                List<SingleDynamicStoreContainer> minimalCart = userManager.getMinimalCart(areaNameFromSession,itemIdToItemAmount,xLocation,yLocation);
                jsonResponse = gson.toJson(minimalCart);

            }
            out.print(jsonResponse);
            out.flush();
        } catch (Exception e) {
            resp.setStatus(400);
            out.print(e.getMessage());
            out.flush();
        }
    }
}
