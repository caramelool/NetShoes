package br.com.netshoes.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class Preco {
    @SerializedName("actual_price")
    private String atual;
    @SerializedName("original_price")
    private String original;
    @SerializedName("payment_condition")
    private String parcelado;

    public String getAtual() {
        return atual;
    }

    public String getOriginal() {
        return original;
    }

    public String getParcelado() {
        return parcelado;
    }
}
