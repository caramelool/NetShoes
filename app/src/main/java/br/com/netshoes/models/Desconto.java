package br.com.netshoes.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class Desconto {
    @SerializedName("strength")
    private String tipo;
    @SerializedName("value")
    private String valor;

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }
}
