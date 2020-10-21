package systemInfoContainers.webContainers;

public class SingleFeedbackContainer {
    private String reviewerName;
    private String orderDate;
    private Integer rate;
    private String review;
    private Integer storeId;

    public SingleFeedbackContainer(String reviewerName, String orderDate, Integer rate, String review, Integer storeId) {
        this.reviewerName = reviewerName;
        this.orderDate = orderDate;
        this.rate = rate;
        this.review = review;
        this.storeId = storeId;
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

    public Integer getStoreId() {
        return storeId;
    }
}
