package sdm.sdmElements;

public class Feedback implements Cloneable {
    private String reviewerName;
    private String orderDate;
    private Integer rate;
    private String review;

    public Feedback(String reviewerName, String orderDate, Integer rate, String review) {
        this.reviewerName = reviewerName;
        this.orderDate = orderDate;
        this.rate = rate;
        this.review = review;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Integer getRate() {
        return rate;
    }

    public String getReview() {
        return review;
    }
}
