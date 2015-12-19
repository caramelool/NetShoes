package br.com.netshoes.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import br.com.netshoes.ActivityMain;
import br.com.netshoes.models.Retorno;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class TaskProdutos extends AsyncTask<Void, Void, Boolean> {

    private ActivityMain mActivityMain;
    private String mUrl;
    private Retorno mRetorno;

    public TaskProdutos(ActivityMain activityMain, String url) {
        this.mActivityMain = activityMain;
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
            json = new JSONObject(response.body().string());

            //Verifica se deu certo a requisição
            if (json.getBoolean("isSuccess")) {

                //Deserializa string json para a classe retorno
                mRetorno = new Gson().fromJson(json.getJSONObject("value").toString(), Retorno.class);

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
                mActivityMain.onTaskDone(this, mRetorno);
            else
                mActivityMain.onTaskErro(this);
        }
        catch (Exception err) {
            Log.e(this.getClass().getSimpleName(), err.getMessage(), err);
        }
    }
}
