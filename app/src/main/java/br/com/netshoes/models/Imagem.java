package br.com.netshoes.models;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class Imagem {
    private String large;
    private String medium;
    private String normal;
    private String small;
    private String thumb;
    private String zoom;

    public String getLarge() {
        return this.ajust(large);
    }

    public String getMedium() {
        return this.ajust(medium);
    }

    public String getNormal() {
        return this.ajust(normal);
    }

    public String getSmall() {
        return this.ajust(small);
    }

    public String getThumb() {
        return this.ajust(thumb);
    }

    public String getZoom() {
        return this.ajust(zoom);
    }

    private String ajust(String url) {
        return String.format("http://%1$s",url.substring(2));
    }
}
