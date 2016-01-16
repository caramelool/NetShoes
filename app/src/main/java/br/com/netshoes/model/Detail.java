package br.com.netshoes.model;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class Detail {

    public enum Type {
        Image(0), ProductName(1), Title(2), Description(3);

        int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private Type type;
    private Object object;

    public Detail(Type type, Object object) {
        this.type = type;
        this.object = object;
    }

    public Type getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}
