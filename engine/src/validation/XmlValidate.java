package validation;

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

public class XmlValidate implements Validator{
    private  SuperDuperMarketDescriptor generatedSdm;
    private Connector engine;
    private UserManager userManager;

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
           /* isAllCustomersIdUnique();*/
            isStoresInValidLocation();
            isAllDiscountsItemSold();
            /*Path xmlPath = Paths.get(toValidate);
            Path fileName = xmlPath.getFileName();
            engine.setXmlFileName(extractFileExtension(fileName.toString()));*/
        } catch (Exception e) {
            throw e;
        }
    }

    private String extractFileExtension(String fileName) {
        if(fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
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
/*
    private void isAllCustomersIdUnique() throws XmlSimilarCustomersIdException {
        List<Integer> generatedSdmIdList = new ArrayList<>();
        generatedSdm.getSDMCustomers().getSDMCustomer()
                .stream().forEach(customer -> generatedSdmIdList.add(customer.getId()));

        Map<Integer, Integer> idToFrequency = getFrequencies(generatedSdmIdList);
        Long duplicates = idToFrequency.entrySet().stream().filter(id -> id.getValue() > 1).count();
        if (duplicates > 0) {
            throw new XmlSimilarCustomersIdException();
        }
    }*/

    private void isStoresItemExist() throws XmlItemNotFoundException {
       Long allItemsExist = engine.getStores().values().stream().
               filter(store -> engine.getItems().keySet().containsAll(store.getItemsIdAndPrices().keySet()) == false).count();
       if (allItemsExist > 0) {
           throw new XmlItemNotFoundException();
       }
    }

    private void isAllDiscountsItemSold() throws XmlDiscountItemIsNotDefinedException, XmlDiscountItemIsNotSoldException {

        if (engine instanceof DesktopEngine || engine instanceof WebEngine) {
            Collection<Store> storesList = engine.getStores().values();
            for (Store store : storesList) {
                for (Discount discount : store.getDiscountList()) {
                    isDiscountItemsInSystem(discount, store);
                }
            }
        }
    }

    private void isDiscountItemsInSystem(Discount discount, Store store) throws XmlDiscountItemIsNotDefinedException, XmlDiscountItemIsNotSoldException {
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
    }


    private void isAllItemsSold() throws XmlItemIsNotSoldException {
        Map<Integer, Integer> frequencyId = new HashMap<>();
        Collection<Store> storesList = engine.getStores().values();
        frequencyId = engine.getItems().keySet().stream().collect(Collectors.toMap(itemId -> itemId,frequency -> 0));

        for (Store store : storesList) {
            for (Integer itemId : store.getItemsIdAndPrices().keySet()){
                Integer currentCount = frequencyId.get(itemId);
                frequencyId.put(itemId, ++currentCount);
            }
        }

        if(frequencyId.values().contains(0)){
            throw new XmlItemIsNotSoldException();
        }
        else{
            Map<Integer,Item> allItems = engine.getItems();
            for(Store store : engine.getStores().values()) {
                for(Integer itemId : store.getItemsIdAndPrices().keySet()) {
                    Item tempItem = allItems.get(itemId);
                    Item item = new Item(tempItem.getId(),tempItem.getName(),tempItem.getPurchaseCategory());
                    store.getItemsAndPrices().put(item,store.getItemsIdAndPrices().get(itemId));
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
        if(invalidStoreLocation > 0 ) {
            throw new XmlStoreLocationOutOfRangeException();
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
        } else if(engine instanceof WebEngine) {
            userManager.addAreaToUser(generatedSdm.getSDMZone().getName(), userName);
        }
        generatedSdm.getSDMItems().getSDMItem().forEach(item ->  engine.getItems().put(item.getId(),generatedItemToMyItem(item)));
        generatedSdm.getSDMStores().getSDMStore().forEach(store -> engine.getStores().put(store.getId(),generatedStoreToMyStore(store)));
     /*   if(engine instanceof DesktopEngine) {
            generatedSdm.getSDMCustomers().getSDMCustomer()
                    .forEach(customer -> ((DesktopEngine) engine).getIdToCustomer()
                            .put(customer.getId(),generatedCustomerToMyCustomer(customer)));
        }*/
    }
/*
    private Customer generatedCustomerToMyCustomer(SDMCustomer sdmCustomer) {
        return new Customer(sdmCustomer.getName(), new Point(sdmCustomer.getLocation().getX(), sdmCustomer.getLocation().getY()), sdmCustomer.getId());
    }*/

    private Item generatedItemToMyItem(SDMItem sdmItem) {
        Item myItem = new Item(sdmItem.getId(), sdmItem.getName(), PurchaseCategory.valueOf(sdmItem.getPurchaseCategory().toUpperCase()));
        return myItem;
    }

    private Store generatedStoreToMyStore(SDMStore sdmStore) {
        Point location = new Point(sdmStore.getLocation().getX(), sdmStore.getLocation().getY());
        Map<Integer,Integer> itemsAndPrices = new HashMap<>();

        sdmStore.getSDMPrices().getSDMSell().forEach(item -> itemsAndPrices.put(item.getItemId(), item.getPrice()));
        Store myStore = new Store(sdmStore.getId(), sdmStore.getName(), sdmStore.getDeliveryPpk(), location, itemsAndPrices);
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

