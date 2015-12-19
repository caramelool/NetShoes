package br.com.netshoes;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

import br.com.netshoes.adapters.AdapterProduto;
import br.com.netshoes.interfaces.ITask;
import br.com.netshoes.models.Produto;
import br.com.netshoes.models.Retorno;
import br.com.netshoes.tasks.TaskProdutos;
import br.com.netshoes.utils.EndlessScroll;
import br.com.netshoes.utils.Urls;

public class ActivityMain extends AppCompatActivity
    implements ITask<Retorno>, EndlessScroll.Listener, AdapterView.OnItemClickListener,
        View.OnClickListener{

    private Toolbar toolbar;
    private TextView txtTotal;
    private GridView gridProdutos;
    private ProgressWheel progress;
    private FrameLayout viewLoading;
    private LinearLayout viewEmpty;

    private Retorno mRetorno;
    private ArrayList<Produto> mArrProdutos;
    private AdapterProduto mAdapterProduto;
    private EndlessScroll mEndlessScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.setContentView(R.layout.activity_main);

            //Inicializa controles
            this.init();

            this.atualizaDados();
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
    }

    void init() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        txtTotal = (TextView) this.findViewById(R.id.txtTotal);
        gridProdutos = (GridView) this.findViewById(R.id.gridProdutos);
        progress = (ProgressWheel) this.findViewById(R.id.progress);
        viewLoading = (FrameLayout) this.findViewById(R.id.viewLoading);
        viewEmpty = (LinearLayout) this.findViewById(R.id.viewEmpty);

        toolbar.setTitle(R.string.app_name);
        this.setSupportActionBar(toolbar);

        mArrProdutos = new ArrayList<>();
        mAdapterProduto = new AdapterProduto(this, mArrProdutos);
        gridProdutos.setAdapter(mAdapterProduto);

        mEndlessScroll = new EndlessScroll(this);
        gridProdutos.setOnScrollListener(mEndlessScroll);
        gridProdutos.setOnItemClickListener(this);
        viewEmpty.setOnClickListener(this);
    }

    /**
     * Atualiza lista
     */
    private void atualizaDados() {

        viewEmpty.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        //Executa task que comunica com a API e dispara, caso não aconteça nenhum erro,
        //o metodo onTaskDone
        new TaskProdutos(this, Urls.DEPARTAMENTO_PRINCIPAL).execute();
    }

    @Override
    public void onTaskDone(AsyncTask task, Retorno retorno) {
        try {

            viewEmpty.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
            viewLoading.setVisibility(View.GONE);

            //Verifica se possui algum retorno
            if (!retorno.getProdutos().isEmpty()) {
                //Guarda o retorno
                mRetorno = retorno;

                //Atualiza lista
                mArrProdutos.addAll(mRetorno.getProdutos());
                mAdapterProduto.notifyDataSetChanged();

                //Atualiza texto que informa o total de registros
                txtTotal.setVisibility(View.VISIBLE);
                txtTotal.setText(
                        String.format("Total de produtos encontrados: %1$s de %2$s",
                                mArrProdutos.size(), mRetorno.getTotal())
                );
            }
            else {
                //Verifica se já tinha encontrado algum produto
                if (mArrProdutos.isEmpty()) {
                    Toast.makeText(this, "Nenhum produto encontrado", Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
    }

    @Override
    public void onTaskErro(AsyncTask task) {
        //Limpa Lista
        mArrProdutos.clear();
        mAdapterProduto.notifyDataSetChanged();

        //Reseta o infit scroll+
        mEndlessScroll.reset();

        viewEmpty.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        viewLoading.setVisibility(View.GONE);
        txtTotal.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMore(int page) {
       try {
           //Proxima pagina
           viewLoading.setVisibility(View.VISIBLE);
           new TaskProdutos(this, Urls.DEPARTAMENTO+mRetorno.getUrl()).execute();
       }
       catch (Exception err) {
           Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
       }
    }

    @Override
    public void onClick(View view) {
        if (view == viewEmpty) {

            this.atualizaDados();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        Produto produto;

        try {
            if (parent == gridProdutos) {

                //Obtem produto selecionado
                produto = mAdapterProduto.getItem(position);

                //Monta intent
                intent = new Intent(this, ActivityProdutoDetalhe.class);
                intent.putExtra(Produto.class.getSimpleName(), new Gson().toJson(produto));

                this.startActivity(intent);
            }
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
    }
}
