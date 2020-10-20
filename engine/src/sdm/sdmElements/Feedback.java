package sdm.sdmElements;

public class Feedback {
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
