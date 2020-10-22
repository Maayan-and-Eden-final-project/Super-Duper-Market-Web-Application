package systemEngine;

import exceptions.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.VBox;

import javafx.util.Pair;
import sdm.enums.PurchaseCategory;
import sdm.sdmElements.*;
import systemInfoContainers.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DesktopEngine extends Connector implements Cloneable {
    private Map<Integer, Customer> idToCustomer;
    private int dynamicOrderId = 0;

   /* public DesktopEngine(WelcomeController welcomeController) {
        this.welcomeController = welcomeController;
        this.idToCustomer = new HashMap<>();

    }*/

    public Integer getDynamicOrderId() {
        return dynamicOrderId;
    }

   /* public void calcMinimalCart(UiAdapter uiAdapter, ProgressDynamicOrderContainer progressDynamicOrderContainer, VBox bodyComponent) {
        CalcMinimalCartTask calcMinimalCartTask = new CalcMinimalCartTask(this, progressDynamicOrderContainer  , uiAdapter);
        welcomeController.initMinimalCart(calcMinimalCartTask, bodyComponent);
        new Thread(calcMinimalCartTask).start();
    }

    public void loadXmlFile(UiAdapter uiAdapter, String path, SimpleBooleanProperty isFileSelected, SimpleBooleanProperty isValidFile) {
        LoadXmlTask loadXmlTask = new LoadXmlTask(this, path,isFileSelected, this.welcomeController, uiAdapter, isValidFile);
        welcomeController.bindTaskToUIComponents(loadXmlTask, welcomeController.getProgressBar(), welcomeController.getProgressLabel());
        new Thread(loadXmlTask).start();
    }*/

    public Double calcShipmentToCustomer(Integer customerId, Integer storeId) {
        double shippingCost = 0;
        double distance = this.stores.get(storeId).getLocation().distance(this.getIdToCustomer().get(customerId).getLocation().x, this.getIdToCustomer().get(customerId).getLocation().y);

        return this.stores.get(storeId).getDeliveryPPK() * distance;
    }

    public  Map<Integer, List<Integer>> makeStoreIdToItemIdList(ProgressStaticOrderContainer staticOrder, Integer storeId) {
        Map<Integer, List<Integer>> storeIdToItemIdList = new HashMap<>();
        List<Integer> itemsList = new ArrayList<>();

        for (StoreItemInformation itemInformation : staticOrder.getItemIdToOrderInfo().values()) {
            if (itemInformation.getAmount() != null) {
                itemsList.add(itemInformation.getItem().getId());
            }
        }

        storeIdToItemIdList.put(storeId, itemsList);
        return storeIdToItemIdList;
    }

    public Integer getNextDynamicOrderId() {
        return dynamicOrderId += 1;
    }

    public boolean isItemDefinedInSystem(Integer itemId)
    {
        return items.containsKey(itemId);
    }

    public void addNewItemToSystem(Integer itemIdSelection, String itemNameSelection, PurchaseCategory purchaseCategorySelection, Map<Integer, Integer> sellingStoresIdToPrice){
        Item newItem = new Item(itemIdSelection, itemNameSelection, purchaseCategorySelection);
        items.put(newItem.getId(), newItem);

        for(Integer storeId : sellingStoresIdToPrice.keySet())
        {
            stores.get(storeId).getItemsIdAndPrices().put(newItem.getId(), sellingStoresIdToPrice.get(storeId));
            stores.get(storeId).getItemsAndPrices().put(newItem, sellingStoresIdToPrice.get(storeId));
        }
    }


    public  List<SingleDiscountContainer> findRelevantDiscounts(Map<Integer, List<Integer>> storeIdToItemIDList, Containable progressOrderInfo) throws CloneNotSupportedException {
        Integer purchasedItemId;
        float amountPurchased = 0;
        double discountAmount;
        int numberOfCurrentDiscount;
        List<SingleDiscountContainer> discountsContainerList = new ArrayList<>();

        for (Integer storeId : storeIdToItemIDList.keySet()) {
            for (Discount discount : this.stores.get(storeId).getDiscountList()) {
                purchasedItemId = discount.getIfYouBuy().getItemId();
                discountAmount = discount.getIfYouBuy().getQuantity();
                if (storeIdToItemIDList.get(storeId).contains(purchasedItemId)) {
                    if(progressOrderInfo instanceof ProgressStaticOrderContainer) {
                        amountPurchased = ((ProgressStaticOrderContainer)progressOrderInfo).getItemIdToOrderInfo().get(purchasedItemId).getAmount();
                    } else if (progressOrderInfo instanceof ProgressDynamicOrderContainer) {
                        amountPurchased = ((ProgressDynamicOrderContainer)progressOrderInfo).getItemIdToOrderItem().get(purchasedItemId).getAmount();
                    }
                    if (((int) (amountPurchased / discountAmount)) >= 1) {

                        for (Offer offer : discount.getThenYouGet().getOffers()) {
                            if(progressOrderInfo instanceof ProgressStaticOrderContainer) {
                                if(((ProgressStaticOrderContainer)progressOrderInfo).getItemIdToOrderInfo().containsKey(offer.getItemId())
                                && ((ProgressStaticOrderContainer)progressOrderInfo).getItemIdToOrderInfo().get(offer.getItemId()).getAmount() != null)
                                {
                                    discountAmount = ((ProgressStaticOrderContainer)progressOrderInfo).getItemIdToOrderInfo().get(offer.getItemId()).getAmount();
                                }
                                else
                                {
                                    discountAmount = 0;
                                }
                            } else if (progressOrderInfo instanceof ProgressDynamicOrderContainer) {
                                if(((ProgressDynamicOrderContainer)progressOrderInfo).getItemIdToOrderItem().containsKey(offer.getItemId()))
                                {
                                    discountAmount = ((ProgressDynamicOrderContainer)progressOrderInfo).getItemIdToOrderItem().get(offer.getItemId()).getAmount();
                                }
                                else
                                {
                                    discountAmount = 0;
                                }
                            }
                            if ((numberOfCurrentDiscount = (int) ( discountAmount/ offer.getQuantity())) >= 1)

                                for (int i = 0; i < numberOfCurrentDiscount; i++) {
                                    SingleDiscountContainer singleDiscountContainer = new SingleDiscountContainer();
                                    singleDiscountContainer.setIfYouBuy(discount.getIfYouBuy().clone());
                                    singleDiscountContainer.setThenYouGet(discount.getThenYouGet().clone());
                                    singleDiscountContainer.setName(discount.getName());
                                    discountsContainerList.add(singleDiscountContainer);
                                }
                        }
                    }
                }
            }
        }
        return discountsContainerList;
    }

    public Integer getPriceForItem(Integer storeId, Integer itemId)
    {
        return stores.get(storeId).getItemsIdAndPrices().get(itemId);
    }

    public String getNameFromItemId(Integer itemId)
    {
        return items.get(itemId).getName();
    }

    public OrderSummeryContainer getOrderSummery(Containable progressOrderContainer, Map<Integer, List<Integer>> storeIdToItemIDList) {
        OrderSummeryContainer orderSummeryContainer;

        if (progressOrderContainer instanceof ProgressStaticOrderContainer) {
            orderSummeryContainer = getStaticOrderSummery((ProgressStaticOrderContainer) progressOrderContainer, storeIdToItemIDList);
        } else {
            orderSummeryContainer = getDynamicOrderSummery((ProgressDynamicOrderContainer) progressOrderContainer, storeIdToItemIDList);
        }

        return orderSummeryContainer;
    }

    private OrderSummeryContainer getDynamicOrderSummery(ProgressDynamicOrderContainer progressDynamicOrderInfo, Map<Integer, List<Integer>> storeIdToItemIDList) {
        OrderSummeryContainer dynamicOrderSummeryContainer = new OrderSummeryContainer();
        float totalOrderCostWithoutShipping = 0;
        double totalShippingCost = 0;

        for (Integer storeId : storeIdToItemIDList.keySet()) {
            Store store = stores.get(storeId).clone();
            SingleOrderStoreInfo singleDynamicStoreInfo = new SingleOrderStoreInfo();

            singleDynamicStoreInfo.setStoreId(storeId);
            singleDynamicStoreInfo.setStoreName(store.getName());
            singleDynamicStoreInfo.setPpk(store.getDeliveryPPK());
            Double distance = store.getLocation().distance(progressDynamicOrderInfo.getUserLocation().x, progressDynamicOrderInfo.getUserLocation().y);
            singleDynamicStoreInfo.setDistanceFromCustomer(distance);
            singleDynamicStoreInfo.setCustomerShippingCost(store.getDeliveryPPK() * distance);
            totalShippingCost +=  singleDynamicStoreInfo.getCustomerShippingCost();

            for (Integer itemId : storeIdToItemIDList.get(storeId)) {
                ProgressOrderItem item = progressDynamicOrderInfo.getItemIdToOrderItem().get(itemId);
                OrderStoreItemInfo itemInfo;
                if (item.getAmount() != null && item.getAmount() != 0) {
                    itemInfo = new OrderStoreItemInfo();
                    itemInfo.setItemId(itemId);
                    itemInfo.setItemName(item.getItemName());
                    itemInfo.setAmount(item.getAmount());
                    itemInfo.setPurchaseCategory(item.getPurchaseCategory());
                    itemInfo.setPricePerPiece((float) store.getItemsIdAndPrices().get(itemId));
                    itemInfo.setTotalPrice(item.getAmount() * (float) store.getItemsIdAndPrices().get(itemId));
                    itemInfo.setFromDiscount(false);

                    singleDynamicStoreInfo.getProgressItems().add(itemInfo);
                    totalOrderCostWithoutShipping += itemInfo.getTotalPrice();
                }

                if (item.getDiscountAmount() != null && item.getDiscountAmount() != 0) {
                    itemInfo = new OrderStoreItemInfo();
                    itemInfo.setItemId(itemId);
                    itemInfo.setItemName(item.getItemName());
                    itemInfo.setAmount(item.getDiscountAmount());
                    itemInfo.setPurchaseCategory(item.getPurchaseCategory());
                    if(item.getOptimisedItemPrice() != null)
                    {
                        itemInfo.setPricePerPiece((float) item.getOptimisedItemPrice());
                        itemInfo.setTotalPrice(item.getDiscountAmount() * (float) item.getOptimisedItemPrice());
                    }
                    else
                    {
                        itemInfo.setPricePerPiece((float) store.getItemsIdAndPrices().get(itemId));
                        itemInfo.setTotalPrice(item.getDiscountAmount() * (float) store.getItemsIdAndPrices().get(itemId));
                    }

                    itemInfo.setFromDiscount(true);

                    singleDynamicStoreInfo.getProgressItems().add(itemInfo);
                    totalOrderCostWithoutShipping += itemInfo.getTotalPrice();
                }
                dynamicOrderSummeryContainer.getStoreIdToStoreInfo().put(storeId, singleDynamicStoreInfo);
            }
        }
        dynamicOrderSummeryContainer.setTotalOrderCostWithoutShipping(totalOrderCostWithoutShipping);
        dynamicOrderSummeryContainer.setTotalShippingCost(totalShippingCost);
        dynamicOrderSummeryContainer.setTotalOrderCost(totalShippingCost + totalOrderCostWithoutShipping);
        dynamicOrderSummeryContainer.setDynamicOrderId(getNextDynamicOrderId());
        return dynamicOrderSummeryContainer;
    }

    private OrderSummeryContainer getStaticOrderSummery(ProgressStaticOrderContainer progressStaticOrderContainer, Map<Integer, List<Integer>> storeIdToItemIDList ) {
        OrderSummeryContainer orderSummeryContainer = new OrderSummeryContainer();
        float totalOrderCostWithoutShipping = 0;
        double totalShippingCost = 0;

        for (Integer storeId : storeIdToItemIDList.keySet()) {
            Store store = stores.get(storeId).clone();
            SingleOrderStoreInfo singleDynamicStoreInfo = new SingleOrderStoreInfo();

            singleDynamicStoreInfo.setStoreId(storeId);
            singleDynamicStoreInfo.setStoreName(store.getName());
            singleDynamicStoreInfo.setPpk(store.getDeliveryPPK());
            Double distance = progressStaticOrderContainer.getDistance();
            singleDynamicStoreInfo.setDistanceFromCustomer(distance);
            singleDynamicStoreInfo.setCustomerShippingCost(store.getDeliveryPPK() * distance);
            totalShippingCost += singleDynamicStoreInfo.getCustomerShippingCost();

            for (Integer itemId : storeIdToItemIDList.get(storeId)) {
                StoreItemInformation item = progressStaticOrderContainer.getItemIdToOrderInfo().get(itemId);
                OrderStoreItemInfo itemInfo;
                if (item.getAmount() != null && item.getAmount() != 0) {
                    itemInfo = new OrderStoreItemInfo();
                    itemInfo.setItemId(itemId);
                    itemInfo.setItemName(item.getItem().getName());
                    itemInfo.setAmount(item.getAmount());
                    itemInfo.setPurchaseCategory(item.getItem().getPurchaseCategory());
                    itemInfo.setPricePerPiece((float)item.getItemPrice());
                    itemInfo.setTotalPrice(item.getTotalItemPrice());
                    itemInfo.setFromDiscount(false);

                    singleDynamicStoreInfo.getProgressItems().add(itemInfo);
                    totalOrderCostWithoutShipping += itemInfo.getTotalPrice();
                }

                if (item.getDiscountAmount() != null && item.getDiscountAmount() != 0) {
                    itemInfo = new OrderStoreItemInfo();
                    itemInfo.setItemId(itemId);
                    itemInfo.setItemName(item.getItem().getName());
                    itemInfo.setAmount(item.getDiscountAmount());
                    itemInfo.setPurchaseCategory(item.getItem().getPurchaseCategory());
                    itemInfo.setPricePerPiece((float) item.getItemPrice());
                    itemInfo.setTotalPrice(item.getTotalItemPrice());

                    itemInfo.setFromDiscount(true);

                    singleDynamicStoreInfo.getProgressItems().add(itemInfo);
                    totalOrderCostWithoutShipping += itemInfo.getTotalPrice();
                }
                orderSummeryContainer.getStoreIdToStoreInfo().put(storeId, singleDynamicStoreInfo);
            }
        }
        orderSummeryContainer.setTotalOrderCostWithoutShipping(totalOrderCostWithoutShipping);
        orderSummeryContainer.setTotalShippingCost(totalShippingCost);
        orderSummeryContainer.setTotalOrderCost(totalShippingCost + totalOrderCostWithoutShipping);

        return orderSummeryContainer;
    }

    public List<OrderSummeryContainer> getOrdersHistory() {
        List<OrderSummeryContainer> ordersHistory = new ArrayList<>();

        for(Store store : stores.values()) {
            for(Order order: store.getOrders()) {
               OrderSummeryContainer orderContainer = new OrderSummeryContainer();
               orderContainer.setDynamicOrderId(order.getDynamicOrderId());
               orderContainer.setTotalShippingCost(order.getDeliveryCost());
               orderContainer.setTotalOrderCost(order.getTotalOrderPrice());
               orderContainer.setTotalOrderCostWithoutShipping(order.getTotalItemsPrice());
               SingleOrderStoreInfo storeInfo = new SingleOrderStoreInfo();
               storeInfo.setStoreId(store.getId());
               storeInfo.setStoreName(store.getName());
               storeInfo.setPpk(store.getDeliveryPPK());
               storeInfo.setDistanceFromCustomer(order.getDistance());
               storeInfo.setCustomerShippingCost(order.getDeliveryCost());

                List<OrderStoreItemInfo> progressItems = new ArrayList<>();
                for(OrderedItem orderedItem : order.getItemIdPairToItems().values()) {
                    OrderStoreItemInfo itemInfo = new OrderStoreItemInfo();
                    itemInfo.setItemId(orderedItem.getItemId());
                    itemInfo.setItemName(orderedItem.getItemName());
                    itemInfo.setAmount(orderedItem.getAmount());
                    itemInfo.setFromDiscount(orderedItem.isFromDiscount());
                    itemInfo.setPricePerPiece(orderedItem.getPricePerPiece());
                    itemInfo.setPurchaseCategory(orderedItem.getPurchaseCategory());
                    itemInfo.setTotalPrice(orderedItem.getTotalPrice());
                    progressItems.add(itemInfo);
                }
                storeInfo.setProgressItems(progressItems);
                orderContainer.getStoreIdToStoreInfo().put(storeInfo.getStoreId(),storeInfo);
                ordersHistory.add(orderContainer);
            }
        }
        return ordersHistory;
    }

    public List<Mappable> getMapElements() {
        List<Mappable> mapElements = new ArrayList<>();
        for (Store store : stores.values()) {
            StoreMapContainer storeMapContainer = new StoreMapContainer();
            storeMapContainer.setStoreId(store.getId());
            storeMapContainer.setStoreName(store.getName());
            storeMapContainer.setPpk(store.getDeliveryPPK());
            storeMapContainer.setNumOfOrders(store.getStoresOrders().size());
            storeMapContainer.setLocation((Point)store.getLocation().clone());
            mapElements.add(storeMapContainer);
        }
        for (Customer customer : getIdToCustomer().values()) {
            CustomerMapContainer customerMapContainer = new CustomerMapContainer();
            customerMapContainer.setCustomerId(customer.getId());
            customerMapContainer.setCustomerName(customer.getName());
            customerMapContainer.setNumOfOrders(customer.getNumberOfOrders());
            customerMapContainer.setLocation((Point)customer.getLocation().clone());
            mapElements.add(customerMapContainer);
        }
        return mapElements;
    }

    public Point getMaxCoordinates() {
        Point maxCoordinates = new Point(1, 1);

        for (Store store : stores.values()) {
            if (store.getLocation().x > maxCoordinates.x) {
                maxCoordinates.setLocation(store.getLocation().x, maxCoordinates.y);
            }
            if (store.getLocation().y > maxCoordinates.y) {
                maxCoordinates.setLocation(maxCoordinates.x, store.getLocation().y);
            }
        }
        for (Customer customer : getIdToCustomer().values()) {
            if (customer.getLocation().x > maxCoordinates.x) {
                maxCoordinates.setLocation(customer.getLocation().x, maxCoordinates.y);
            }
            if (customer.getLocation().y > maxCoordinates.y) {
                maxCoordinates.setLocation(maxCoordinates.x, customer.getLocation().y);
            }
        }
        return maxCoordinates;
    }

    public void updateNewOrder(OrderSummeryContainer orderSummery, Integer storeId, Integer userId,String orderDate) {
        float totalItemsCost = 0;

        for(SingleOrderStoreInfo storeInfo : orderSummery.getStoreIdToStoreInfo().values()) {
            Order newOrder = new Order();
            for (OrderStoreItemInfo itemInfo : storeInfo.getProgressItems()) {
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setItemId(itemInfo.getItemId());
                orderedItem.setItemName(itemInfo.getItemName());
                orderedItem.setAmount(itemInfo.getAmount());
                orderedItem.setPurchaseCategory(itemInfo.getPurchaseCategory());
                orderedItem.setPricePerPiece(itemInfo.getPricePerPiece());
                orderedItem.setFromDiscount(itemInfo.isFromDiscount());
                orderedItem.setTotalPrice(itemInfo.getTotalPrice());
                newOrder.getItemIdPairToItems().put(new Pair<>(orderedItem.getItemId(), orderedItem.isFromDiscount()), orderedItem);
                totalItemsCost += itemInfo.getTotalPrice();

                stores.get(storeInfo.getStoreId()).getPurchasedItems().put(itemInfo.getItemId(),itemInfo.getAmount());
            }
            newOrder.setOrderId(stores.get(storeInfo.getStoreId()).getNewOrderId());
            newOrder.setDeliveryCost(storeInfo.getCustomerShippingCost());
            newOrder.setDistance(storeInfo.getDistanceFromCustomer());
            List<Integer> distinctItems = new ArrayList<>();
            for (OrderStoreItemInfo item : storeInfo.getProgressItems()) {
                if (!distinctItems.contains(item.getItemId())) {
                    distinctItems.add(item.getItemId());
                }
            }

            newOrder.setTotalNumberOfItems(distinctItems.size());
            newOrder.setStoreId(storeInfo.getStoreId());
            newOrder.setStoreName(storeInfo.getStoreName());
            newOrder.setTotalItemsPrice(totalItemsCost);
            newOrder.setTotalOrderPrice(storeInfo.getCustomerShippingCost() + totalItemsCost);
            newOrder.setOrderDate(orderDate);
            if(orderSummery.getDynamicOrderId() != null) {
                newOrder.setDynamicOrderId(orderSummery.getDynamicOrderId());
            }

            double totalDeliveryPayments = stores.get(storeInfo.getStoreId()).getTotalDeliveryPayment();
            stores.get(storeInfo.getStoreId()).setTotalDeliveryPayment(totalDeliveryPayments + newOrder.getDeliveryCost());
            stores.get(storeInfo.getStoreId()).getOrders().add(newOrder);
        }
        Customer customer = this.idToCustomer.get(userId);
        Integer newAddedOrders = orderSummery.getStoreIdToStoreInfo().size();
        customer.setNumberOfOrders(customer.getNumberOfOrders() + newAddedOrders);
        customer.setAverageShipping((float)((customer.getAverageShipping() * (customer.getNumberOfOrders() - newAddedOrders)
                + orderSummery.getTotalShippingCost()) / customer.getNumberOfOrders()));

        customer.setAverageOrdersCost(((customer.getAverageOrdersCost() * (customer.getNumberOfOrders() - newAddedOrders)
                + orderSummery.getTotalOrderCostWithoutShipping()) / customer.getNumberOfOrders()));

    }

    public void addNewDiscountToStore(AddDiscountContainer newDiscount) throws CloneNotSupportedException {
        Discount discount = new Discount();
        discount.setName(newDiscount.getDiscountName());
        discount.setIfYouBuy(newDiscount.getIfYouBuy().clone());
        discount.setThenYouGet(newDiscount.getThenYouGet().clone());
        stores.get(newDiscount.getStoreId()).getDiscountList().add(discount);
    }

    @Override
    public Connector clone() throws CloneNotSupportedException {
        DesktopEngine cloneDesktopEngine = (DesktopEngine) super.clone();
        Map<Integer,Item> itemsCopy = new HashMap<>();
        Map<Integer,Store> storesCopy = new HashMap<>();
        Map<Integer,Customer> customerCopy = new HashMap<>();

        for(Item item : items.values()) {
            itemsCopy.put(item.getId(),item.clone());
        }
        for(Store store : stores.values()) {
            storesCopy.put(store.getId(),store.clone());
        }
        for (Customer customer : idToCustomer.values()) {
            customerCopy.put(customer.getId(),customer.clone());
        }

        cloneDesktopEngine.items = itemsCopy;
        cloneDesktopEngine.stores = storesCopy;
        cloneDesktopEngine.setIdToCustomer(customerCopy);

        return cloneDesktopEngine;
    }

    @Override
    public StoresContainer getStoresInformation() throws CloneNotSupportedException {
        return systemStoresInformation();
    }

    public StoresContainer systemStoresInformation() throws CloneNotSupportedException {
        StoresContainer systemStoresInformation = new StoresContainer();

        for(Store store : stores.values()){
            Store newStore = store.clone();
            systemStoresInformation.getStores().put(newStore.getId(),newStore);
        }

        return systemStoresInformation;
    }

    @Override
    public ItemsContainer getItemsInformation() throws CloneNotSupportedException {
       return systemItemsInformation();
    }

    @Override
    public ProgressStaticOrderContainer getProgressOrderInformation(Point userLocation, Integer storeId, Map<Integer, StoreItemInformation> storeItemInfo) throws CloneNotSupportedException {
        ProgressStaticOrderContainer progressOrderContainer = new ProgressStaticOrderContainer();
        progressOrderContainer.calcDistPpkAndShippingCost(userLocation, stores.get(storeId));

        for(StoreItemInformation itemInfo : storeItemInfo.values()) {
            StoreItemInformation item = new StoreItemInformation();
            item.setItem(itemInfo.getItem().clone());
            item.setItemPrice(itemInfo.getItemPrice());
            if(itemInfo.getAmount() != null) {
                item.setAmountAndTotalPrice(itemInfo.getAmount());
            }
            progressOrderContainer.getItemIdToOrderInfo().put(item.getItem().getId(), item);
        }

        return progressOrderContainer;
    }

    @Override
    public Map<Integer,StoreItemInformation> getStoreItemsInformation (Integer storeId) throws CloneNotSupportedException {
        Map<Integer,StoreItemInformation> storeItemInformation = new HashMap<>();
        Store chosenStore = stores.get(storeId);
        StoreItemInformation itemInformation;
        Item cloneItem;

        for(Item item : items.values()){
            itemInformation = new StoreItemInformation();
            cloneItem = item.clone();
            itemInformation.setItem(cloneItem);
            if(!chosenStore.getItemsIdAndPrices().containsKey(item.getId())) {
                itemInformation.setItemPrice(-1);

            } else{
                itemInformation.setItemPrice(chosenStore.getItemsAndPrices().get(item));
            }
            storeItemInformation.put(item.getId(),itemInformation);
        }
        return storeItemInformation;
    }

    @Override
    public void makeNewOrder(Integer storeId, Date orderDate, Orderable progressOrderInfo) {

    }

    @Override
    public Map<Pair<Integer,Integer>, OrdersContainer> getStoresOrders() {
        return null;
    }

    @Override
    public void deleteStoreItem(Integer storeId, Integer itemId) throws ItemIsNotSoldException, SingleSellingStoreException {
        long numberOfSellingStores = 0;
        if(stores.get(storeId).getItemsIdAndPrices().containsKey(itemId)) {
            numberOfSellingStores = stores.values().stream().filter(store -> store.getItemsIdAndPrices().containsKey(itemId) == true).count();
            if(numberOfSellingStores > 1) {
                //delete
                stores.get(storeId).getItemsIdAndPrices().remove(itemId);
                stores.get(storeId).getItemsAndPrices().remove(items.get(itemId));
                deleteStoreDiscount(storeId, itemId);
            } else {
                throw new SingleSellingStoreException();
            }
        } else {
            throw new ItemIsNotSoldException();
        }

    }

    private List<String> deleteStoreDiscount(Integer storeId, Integer itemId) {
        List<Discount> discounts = stores.get(storeId).getDiscountList();
        List<String> removedDiscounts = new ArrayList<>();

        for (Discount discount : discounts) {
            if (discount.getIfYouBuy().getItemId() == itemId) {
                removedDiscounts.add(discount.getName());
                discounts.remove(discount);
            } else {
                for (Offer offer : discount.getThenYouGet().getOffers()) {
                    if (offer.getItemId() == itemId) {
                        removedDiscounts.add(discount.getName());
                        discounts.remove(discount);
                    }
                }
            }
        }
        return removedDiscounts;
    }

    public void addNewStore(AddNewStoreContainer newStoreToAdd) throws CloneNotSupportedException {

        Map<Integer, Integer> itemIdToItemPrice = new HashMap<>();
        Map<Item,Integer> itemToItemPrice = new HashMap<>();

        for(AddNewStoreItemContainer itemToAdd : newStoreToAdd.getItemsList()) {
            Item newItem = items.get(itemToAdd.getItemId()).clone();
            itemIdToItemPrice.put(newItem.getId(),itemToAdd.getItemPrice());
            itemToItemPrice.put(newItem,itemToAdd.getItemPrice());
        }

        Store newStore = new Store(newStoreToAdd.getStoreId(),newStoreToAdd.getStoreName(),
                newStoreToAdd.getPpk(), newStoreToAdd.getStoreLocation(),itemIdToItemPrice);

        newStore.setItemsAndPrices(itemToItemPrice);
        stores.put(newStore.getId(),newStore);
    }

    public void rejectIfStoreIdIsAlreadyExist(Integer storeId) throws XmlSimilarStoresIdException {
        if(stores.get(storeId) != null) {
            throw new XmlSimilarStoresIdException();
        }
    }

    @Override
    public void rejectIfItemNotDefinedInStore(Integer storeId, Integer itemId) throws ItemIsNotSoldException {
        if(!stores.get(storeId).isItemSoldInStore(itemId)) {
            throw new ItemIsNotSoldException();
        }
    }

    @Override
    public void rejectIfItemDefinedInStore(Integer storeId, Integer itemId) throws XmlSimilarItemsIdException {
        if(stores.get(storeId).isItemSoldInStore(itemId)) {
            throw new XmlSimilarItemsIdException();
        }
    }

    @Override
    public void addNewItemToStore(Integer storeId, Integer itemId, Integer itemPrice) {
        stores.get(storeId).addItem(items.get(itemId),itemPrice);
    }

    @Override
    public void updateItemPrice(Integer storeId, Integer itemId, Integer newPrice) throws ItemIsNotSoldException {
        stores.get(storeId).getItemsAndPrices().put(items.get(itemId),newPrice);
        stores.get(storeId).getItemsIdAndPrices().put(itemId,newPrice);
    }

    @Override
    public void saveOrdersToFile(String path) throws IOException {

    }

    @Override
    public void loadOrdersFromFile(String usersPath) throws IOException, ClassNotFoundException, InvalideOrderHistoryLoadFileException {

    }

    @Override
    public void saveMaxOrderIdToFile() throws IOException {

    }

    @Override
    public void loadMaxOrderIdToFile() throws FileNotFoundException {

    }

    @Override
    public ProgressDynamicOrderContainer getProgressDynamicOrder(Point userLocation, Map<Integer,Float> itemIdToAmount) {
        ProgressDynamicOrderContainer progressDynamicOrderContainer = new ProgressDynamicOrderContainer();
        ProgressOrderItem progressDynamicItem;
        Item item;

        for(Integer itemId : itemIdToAmount.keySet()) {
            progressDynamicItem = new ProgressOrderItem();
            item = items.get(itemId);
            progressDynamicItem.setItemId(itemId);
            progressDynamicItem.setAmount(itemIdToAmount.get(itemId));
            progressDynamicItem.setItemName(item.getName());
            progressDynamicItem.setPurchaseCategory(item.getPurchaseCategory());
            progressDynamicOrderContainer.getItemIdToOrderItem().put(itemId,progressDynamicItem);
        }
        progressDynamicOrderContainer.setUserLocation(userLocation);
        return progressDynamicOrderContainer;
    }


    public CustomersContainer getCustomersInformation() {
        CustomersContainer customersContainer = new CustomersContainer();

        for(Customer customer : idToCustomer.values()) {
            customersContainer.getCustomerIdToCustomer()
                    .put(customer.getId(),
                            new CustomerInformation(customer.getId(),customer.getName(),customer.getLocation(),customer.getNumberOfOrders(),customer.getAverageOrdersCost(),customer.getAverageShipping()));
        }

        return customersContainer;
    }

    public Map<Integer, Customer> getIdToCustomer() {
        return idToCustomer;
    }

    public void setIdToCustomer(Map<Integer, Customer> idToCustomer) {
        this.idToCustomer = idToCustomer;
    }

    public ItemsContainer systemItemsInformation() throws CloneNotSupportedException {
        ItemsContainer systemItemsInfo = new ItemsContainer();
        List<Store> storesList;
        int sellingStores;
        int sum;
        float average;
        float numberOfSoldItems = 0;
        ItemInfo itemInfo;

        for(Item item : items.values()) {
            itemInfo = new ItemInfo();

            storesList = stores.values().stream().filter(store -> store.getItemsIdAndPrices()
                    .containsKey(item.getId())).collect(Collectors.toList());
            sellingStores = storesList.size();

            sum = storesList.stream().mapToInt(store -> store.getItemsIdAndPrices().get(item.getId())).sum();
            average = sum / sellingStores;

            numberOfSoldItems = 0;

            for (Store store : storesList) {
                if(store.getPurchasedItems().containsKey(item.getId()))
                    numberOfSoldItems += store.getPurchasedItems().get(item.getId());
            }
            itemInfo.setItemId(item.getId());
            itemInfo.setItemName(item.getName());
            itemInfo.setAvgPrice(average);
            itemInfo.setNumOfSellingStores(sellingStores);
            itemInfo.setPurchaseCategory(item.getPurchaseCategory());
            itemInfo.setTotalPurchases(numberOfSoldItems);
            systemItemsInfo.getItemIdToItemInfo().put(item.getId(),itemInfo);
        }

        return systemItemsInfo;
    }
}
