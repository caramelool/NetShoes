package br.com.netshoes.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class Retorno {
    @SerializedName("total")
    private int total = 0;
    @SerializedName("products")
    private ArrayList<Produto> produtos = new ArrayList<>();
    @SerializedName("url")
    private String url;

    public int getTotal() {
        return total;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public String getUrl() {
        return url;
    }
}
