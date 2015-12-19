package br.com.netshoes.utils;

/**
 * Created by Caramelo on 18/12/2015.
 */
public enum DetalheTipo {
    Imagem(0), NomeProduto(1), Titulo(2), Descricao(3);

    int id;

    DetalheTipo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
