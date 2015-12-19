package br.com.netshoes.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import br.com.netshoes.ActivityProdutoDetalhe;
import br.com.netshoes.models.Produto;
import br.com.netshoes.models.Retorno;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class TaskProdutoDetalhe extends AsyncTask<Void, Void, Boolean> {

    private ActivityProdutoDetalhe mActivityProdutoDetalhe;
    private String mUrl;
    private Produto mProduto;

    public TaskProdutoDetalhe(ActivityProdutoDetalhe activityProdutoDetalhe, String url) {
        this.mActivityProdutoDetalhe = activityProdutoDetalhe;
        this.mUrl = url;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        OkHttpClient client;
        Request request;
        Response response;
        JSONObject json;

        try {
            client = new OkHttpClient();

            request = new Request.Builder()
                    .url(mUrl)
                    .addHeader("User-Agent", "Netshoes App")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .build();

            response = client.newCall(request).execute();
            String e = response.body().string();
            json = new JSONObject(e);

            //Verifica se deu certo a requisição
            if (json.getBoolean("isSuccess")) {

                //Deserializa string json para a classe retorno
                mProduto = new Gson().fromJson(json.getJSONObject("value").toString(), Produto.class);

                return true;
            }
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        try {
            if (aBoolean)
                mActivityProdutoDetalhe.onTaskDone(this, mProduto);
            else
                mActivityProdutoDetalhe.onTaskErro(this);
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
    }
}
