package br.com.netshoes.view.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

import br.com.netshoes.R;
import br.com.netshoes.view.acitivity.BaseActivity;
import br.com.netshoes.view.adapter.ProductDetailAdapter;
import br.com.netshoes.model.Characteristic;
import br.com.netshoes.model.Detail;
import br.com.netshoes.model.Product;
import br.com.netshoes.model.ProductDetailResult;
import br.com.netshoes.network.NetShoesService;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Caramelo on 15/01/2016.
 */
public class ProductDetailFragment extends BaseFragment {

    @Bind(R.id.rvDetail)
    RecyclerView rvDetail;
    @Bind(R.id.tvBadge)
    TextView tvBadge;
    @Bind(R.id.tvOriginalPrice)
    TextView tvOriginalPrice;
    @Bind(R.id.tvActualPrice)
    TextView tvActualPrice;
    @Bind(R.id.progressWheel)
    ProgressWheel progressWheel;
    @Bind(R.id.viewEmpty)
    LinearLayout viewEmpty;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private Product mProduct;
    private ProductDetailAdapter mProductDetailAdapter;

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public void onResume() {
        super.onResume();

        BaseActivity baseActivity = ((BaseActivity)getActivity());
        baseActivity.applyToolBarTitle(mProduct.getName());
        baseActivity.applyHomeButton(true);

    }

    @Override
    protected void init(Bundle savedInstanceState) {

        deserializableProduct();

        rvDetail.setHasFixedSize(true);
        rvDetail.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mProductDetailAdapter = new ProductDetailAdapter(this.getContext());
        rvDetail.setAdapter(mProductDetailAdapter);

        updatePrice();

        getDetail();
    }

    public void deserializableProduct() {
        mProduct = (Product) getArguments().getSerializable(Product.class.getSimpleName());
        if (mProduct == null)
            throw new NullPointerException("Product not found!!!");
    }

    public void getDetail() {
        NetShoesService.Interface service = new NetShoesService().getService();
        Call<ProductDetailResult> serviceCall = service.productDetail(mProduct.getBaseSku());
        serviceCall.enqueue(new Callback<ProductDetailResult>() {
            @Override
            public void onResponse(Response<ProductDetailResult> response) {
                ProductDetailResult result = response.body();
                if (result != null && result.isSuccess()) {

                    makeDetail(result.getProduct());

                    if (mProductDetailAdapter.getItemCount() == 0)
                        showViewEmpty();
                    else
                        hideViewEmpty();
                } else {
                    showViewEmpty();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showViewEmpty();
            }
        });
    }

    private void makeDetail(Product product) {
        product.setImage(mProduct.getImage());
        mProduct = product;

        ArrayList<Detail> array = new ArrayList<>();

        array.add(new Detail(Detail.Type.Image, mProduct.getImage()));
        array.add(new Detail(Detail.Type.ProductName, mProduct.getName()));
        array.add(new Detail(Detail.Type.Title, "Descrição"));
        array.add(new Detail(Detail.Type.Description, mProduct.getDescription()));

        for (Characteristic characteristic : mProduct.getCharacteristics()) {
            array.add(new Detail(Detail.Type.Title, characteristic.getName()));
            array.add(new Detail(Detail.Type.Description, characteristic.getValue()));
        }

        mProductDetailAdapter.addItens(array);

        updatePrice();
    }

    private void updatePrice() {
        if (mProduct.getBadge() != null) {
            tvBadge.setVisibility(View.VISIBLE);
            tvBadge.setText(mProduct.getBadge().getValue());
        }
        else
            tvBadge.setVisibility(View.GONE);

        tvOriginalPrice.setText(mProduct.getPrice().getOriginalPrice());
        tvActualPrice.setText(mProduct.getPrice().getActualPrice().replace("R$", ""));

        tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags()
                | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @OnClick(R.id.viewEmpty)
    public void onViewEmptyClick() {
        getDetail();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        Toast.makeText(this.getContext(), "Produto adicionado no carrinho!!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showViewEmpty() {
        progressWheel.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.VISIBLE);
    }

    public void hideViewEmpty() {
        progressWheel.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.GONE);
    }

    public void showLoading() {
        progressWheel.setVisibility(View.VISIBLE);
        viewEmpty.setVisibility(View.GONE);
    }
}
