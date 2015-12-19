package br.com.netshoes.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.netshoes.R;
import br.com.netshoes.models.Detalhe;
import br.com.netshoes.models.Imagem;
import br.com.netshoes.utils.DetalheTipo;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class AdapterProdutoDetalhe extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Detalhe> mArrDetalhes;

    public AdapterProdutoDetalhe(Context context, ArrayList<Detalhe> arrDetalhes) {
        this.mContext = context;
        this.mArrDetalhes = arrDetalhes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == DetalheTipo.Imagem.getId()) {
            itemView = View.inflate(mContext, R.layout.lst_detalhe_imagem, null);
            return new ViewHolderImagem(itemView);
        }
        else if (viewType == DetalheTipo.NomeProduto.getId()) {
            itemView = View.inflate(mContext, R.layout.lst_detalhe_nome, null);
            return new ViewHolderNomeProduto(itemView);
        }
        else if (viewType == DetalheTipo.Titulo.getId()) {
            itemView = View.inflate(mContext, R.layout.lst_detalhe_titulo, null);
            return new ViewHolderTitulo(itemView);
        }
        else {
            itemView = View.inflate(mContext, R.layout.lst_detalhe_descricao, null);
            return new ViewHolderDetalhe(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Detalhe detalhe = this.getItem(position);

        if (holder instanceof ViewHolderImagem)
            ((ViewHolderImagem)holder).atualizar(detalhe);
        else if (holder instanceof ViewHolderNomeProduto)
            ((ViewHolderNomeProduto)holder).atualizar(detalhe);
        else if (holder instanceof ViewHolderTitulo)
            ((ViewHolderTitulo)holder).atualizar(detalhe);
        else
            ((ViewHolderDetalhe)holder).atualizar(detalhe);
    }

    @Override
    public int getItemViewType(int position) {
        Detalhe detalhe = this.getItem(position);
        return detalhe.getTipo().getId();
    }

    @Override
    public int getItemCount() {
        return mArrDetalhes.size();
    }

    public Detalhe getItem(int position) {
        return mArrDetalhes.get(position);
    }

    /** Views Holders **/

    class ViewHolderImagem extends RecyclerView.ViewHolder {

        private ImageView imgFoto;
        private Imagem imagem;

        public ViewHolderImagem(View itemView) {
            super(itemView);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
        }

        public void atualizar(Detalhe detalhe) {
            imagem = (Imagem) detalhe.getObject();
            Picasso.with(mContext)
                    .load(imagem.getLarge())
                    .into(imgFoto);
        }
    }

    class ViewHolderNomeProduto extends RecyclerView.ViewHolder {

        private TextView txtNome;
        private String nome;

        public ViewHolderNomeProduto(View itemView) {
            super(itemView);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
        }

        public void atualizar(Detalhe detalhe) {
            try {
                nome = (String) detalhe.getObject();
                txtNome.setText(nome);
            }
            catch (Exception err) {
                txtNome.setText("");
            }
        }
    }

    class ViewHolderTitulo extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private String texto;

        public ViewHolderTitulo(View itemView) {
            super(itemView);
            txtTitulo = (TextView) itemView.findViewById(R.id.txtTitulo);
        }

        public void atualizar(Detalhe detalhe) {
            try {
                texto = (String) detalhe.getObject();
                txtTitulo.setText(texto);
            }
            catch (Exception err) {
                txtTitulo.setText("");
            }
        }
    }

    class ViewHolderDetalhe extends RecyclerView.ViewHolder {

        private TextView txtDescricao;
        private String texto;

        public ViewHolderDetalhe(View itemView) {
            super(itemView);
            txtDescricao = (TextView) itemView.findViewById(R.id.txtDescricao);
        }

        public void atualizar(Detalhe detalhe) {
            try {
                texto = (String) detalhe.getObject();
                txtDescricao.setText(texto);
            }
            catch (Exception err) {
                txtDescricao.setText("");
            }
        }
    }
}
