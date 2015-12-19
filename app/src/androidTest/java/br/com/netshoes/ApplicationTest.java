package br.com.netshoes;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.MoreAsserts;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import br.com.netshoes.models.Retorno;
import br.com.netshoes.utils.Urls;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public Application mApplication;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mApplication = this.getApplication();
    }

    public void testProdutosValidos() throws Exception {
        OkHttpClient client;
        Request request;
        Response response;
        JSONObject json;
        Retorno retorno;

        client = new OkHttpClient();

        request = new Request.Builder()
                .url(Urls.DEPARTAMENTO_PRINCIPAL)
                .addHeader("User-Agent", "Netshoes App")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .build();

        response = client.newCall(request).execute();
        json = new JSONObject(response.body().string());

        //Verifica se a operação deu certo
        assertEquals("Operação sem sucesso", true, json.getBoolean("isSuccess"));

        //Obtem retorno
        retorno = new Gson().fromJson(json.getJSONObject("value").toString(), Retorno.class);

        //Verifica se foi encontrado algum produto
        MoreAsserts.assertNotEmpty("Nenhum produto encontrado", retorno.getProdutos());
    }
}