package br.com.netshoes.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class Produto {
    @SerializedName("name")
    private String nome;
    @SerializedName("description")
    private String descricao;
    @SerializedName("url")
    private String url;
    @SerializedName("image")
    private Imagem imagem;
    @SerializedName("price")
    private Preco preco;
    @SerializedName("badge")
    private Desconto desconto;
    @SerializedName("characteristics")
    private ArrayList<Caracteristica> caracteristicas;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUrl() {
        return url;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public Preco getPreco() {
        return preco;
    }

    public Desconto getDesconto() {
        return desconto;
    }

    public ArrayList<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }
}
