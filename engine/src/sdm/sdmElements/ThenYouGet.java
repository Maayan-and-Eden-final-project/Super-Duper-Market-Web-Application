package sdm.sdmElements;
import sdm.enums.Operator;

import java.util.ArrayList;
import java.util.List;


public class ThenYouGet implements Cloneable {

    private List<Offer> offers;
    private Operator operator;

    @Override
    public ThenYouGet clone(){
        ThenYouGet thenYouGet = new ThenYouGet();
        try {
            List<Offer> offerList = new ArrayList<>();

            for (Offer offer : offers) {
                Offer tempOffer = offer.clone();
                offerList.add(tempOffer);
            }
            thenYouGet.setOffers(offerList);
            thenYouGet.setOperator(this.operator);

        } catch (Exception e) {
            thenYouGet = null;
        }
        return thenYouGet;
    }

    public ThenYouGet() {
        this.offers = new ArrayList<>();
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
