package br.com.netshoes.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

import br.com.netshoes.R;
import br.com.netshoes.view.acitivity.BaseActivity;
import br.com.netshoes.view.adapter.ProductListAdapter;
import br.com.netshoes.interfaces.EndlessRecyclerView;
import br.com.netshoes.interfaces.RecycleViewInterface;
import br.com.netshoes.model.ProductsResult;
import br.com.netshoes.model.Product;
import br.com.netshoes.network.NetShoesService;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProductListFragment extends BaseFragment
    implements RecycleViewInterface.OnItemClickListener{

    @Bind(R.id.tvTotal)
    TextView tvTotal;
    @Bind(R.id.rvProducts)
    RecyclerView rvProducts;
    @Bind(R.id.progressWheel)
    ProgressWheel progressWheel;
    @Bind(R.id.viewLoading)
    FrameLayout viewLoading;
    @Bind(R.id.viewEmpty)
    LinearLayout viewEmpty;

    private ProductsResult mProductsResult;
    private ProductListAdapter mProductListAdapter;
    private EndlessRecyclerView mEndlessRecyclerView;

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_product_list;
    }

    @Override
    public void onResume() {
        super.onResume();

        BaseActivity baseActivity = ((BaseActivity)getActivity());
        baseActivity.applyToolBarTitle(getString(R.string.app_name));
        baseActivity.applyHomeButton(false);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mProductListAdapter = new ProductListAdapter(this);

        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        rvProducts.setAdapter(mProductListAdapter);

        mEndlessRecyclerView = new EndlessRecyclerView() {
            @Override
            public void onLoad(int page) {
                String url = "departamento" + mProductsResult.getValue().getUrl();
                if (!url.isEmpty()) {
                    showLoadingBottom();
                    getProducts(url);
                }
            }

            @Override
            public void reset() {
                super.reset();
                mProductListAdapter.clearItens();
            }
        };
        rvProducts.addOnScrollListener(mEndlessRecyclerView);

        getProducts();
    }

    private Callback<ProductsResult> mCallback = new Callback<ProductsResult>() {
        @Override
        public void onResponse(Response<ProductsResult> response) {
            mProductsResult = response.body();
            if (mProductsResult != null && mProductsResult.isSuccess()) {
                ArrayList<Product> products = mProductsResult.getValue().getProducts();
                mProductListAdapter.addItens(products);
                updateProductCount();
                if (mProductListAdapter.getItemCount() == 0)
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
    };

    private void getProducts(String url) {
        NetShoesService.Interface service = new NetShoesService().getService();
        Call<ProductsResult> serviceCall = service.listProducts(url);
        serviceCall.enqueue(mCallback);
    }
    private void getProducts() {
        mProductListAdapter.clearItens();
        showLoading();

        String url = "departamento?Nr=OR(product.productType.displayName:Tênis,product.productType.displayName:Tênis)";
        getProducts(url);
    }

    @OnClick(R.id.viewEmpty)
    public void onViewEmptyClick() {
        getProducts();
    }

    @Override
    public void OnItemClick(RecyclerView.Adapter adapter, int position) {
        if (adapter == mProductListAdapter) {
            Product product = mProductListAdapter.getItem(position);
            ProductDetailFragment fragment = new ProductDetailFragment();

            Bundle bundle = new Bundle();
            bundle.putString(Product.class.getSimpleName(), new Gson().toJson(product));

            ((BaseActivity)getActivity()).attachFragment(fragment, bundle, R.id.frame_container);
        }
    }

    public void updateProductCount() {
        int current = mProductListAdapter.getItemCount();
        int total = mProductsResult.getValue().getTotal();
        tvTotal.setVisibility(View.VISIBLE);
        tvTotal.setText(
                String.format("Total de produtos encontrados: %1$s de %2$s",
                        current, total)
        );
    }

    public void showViewEmpty() {
        progressWheel.setVisibility(View.GONE);
        viewLoading.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.VISIBLE);
        mEndlessRecyclerView.reset();
    }

    public void hideViewEmpty() {
        progressWheel.setVisibility(View.GONE);
        viewLoading.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.GONE);
    }

    public void showLoading() {
        progressWheel.setVisibility(View.VISIBLE);
        viewLoading.setVisibility(View.GONE);
        viewEmpty.setVisibility(View.GONE);
    }

    public void showLoadingBottom() {
        progressWheel.setVisibility(View.GONE);
        viewLoading.setVisibility(View.VISIBLE);
        viewEmpty.setVisibility(View.GONE);
    }
}
