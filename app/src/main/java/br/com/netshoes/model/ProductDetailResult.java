package br.com.netshoes.model;

import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;


/**
 * Created by Caramelo on 17/12/2015.
 */
public class ProductDetailResult {

    @SerializedName("value")
    private Product product;
    @SerializedName("isSuccess")
    private boolean success;

    public Product getProduct() {
        return product;
    }

    public boolean isSuccess() {
        return success;
    }
}
