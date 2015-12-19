package br.com.netshoes.models;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class Detalhe {

    public enum Tipo {
        Imagem(0), NomeProduto(1), Titulo(2), Descricao(3);

        int id;

        Tipo(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private Tipo tipo;
    private Object object;

    public Detalhe(Tipo tipo, Object object) {
        this.tipo = tipo;
        this.object = object;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Object getObject() {
        return object;
    }
}
