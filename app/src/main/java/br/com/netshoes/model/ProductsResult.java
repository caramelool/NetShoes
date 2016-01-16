package br.com.netshoes.model;

import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Created by Caramelo on 17/12/2015.
 */
public class ProductsResult {

    @SerializedName("value")
    private Value value;
    private String message;
    @SerializedName("isSuccess")
    private boolean success;

    public Value getValue() {
        return value;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class Value {
        private String url;
        private ArrayList<Product> products;
        private int total;

        public String getUrl() {
            try {
                return URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException err) {
                return "";
            }
        }

        public ArrayList<Product> getProducts() {
            return products;
        }

        public int getTotal() {
            return total;
        }
    }

}
