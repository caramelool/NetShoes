package br.com.netshoes.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.netshoes.R;
import br.com.netshoes.interfaces.RecycleViewInterface;
import br.com.netshoes.model.Product;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Caramelo on 17/12/2015.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.VH>
    implements RecycleViewInterface.Adapter<Product> {

    private Fragment mFragment;
    private Context mContext;
    private ArrayList<Product> mArrProducts;

    public ProductListAdapter(Fragment fragment) {
        this.mFragment = fragment;
        this.mContext = fragment.getContext();
        this.mArrProducts = new ArrayList<>();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.fill(position);
    }

    @Override
    public int getItemCount() {
        return mArrProducts.size();
    }

    @Override
    public ArrayList<Product> getItens() {
        return mArrProducts;
    }

    @Override
    public void addItens(ArrayList<Product> itens) {
        mArrProducts.addAll(itens);
        notifyDataSetChanged();
    }

    @Override
    public Product getItem(int position) {
        return mArrProducts.get(position);
    }

    @Override
    public void addItem(Product item) {
        mArrProducts.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(Product item) {
        mArrProducts.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public void clearItens() {
        mArrProducts.clear();
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        @Bind(R.id.tvBadge)
        TextView tvBadge;
        @Bind(R.id.ivImage)
        ImageView ivImage;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvOriginalPrice)
        TextView tvOriginalPrice;
        @Bind(R.id.tvActualPrice)
        TextView tvActualPrice;

        private int mPosition;

        public VH(View convertView) {
            super(convertView);

            ButterKnife.bind(this, convertView);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFragment instanceof RecycleViewInterface.OnItemClickListener) {
                        ((RecycleViewInterface.OnItemClickListener)mFragment)
                                .OnItemClick(ProductListAdapter.this, mPosition);
                    }
                }
            });
        }

        public void fill(int position) {

            mPosition = position;

            Product product = mArrProducts.get(position);

            //Utiliza a biblioteca picasso para obtem a imagem
            Picasso.with(mContext)
                    .load(product.getImage().getLarge())
                    .into(ivImage);

            //If has badge
            if (product.getBadge() != null) {
                tvBadge.setVisibility(View.VISIBLE);
                tvBadge.setText(product.getBadge().getValue());
            }
            else
                tvBadge.setVisibility(View.GONE);

            //Preenche campos
            tvName.setText(product.getName());
            tvOriginalPrice.setText(product.getPrice().getOriginalPrice());
            tvActualPrice.setText(product.getPrice().getActualPrice());

            //Set strikethrough
            tvOriginalPrice.setPaintFlags(
                    tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
