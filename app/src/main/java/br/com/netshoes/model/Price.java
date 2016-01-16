package br.com.netshoes.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class Price {
    @SerializedName("actual_price")
    private String actualPrice;
    @SerializedName("original_price")
    private String originalPrice;
    @SerializedName("payment_condition")
    private String paymentCondition;

    public String getActualPrice() {
        return actualPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public String getPaymentCondition() {
        return paymentCondition;
    }
}
