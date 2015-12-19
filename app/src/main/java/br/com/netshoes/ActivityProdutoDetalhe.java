package br.com.netshoes;

import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

import br.com.netshoes.adapters.AdapterProdutoDetalhe;
import br.com.netshoes.interfaces.ITask;
import br.com.netshoes.models.Caracteristica;
import br.com.netshoes.models.Detalhe;
import br.com.netshoes.models.Produto;
import br.com.netshoes.tasks.TaskProdutoDetalhe;
import br.com.netshoes.utils.Urls;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class ActivityProdutoDetalhe extends AppCompatActivity
    implements ITask<Produto>, View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView rclDetalhe;
    private TextView txtDesconto;
    private TextView txtPrecoOriginal;
    private TextView txtPrecoAtual;
    private ProgressWheel progress;
    private LinearLayout viewEmpty;
    private FloatingActionButton fabCarrinho;

    private Produto mProduto;
    private ArrayList<Detalhe> mArrDetalhes;
    private AdapterProdutoDetalhe mAdapterProdutoDetalhe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.setContentView(R.layout.activity_produto_detalhe);

            this.init();

            this.atualizaDados();
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
    }

    void init() {

        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        rclDetalhe = (RecyclerView) this.findViewById(R.id.rclDetalhe);
        txtDesconto = (TextView) this.findViewById(R.id.txtDesconto);
        txtPrecoOriginal = (TextView) this.findViewById(R.id.txtPrecoOriginal);
        txtPrecoAtual = (TextView) this.findViewById(R.id.txtPrecoAtual);
        progress = (ProgressWheel) this.findViewById(R.id.progress);
        viewEmpty = (LinearLayout) this.findViewById(R.id.viewEmpty);
        fabCarrinho = (FloatingActionButton) this.findViewById(R.id.fabCarrinho);

        //Obtem produto
        mProduto = new Gson().fromJson(this.getIntent().getExtras()
                .getString(Produto.class.getSimpleName()), Produto.class);

        toolbar.setTitle(R.string.detalhe_produto);
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rclDetalhe.setHasFixedSize(true);
        rclDetalhe.setLayoutManager(new LinearLayoutManager(this));

        mArrDetalhes = new ArrayList<>();
        mAdapterProdutoDetalhe = new AdapterProdutoDetalhe(this, mArrDetalhes);
        rclDetalhe.setAdapter(mAdapterProdutoDetalhe);

        viewEmpty.setOnClickListener(this);
        fabCarrinho.setOnClickListener(this);

        //Preenche dados referente ao preço do produto
        this.preencheDadosPreco();
    }

    /**
     * Atualiza informações do produto
     */
    private void atualizaDados() {

        progress.setVisibility(View.VISIBLE);
        viewEmpty.setVisibility(View.GONE);

        new TaskProdutoDetalhe(this, Urls.NETSHOES+mProduto.getUrl())
                .execute();
    }

    /**
     * Preenche dados refente ao preço do produto
     */
    private void preencheDadosPreco() {
        //Verifica se possui desconto
        if (mProduto.getDesconto() != null) {
            txtDesconto.setVisibility(View.VISIBLE);
            txtDesconto.setText(mProduto.getDesconto().getValor());
        }
        else
            txtDesconto.setVisibility(View.GONE);

        //Preenche campos
        txtPrecoOriginal.setText(mProduto.getPreco().getOriginal());
        txtPrecoAtual.setText(mProduto.getPreco().getAtual().replace("R$", ""));

        //Seta strikethrough
        txtPrecoOriginal.setPaintFlags(txtPrecoOriginal.getPaintFlags()
                | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void onTaskDone(AsyncTask task, Produto produtoAtualizado) {
        progress.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.GONE);

        //O campo imagem não vem preenchido então seto a imagem do produto desatualizado
        produtoAtualizado.setImagem(mProduto.getImagem());
        mProduto = produtoAtualizado;

        mArrDetalhes.clear();

        //Adiciona imagem
        mArrDetalhes.add(new Detalhe(Detalhe.Tipo.Imagem, produtoAtualizado.getImagem()));

        //Adiciona nome
        mArrDetalhes.add(new Detalhe(Detalhe.Tipo.NomeProduto, produtoAtualizado.getNome()));

        //Adiciona descrição
        mArrDetalhes.add(new Detalhe(Detalhe.Tipo.Titulo, "Descrição"));
        mArrDetalhes.add(new Detalhe(Detalhe.Tipo.Descricao, produtoAtualizado.getDescricao()));

        //Le caracteristicas
        for (Caracteristica caracteristica : produtoAtualizado.getCaracteristicas()) {
            //Adiciona todas as caracteristicas
            mArrDetalhes.add(new Detalhe(Detalhe.Tipo.Titulo, caracteristica.getNome()));
            mArrDetalhes.add(new Detalhe(Detalhe.Tipo.Descricao, caracteristica.getValor()));
        }

        //Atualiza dados referente ao preço
        this.preencheDadosPreco();

        //Atualiza lista
        mAdapterProdutoDetalhe.notifyDataSetChanged();
    }

    @Override
    public void onTaskErro(AsyncTask task) {
        progress.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view == viewEmpty) {
            this.atualizaDados();
        }
        else if (view == fabCarrinho) {
            Toast.makeText(this, "Produto adicionado no carrinho!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;

        id = item.getItemId();

        if (id == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}
