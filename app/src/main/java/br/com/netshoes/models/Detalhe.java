package br.com.netshoes.models;

import br.com.netshoes.utils.DetalheTipo;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class Detalhe {
    private DetalheTipo tipo;
    private Object object;

    public Detalhe(DetalheTipo tipo, Object object) {
        this.tipo = tipo;
        this.object = object;
    }

    public DetalheTipo getTipo() {
        return tipo;
    }

    public Object getObject() {
        return object;
    }
}
