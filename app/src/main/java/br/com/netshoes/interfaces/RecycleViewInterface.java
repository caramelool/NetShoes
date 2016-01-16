package br.com.netshoes.interfaces;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Caramelo on 14/01/2016.
 */
public class RecycleViewInterface {
    public interface Adapter<T> {
        ArrayList<T> getItens();
        void addItens(ArrayList<T> itens);
        T getItem(int position);
        void addItem(T item);
        void removeItem(T item);
        void clearItens();
    }
    public interface OnItemClickListener {
        void OnItemClick(RecyclerView.Adapter adapter, int position);
    }
}
