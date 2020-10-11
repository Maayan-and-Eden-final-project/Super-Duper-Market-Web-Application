package validation;

import areas.Area;
import exceptions.*;
import sdm.enums.Operator;
import sdm.enums.PurchaseCategory;
import sdm.generated3.SDMItem;
import sdm.generated3.SDMStore;
import sdm.generated3.SuperDuperMarketDescriptor;
import sdm.generated2.*;
import sdm.sdmElements.*;
import sdm.sdmElements.IfYouBuy;
import sdm.sdmElements.ThenYouGet;
import sdmWebApplication.utils.ServletUtils;
import systemEngine.Connector;
import systemEngine.DesktopEngine;
import systemEngine.WebEngine;
import users.SingleUser;
import users.UserManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static sdmWebApplication.utils.ServletUtils.getUserManager;

public class XmlValidate implements Validator {
    private SuperDuperMarketDescriptor generatedSdm;
    private Connector engine;
    private UserManager userManager;
    private Area tempArea;

    public XmlValidate(Connector linkableEngine) {
        this.engine = linkableEngine;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void validate(InputStream inputStream, String userName) throws Exception {

        try {
            isXmlFileExist(inputStream, userName);
            isAllItemsIdUnique();
            isAllStoresIdUnique();
            isStoresItemExist();
            isAllItemsSold();
            isItemAlreadyExist();
            isStoresInValidLocation();
            isAllDiscountsItemSold();
            userManager.addAreaToUser(tempArea, userName);
        } catch (Exception e) {
            throw e;
        }
    }

    private String extractFileExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    private void isXmlFileExist(InputStream inputStream, String userName) throws JAXBException, AreaAlreadyExistException {

        try {

            JAXBContext jaxbContext = null;
            jaxbContext = JAXBContext.newInstance(SuperDuperMarketDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            generatedSdm = (SuperDuperMarketDescriptor) jaxbUnmarshaller.unmarshal(inputStream);
            loadFromGeneratedSdm(generatedSdm, userName);

        } catch (JAXBException e) {
            throw e;
        }
    }

    private void isAllItemsIdUnique() throws XmlSimilarItemsIdException {
        List<Integer> generatedSdmIdList = new ArrayList<>();
        generatedSdm.getSDMItems().getSDMItem().stream().forEach(item -> generatedSdmIdList.add(item.getId()));

        Map<Integer, Integer> idToFrequency = getFrequencies(generatedSdmIdList);
        Long duplicates = idToFrequency.entrySet().stream().filter(id -> id.getValue() > 1).count();
        if (duplicates > 0) {
            throw new XmlSimilarItemsIdException();
        }
    }

    private void isAllStoresIdUnique() throws XmlSimilarStoresIdException {
        List<Integer> generatedSdmIdList = new ArrayList<>();
        generatedSdm.getSDMStores().getSDMStore().stream().forEach(store -> generatedSdmIdList.add(store.getId()));

        Map<Integer, Integer> idToFrequency = getFrequencies(generatedSdmIdList);
        Long duplicates = idToFrequency.entrySet().stream().filter(id -> id.getValue() > 1).count();
        if (duplicates > 0) {
            throw new XmlSimilarStoresIdException();
        }
    }

    private void isStoresItemExist() throws XmlItemNotFoundException {

        Long allItemsExist = tempArea.getStoreIdToStore().values().stream().filter(store ->
                tempArea.getItemIdToItem().keySet().containsAll(store.getItemsIdAndPrices().keySet()) == false).count();
        if (allItemsExist > 0) {
            throw new XmlItemNotFoundException();
        }
    }

    private void isAllDiscountsItemSold() throws XmlDiscountItemIsNotDefinedException, XmlDiscountItemIsNotSoldException {

        if (engine instanceof DesktopEngine) {
            Collection<Store> storesList = engine.getStores().values();
            for (Store store : storesList) {
                for (Discount discount : store.getDiscountList()) {
                    isDiscountItemsInSystem(discount, store);
                }
            }
        } else if (engine instanceof WebEngine) {
            for (Store store : tempArea.getStoreIdToStore().values()) {
                for (Discount discount : store.getDiscountList()) {
                    isDiscountItemsInSystem(discount, store);
                }
            }
        }
    }

    private void isDiscountItemsInSystem(Discount discount, Store store) throws XmlDiscountItemIsNotDefinedException, XmlDiscountItemIsNotSoldException {
        if (engine instanceof DesktopEngine) {
            for (Offer offer : discount.getThenYouGet().getOffers()) {
                if (!engine.getItems().keySet().contains(offer.getItemId())) {
                    throw new XmlDiscountItemIsNotDefinedException();
                }
                if (!store.getItemsIdAndPrices().keySet().contains(offer.getItemId())) {
                    throw new XmlDiscountItemIsNotSoldException();
                }
            }
            if (!engine.getItems().keySet().contains(discount.getIfYouBuy().getItemId())) {
                throw new XmlDiscountItemIsNotDefinedException();
            }
            if (!store.getItemsIdAndPrices().keySet().contains(discount.getIfYouBuy().getItemId())) {
                throw new XmlDiscountItemIsNotSoldException();
            }
        } else if (engine instanceof WebEngine) {
            for (Offer offer : discount.getThenYouGet().getOffers()) {
                if (!tempArea.getItemIdToItem().keySet().contains(offer.getItemId())) {
                    throw new XmlDiscountItemIsNotDefinedException();
                }
                if (!store.getItemsIdAndPrices().keySet().contains(offer.getItemId())) {
                    throw new XmlDiscountItemIsNotSoldException();
                }
            }
            if (!tempArea.getItemIdToItem().keySet().contains(discount.getIfYouBuy().getItemId())) {
                throw new XmlDiscountItemIsNotDefinedException();
            }
            if (!store.getItemsIdAndPrices().keySet().contains(discount.getIfYouBuy().getItemId())) {
                throw new XmlDiscountItemIsNotSoldException();
            }
        }
    }

    private void isAllItemsSold() throws XmlItemIsNotSoldException {
        Map<Integer, Integer> frequencyId = new HashMap<>();
        Collection<Store> storesList = tempArea.getStoreIdToStore().values();
        frequencyId = tempArea.getItemIdToItem().keySet().stream().collect(Collectors.toMap(itemId -> itemId, frequency -> 0));

        for (Store store : storesList) {
            for (Integer itemId : store.getItemsIdAndPrices().keySet()) {
                Integer currentCount = frequencyId.get(itemId);
                frequencyId.put(itemId, ++currentCount);
            }
        }

        if (frequencyId.values().contains(0)) {
            throw new XmlItemIsNotSoldException();
        } else {
            Map<Integer, Item> allItems = tempArea.getItemIdToItem();
            for (Store store : tempArea.getStoreIdToStore().values()) {
                for (Integer itemId : store.getItemsIdAndPrices().keySet()) {
                    Item tempItem = allItems.get(itemId);
                    Item item = new Item(tempItem.getId(), tempItem.getName(), tempItem.getPurchaseCategory());
                    store.getItemsAndPrices().put(item, store.getItemsIdAndPrices().get(itemId));
                }
            }
        }
    }

    private void isItemAlreadyExist() throws XmlItemAlreadyExistException {
        List<Integer> itemIds = new ArrayList<>();
        Long duplicates = null;
        for(SDMStore store : generatedSdm.getSDMStores().getSDMStore()) {
            itemIds = store.getSDMPrices().getSDMSell().stream().map(item -> item.getItemId()).collect(Collectors.toList());
            List<Integer> finalItemIds = itemIds;
            duplicates = itemIds.stream().filter(id -> Collections.frequency(finalItemIds, id) > 1).count();

            if (duplicates > 0) {
                throw new XmlItemAlreadyExistException();
            }
        }
    }

    private void isStoresInValidLocation() throws XmlStoreLocationOutOfRangeException, XmlCustomerLocationOutOfRangeException, XmlSameCustomerAndStoreLocationException {
        Long invalidCustomerLocation = null;

        if (engine instanceof DesktopEngine) {
            Long invalidStoreLocation = engine.getStores().values().stream().filter(store ->
                    store.getLocation().getX() < 1 ||
                            store.getLocation().getX() > 50 ||
                            store.getLocation().getY() < 1 ||
                            store.getLocation().getY() > 50).count();

            if (engine instanceof DesktopEngine) {
                invalidCustomerLocation = ((DesktopEngine) engine).getIdToCustomer().values().stream().filter(customer ->
                        customer.getLocation().getX() < 1 ||
                                customer.getLocation().getX() > 50 ||
                                customer.getLocation().getY() < 1 ||
                                customer.getLocation().getY() > 50).count();

                for (Customer customer : ((DesktopEngine) engine).getIdToCustomer().values()) {
                    for (Store store : engine.getStores().values()) {
                        if (customer.getLocation().equals(store.getLocation())) {
                            throw new XmlSameCustomerAndStoreLocationException();
                        }
                    }
                }
                if (invalidCustomerLocation > 0) {
                    throw new XmlCustomerLocationOutOfRangeException();
                }
            }
            if (invalidStoreLocation > 0) {
                throw new XmlStoreLocationOutOfRangeException();
            }
        } else if (engine instanceof WebEngine) {
            Long invalidStoreLocation = tempArea.getStoreIdToStore().values().stream().filter(store ->
                    store.getLocation().getX() < 1 ||
                            store.getLocation().getX() > 50 ||
                            store.getLocation().getY() < 1 ||
                            store.getLocation().getY() > 50).count();

            if (invalidStoreLocation > 0) {
                throw new XmlStoreLocationOutOfRangeException();
            }
        }
    }

    private  Map<Integer, Integer> getFrequencies(List<Integer> generatedSdmIdList) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (Integer id : generatedSdmIdList) {
            Integer currentCount = frequency.get(id);
            if (currentCount == null) {
                currentCount = 0;
            }
            frequency.put(id, ++currentCount);
        }
        return frequency;
    }

    public void loadFromGeneratedSdm(SuperDuperMarketDescriptor generatedSdm, String userName) throws AreaAlreadyExistException {
       /* engine.getItems().clear();
        engine.getStores().clear();*/
        if(engine instanceof DesktopEngine) {
            ((DesktopEngine) engine).getIdToCustomer().clear();
            generatedSdm.getSDMItems().getSDMItem().forEach(item ->  engine.getItems().put(item.getId(),generatedItemToMyItem(item)));
            //generatedSdm.getSDMStores().getSDMStore().forEach(store -> engine.getStores().put(store.getId(),generatedStoreToMyStore(store)));
        } else if(engine instanceof WebEngine) {
            tempArea = new Area(generatedSdm.getSDMZone().getName());

            generatedSdm.getSDMItems().getSDMItem().forEach(item -> {
                tempArea.addItemToArea(generatedItemToMyItem(item));
            });

            generatedSdm.getSDMStores().getSDMStore().forEach(store -> {
                tempArea.addStoreToArea(generatedStoreToMyStore(store, generatedSdm.getSDMZone().getName()));
            });
        }
    }


    private Item generatedItemToMyItem(SDMItem sdmItem) {
        Item myItem = new Item(sdmItem.getId(), sdmItem.getName(), PurchaseCategory.valueOf(sdmItem.getPurchaseCategory().toUpperCase()));
        return myItem;
    }

    private Store generatedStoreToMyStore(SDMStore sdmStore, String areaName) {
        Point location = new Point(sdmStore.getLocation().getX(), sdmStore.getLocation().getY());
        Map<Integer,Integer> itemsAndPrices = new HashMap<>();

        sdmStore.getSDMPrices().getSDMSell().forEach(item -> itemsAndPrices.put(item.getItemId(), item.getPrice()));
        Store myStore = new Store(sdmStore.getId(), sdmStore.getName(), sdmStore.getDeliveryPpk(), location, itemsAndPrices,areaName);
        myStore.setDiscountList(generatedDiscountToMyDiscount(sdmStore));
        return myStore;
    }

    private List<Discount> generatedDiscountToMyDiscount(SDMStore sdmStore) {
        List<Discount> discounts = new ArrayList<>();
        if(sdmStore.getSDMDiscounts() != null) {
            sdmStore.getSDMDiscounts().getSDMDiscount().forEach(discount -> {
                Discount myDiscount = new Discount();
                IfYouBuy ifYouBuy = new IfYouBuy();
                ThenYouGet thenYouGet = new ThenYouGet();
                List<Offer> offers = new ArrayList<>();
                myDiscount.setName(discount.getName());
                ifYouBuy.setItemId(discount.getIfYouBuy().getItemId());
                ifYouBuy.setQuantity(discount.getIfYouBuy().getQuantity());
                myDiscount.setIfYouBuy(ifYouBuy);
                discount.getThenYouGet().getSDMOffer().forEach(offer -> {
                    Offer myOffer = new Offer();
                    myOffer.setItemId(offer.getItemId());
                    myOffer.setForAdditional(offer.getForAdditional());
                    myOffer.setQuantity(offer.getQuantity());
                    offers.add(myOffer);
                });
                thenYouGet.setOffers(offers);
                thenYouGet.setOperator(generatedToMyOperator(discount.getThenYouGet().getOperator()));
                myDiscount.setThenYouGet(thenYouGet);
                discounts.add(myDiscount);
            });
        }
        return discounts;
    }

    private Operator generatedToMyOperator(String operator) {
        Operator myOperator;
        switch (operator) {
            case "ONE-OF":
                myOperator = Operator.ONE_OF;
                break;
            case "IRRELEVANT":
                myOperator = Operator.IRRELEVANT;
                break;
            case "ALL-OR-NOTHING":
                myOperator = Operator.ALL_OR_NOTHING;
                break;
            case "":
                myOperator = Operator.IRRELEVANT;
                break;
            default:
                myOperator = Operator.IRRELEVANT;
                break;
        }
        return myOperator;
    }
}