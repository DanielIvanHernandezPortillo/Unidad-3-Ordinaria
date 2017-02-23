package com.example.danielarguello.orders.adapters;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danielarguello.orders.R;
import com.example.danielarguello.orders.model.Product;

import java.util.List;

/**
 * Created by DANIEL  on 15/02/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements View.OnClickListener{

    List<Product> products;
    View.OnClickListener listener;

    public ProductAdapter(List<Product> products){
        this.products = products;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);

        ProductViewHolder holder = new ProductViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder, int position) {
        holder.tvProductname.setText(products.get(position).getName());
        holder.tvProductPrice.setText(String.valueOf(products.get(position).getPrice()));
        holder.tvProductStock.setText(String.valueOf(products.get(position).getStock()));
        holder.ivProduct.setImageResource(R.drawable.androidan);
        holder.setListener(this);

    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!= null){
            listener.onClick(v);
        }

    }

    public static class ProductViewHolder extends  RecyclerView.ViewHolder  implements View.OnClickListener{

        CardView cvProduct;
        TextView tvProductname;
        TextView tvProductPrice;
        TextView tvProductStock;
        ImageView ivProduct;
        ImageButton btEditProduct;
        ImageButton btDeleteProduct;
        View.OnClickListener listener;



        public ProductViewHolder(View itemView) {
            super(itemView);
            cvProduct = (CardView) itemView.findViewById(R.id.cv_product);
            ivProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            tvProductname = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
            tvProductStock = (TextView) itemView.findViewById(R.id.tv_product_stock);
            btEditProduct = (ImageButton) itemView.findViewById(R.id.bt_edit_product);
            btDeleteProduct = (ImageButton) itemView.findViewById(R.id.bt_delete_product);
            btEditProduct.setOnClickListener(this);
            btDeleteProduct.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onClick(v);
            }

        }

        public void setListener(View.OnClickListener listener) {
            this.listener = listener;
        }
    }
}
