package br.com.netshoes.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.netshoes.R;
import br.com.netshoes.interfaces.RecycleViewInterface;
import br.com.netshoes.model.Detail;
import br.com.netshoes.model.Image;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Caramelo on 18/12/2015.
 */
public class ProductDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    implements RecycleViewInterface.Adapter<Detail>{

    private Context mContext;
    private ArrayList<Detail> mArrDetails;

    public ProductDetailAdapter(Context context) {
        this.mContext = context;
        this.mArrDetails = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == Detail.Type.Image.getId()) {
            itemView = View.inflate(mContext, R.layout.adapter_detail_image, null);
            return new ViewHolderImage(itemView);
        }
        else if (viewType == Detail.Type.ProductName.getId()) {
            itemView = View.inflate(mContext, R.layout.adapter_detail_name, null);
            return new ViewHolderName(itemView);
        }
        else if (viewType == Detail.Type.Title.getId()) {
            itemView = View.inflate(mContext, R.layout.adapter_detail_title, null);
            return new ViewHolderTile(itemView);
        }
        else {
            itemView = View.inflate(mContext, R.layout.adapter_detail_description, null);
            return new ViewHolderDescription(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Detail detail = this.getItem(position);

        if (holder instanceof ViewHolderImage)
            ((ViewHolderImage)holder).fill(detail);
        else if (holder instanceof ViewHolderName)
            ((ViewHolderName)holder).fill(detail);
        else if (holder instanceof ViewHolderTile)
            ((ViewHolderTile)holder).fill(detail);
        else
            ((ViewHolderDescription)holder).fill(detail);
    }

    @Override
    public int getItemViewType(int position) {
        Detail detail = this.getItem(position);
        return detail.getType().getId();
    }

    @Override
    public int getItemCount() {
        return mArrDetails.size();
    }

    @Override
    public ArrayList<Detail> getItens() {
        return mArrDetails;
    }

    @Override
    public void addItens(ArrayList<Detail> itens) {
        mArrDetails.addAll(itens);
        notifyDataSetChanged();
    }

    @Override
    public Detail getItem(int position) {
        return mArrDetails.get(position);
    }

    @Override
    public void addItem(Detail item) {
        mArrDetails.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(Detail item) {
        mArrDetails.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public void clearItens() {
        mArrDetails.clear();
        notifyDataSetChanged();
    }

    /** Views Holders **/

    class ViewHolderImage extends RecyclerView.ViewHolder {

        @Bind(R.id.ivImage)
        ImageView ivImage;

        public ViewHolderImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fill(Detail detail) {
            Image image = (Image) detail.getObject();
            Picasso.with(mContext)
                    .load(image.getLarge())
                    .into(ivImage);
        }
    }

    class ViewHolderName extends RecyclerView.ViewHolder {

        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolderName(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fill(Detail detail) {
            try {
                String name = (String) detail.getObject();
                tvName.setText(name);
            } catch (Exception err) {
                tvName.setText("");
            }
        }
    }

    class ViewHolderTile extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public ViewHolderTile(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fill(Detail detail) {
            try {
                String text = (String) detail.getObject();
                tvTitle.setText(text);
            } catch (Exception err) {
                tvTitle.setText("");
            }
        }
    }

    class ViewHolderDescription extends RecyclerView.ViewHolder {

        @Bind(R.id.tvDescription)
        TextView tvDescription;

        public ViewHolderDescription(View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }

        public void fill(Detail detail) {
            try {
                String text = (String) detail.getObject();
                tvDescription.setText(text);
            } catch (Exception err) {
                tvDescription.setText("");
            }
        }
    }
}
