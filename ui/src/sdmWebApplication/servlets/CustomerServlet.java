package sdmWebApplication.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sdm.sdmElements.Offer;
import sdmWebApplication.utils.ServletUtils;
import sdmWebApplication.utils.SessionUtils;
import systemInfoContainers.OrderSummeryContainer;
import systemInfoContainers.ProgressOrderItem;
import systemInfoContainers.SingleDiscountContainer;
import systemInfoContainers.webContainers.*;
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
            }  else if(actionType.equals("getMinimalCart")) {
                Integer xLocation = Integer.parseInt(req.getParameter("xLocationKey"));
                Integer yLocation = Integer.parseInt(req.getParameter("yLocationKey"));
                String mapString = req.getParameter("itemIdToAmountKey");
                Type itemsMapType = new TypeToken<Map<Integer, Float>>() {}.getType();
                Map<Integer,Float> itemIdToItemAmount = gson.fromJson(mapString,itemsMapType);

                MinimalCartContainer minimalCart = userManager.getMinimalCart(areaNameFromSession,itemIdToItemAmount,xLocation,yLocation);
                jsonResponse = gson.toJson(minimalCart);

            } else if(actionType.equals("getOrdersHistory")) {
                String userNameFromSession = SessionUtils.getUsername(req);

                List<SingleCustomerOrderContainer> ordersHistory = userManager.getCustomerOrderHistory(userNameFromSession,areaNameFromSession);
                jsonResponse = gson.toJson(ordersHistory);

            }
            out.print(jsonResponse);
            out.flush();
        } catch (Exception e) {
            resp.setStatus(400);
            out.print(e.getMessage());
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionType = req.getParameter("actionType");
        resp.setContentType("application/json");
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String areaNameFromSession = SessionUtils.getAreaName(req);
        String usernameFromSession = SessionUtils.getUsername(req);
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        String jsonResponse = null;

        try {
            if (actionType.equals("getDiscounts")) {

                Integer storeId = null;
                String method = req.getParameter("methodKey");
                try {
                    String store = req.getParameter("storeKey");
                    storeId = Integer.parseInt(store.split(":")[0].trim());
                } catch (Exception e) {
                    storeId = -1;
                }
                String mapItemsString = req.getParameter("itemIdToAmountKey");
                Type itemsMapType = new TypeToken<Map<Integer, Float>>() {}.getType();
                Map<Integer, Float> itemIdToItemAmount = gson.fromJson(mapItemsString, itemsMapType);
                String minimalCartString = req.getParameter("minimalCartKey");
                Type minimalCartMapType = new TypeToken<Map<Integer, List<ProgressOrderItem>>>() {}.getType();
                Map<Integer, List<ProgressOrderItem>> minimalCart = gson.fromJson(minimalCartString, minimalCartMapType);

                List<SingleDiscountContainer> discounts = null;
                discounts = userManager.getDiscounts(areaNameFromSession, storeId, method, itemIdToItemAmount, minimalCart);

                jsonResponse = gson.toJson(discounts);

            } else if (actionType.equals("getOrderSummery")) {
                Integer storeId = null;
                try {
                    String store = req.getParameter("storeKey");
                    storeId = Integer.parseInt(store.split(":")[0].trim());
                } catch (Exception e) {
                    storeId = -1;
                }
                String method = req.getParameter("methodKey");
                Integer xLocation = Integer.parseInt(req.getParameter("xLocationKey"));
                Integer yLocation = Integer.parseInt(req.getParameter("yLocationKey"));

                String itemsMapString = req.getParameter("itemIdToAmountKey");
                Type itemsMapType = new TypeToken<Map<Integer, Float>>() {}.getType();
                Map<Integer, Float> itemIdToItemAmount = gson.fromJson(itemsMapString, itemsMapType);

                String discountsMapString = req.getParameter("discountListKey");
                Type discountsMapType = new TypeToken<Map<String, List<Offer>>>() {}.getType();
                Map<String, List<Offer>> discountNameToOffersList = gson.fromJson(discountsMapString, discountsMapType);
                Map<Integer, List<ProgressOrderItem>> minimalCart = null;
                if (method.equals("Dynamic Order")) {
                    String minimalCartString = req.getParameter("minimalCartKey");
                    Type minimalCartMapType = new TypeToken<Map<Integer, List<ProgressOrderItem>>>() {}.getType();
                    minimalCart = gson.fromJson(minimalCartString, minimalCartMapType);
                }

                OrderSummeryContainer orderSummery = userManager.getOrderSummery(areaNameFromSession, itemIdToItemAmount, discountNameToOffersList, xLocation, yLocation, method, storeId, minimalCart);
                jsonResponse = gson.toJson(orderSummery);

            } else if(actionType.equals("confirmOrder")) {
                String method = req.getParameter("methodKey");
                String date = req.getParameter("dateKey");
                String orderSummeryString = req.getParameter("orderSummeryKey");
                Type orderSummeryType = new TypeToken<OrderSummeryContainer>() {}.getType();
                OrderSummeryContainer orderSummery = gson.fromJson(orderSummeryString, orderSummeryType);

                userManager.addNewOrder(orderSummery,date,areaNameFromSession,usernameFromSession,method);
                FillFeedbackContainer feedbackStores = userManager.getFeedbackStores(orderSummery.getStoreIdToStoreInfo(), date);
                jsonResponse = gson.toJson(feedbackStores);
            } else if(actionType.equals("addStoreFeedback")) {
                Integer storeId = Integer.parseInt(req.getParameter("storeIdKey"));
                Integer rate = Integer.parseInt(req.getParameter("chosenFeedbackRateKey"));
                String review = req.getParameter("feedbackReviewKey");
                String date = req.getParameter("orderDateKey");

                userManager.addFeedbackToStore(areaNameFromSession,storeId,usernameFromSession,date,rate,review);

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
