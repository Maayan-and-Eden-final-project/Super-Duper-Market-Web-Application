//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.06 at 10:11:52 AM IDT 
//


package sdm.generated2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sdm.generated2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeliveryPpk_QNAME = new QName("", "delivery-ppk");
    private final static QName _Name_QNAME = new QName("", "name");
    private final static QName _PurchaseCategory_QNAME = new QName("", "purchase-category");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sdm.generated2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SDMDiscounts }
     * 
     */
    public SDMDiscounts createSDMDiscounts() {
        return new SDMDiscounts();
    }

    /**
     * Create an instance of {@link SDMDiscount }
     * 
     */
    public SDMDiscount createSDMDiscount() {
        return new SDMDiscount();
    }

    /**
     * Create an instance of {@link IfYouBuy }
     * 
     */
    public IfYouBuy createIfYouBuy() {
        return new IfYouBuy();
    }

    /**
     * Create an instance of {@link ThenYouGet }
     * 
     */
    public ThenYouGet createThenYouGet() {
        return new ThenYouGet();
    }

    /**
     * Create an instance of {@link SDMOffer }
     * 
     */
    public SDMOffer createSDMOffer() {
        return new SDMOffer();
    }

    /**
     * Create an instance of {@link SDMStore }
     * 
     */
    public SDMStore createSDMStore() {
        return new SDMStore();
    }

    /**
     * Create an instance of {@link Location }
     * 
     */
    public Location createLocation() {
        return new Location();
    }

    /**
     * Create an instance of {@link SDMPrices }
     * 
     */
    public SDMPrices createSDMPrices() {
        return new SDMPrices();
    }

    /**
     * Create an instance of {@link SDMSell }
     * 
     */
    public SDMSell createSDMSell() {
        return new SDMSell();
    }

    /**
     * Create an instance of {@link SDMStores }
     * 
     */
    public SDMStores createSDMStores() {
        return new SDMStores();
    }

    /**
     * Create an instance of {@link SDMItems }
     * 
     */
    public SDMItems createSDMItems() {
        return new SDMItems();
    }

    /**
     * Create an instance of {@link SDMItem }
     * 
     */
    public SDMItem createSDMItem() {
        return new SDMItem();
    }

    /**
     * Create an instance of {@link SDMCustomers }
     * 
     */
    public SDMCustomers createSDMCustomers() {
        return new SDMCustomers();
    }

    /**
     * Create an instance of {@link SDMCustomer }
     * 
     */
    public SDMCustomer createSDMCustomer() {
        return new SDMCustomer();
    }

    /**
     * Create an instance of {@link SuperDuperMarketDescriptor }
     * 
     */
    public SuperDuperMarketDescriptor createSuperDuperMarketDescriptor() {
        return new SuperDuperMarketDescriptor();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "delivery-ppk")
    public JAXBElement<Integer> createDeliveryPpk(Integer value) {
        return new JAXBElement<Integer>(_DeliveryPpk_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "purchase-category")
    public JAXBElement<String> createPurchaseCategory(String value) {
        return new JAXBElement<String>(_PurchaseCategory_QNAME, String.class, null, value);
    }

}
