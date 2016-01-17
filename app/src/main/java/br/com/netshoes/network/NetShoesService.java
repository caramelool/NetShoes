package br.com.netshoes.network;

import br.com.netshoes.model.ProductDetailResult;
import br.com.netshoes.model.ProductsResult;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Caramelo on 14/01/2016.
 */
public class NetShoesService {

    private static Retrofit mRetrofit;

    public NetShoesService() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://www.netshoes.com.br")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public Interface getService() {
        return mRetrofit.create(Interface.class);
    }

    public interface Interface {
        @Headers({
                "User-Agent: Netshoes App",
                "X-Requested-With: XMLHttpRequest"
        })
        @GET("/{url}")
        Call<ProductsResult> listProducts(@Path("url") String url);


        @Headers({
                "User-Agent: Netshoes App",
                "X-Requested-With: XMLHttpRequest"
        })
        @GET("/produto/{base_sku}")
        Call<ProductDetailResult> productDetail(@Path("base_sku") String baseSku);
    }
}
