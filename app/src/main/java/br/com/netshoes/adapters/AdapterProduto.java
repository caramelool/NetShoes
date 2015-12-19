package br.com.netshoes.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.netshoes.R;
import br.com.netshoes.models.Produto;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class AdapterProduto extends BaseAdapter {

    private Context mContext;
    private ArrayList<Produto> mArrProdutos;

    public AdapterProduto(Context mContext, @NonNull ArrayList<Produto> mArrProdutos) {
        this.mContext = mContext;
        this.mArrProdutos = mArrProdutos;
    }

    @Override
    public int getCount() {
        return mArrProdutos.size();
    }

    @Override
    public Produto getItem(int position) {
        return mArrProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Produto produto;

        try {
            if (convertView == null) {
                //Infla o convert view com a view da linha
                convertView = View.inflate(mContext, R.layout.lst_produto, null);

                //Instancia a classe viewHolder
                viewHolder = new ViewHolder(convertView);

                //Guarda na tag o viewHolder para obter nas proximas chamadas
                convertView.setTag(viewHolder);
            }
            else {
                //Obtem viewHolder
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //Obtem produto
            produto = this.getItem(position);

            //Utiliza a biblioteca picasso para obtem a imagem
            Picasso.with(mContext)
                    .load(produto.getImagem().getLarge())
                    .into(viewHolder.imgFoto);

            //Verifica se possui desconto
            if (produto.getDesconto() != null) {
                viewHolder.txtDesconto.setVisibility(View.VISIBLE);
                viewHolder.txtDesconto.setText(produto.getDesconto().getValor());
            }
            else
                viewHolder.txtDesconto.setVisibility(View.GONE);

            //Preenche campos
            viewHolder.txtNome.setText(produto.getNome());
            viewHolder.txtPrecoOriginal.setText(produto.getPreco().getOriginal());
            viewHolder.txtPrecoAtual.setText(produto.getPreco().getAtual());

            //Seta strikethrough
            viewHolder.txtPrecoOriginal.setPaintFlags(
                    viewHolder.txtPrecoOriginal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }

        return convertView;
    }

    class ViewHolder {
        public TextView txtDesconto;
        public ImageView imgFoto;
        public TextView txtNome;
        public TextView txtPrecoOriginal;
        public TextView txtPrecoAtual;

        public ViewHolder(View convertView) {
            txtDesconto = (TextView) convertView.findViewById(R.id.txtDesconto);
            imgFoto = (ImageView) convertView.findViewById(R.id.imgFoto);
            txtNome = (TextView) convertView.findViewById(R.id.txtNome);
            txtPrecoOriginal = (TextView) convertView.findViewById(R.id.txtPrecoOriginal);
            txtPrecoAtual = (TextView) convertView.findViewById(R.id.txtPrecoAtual);
        }
    }
}
