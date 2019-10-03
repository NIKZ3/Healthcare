package com.t1;

public class revmodel {
    private String rating,review;

    public revmodel(String rating,String review)
    {
        this.rating=rating;
        this.review=review;
    }
    public String getrating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getreview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
