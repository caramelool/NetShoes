package br.com.netshoes.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class Caracteristica {
    @SerializedName("name")
    private String nome;
    @SerializedName("value")
    private String valor;

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }
}
