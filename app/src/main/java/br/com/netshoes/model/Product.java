package br.com.netshoes.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class Product {
    private String name;
    private String description;
    @SerializedName("base_sku")
    private String baseSku;
    private Image image;
    private Price price;
    private Badge badge;
    private ArrayList<Characteristic> characteristics;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBaseSku() {
        return baseSku;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Price getPrice() {
        return price;
    }

    public Badge getBadge() {
        return badge;
    }

    public ArrayList<Characteristic> getCharacteristics() {
        return characteristics;
    }
}
