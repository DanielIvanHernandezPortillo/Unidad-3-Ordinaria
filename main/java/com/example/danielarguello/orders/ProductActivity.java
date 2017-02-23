package com.example.danielarguello.orders;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danielarguello.orders.adapters.ProductAdapter;
import com.example.danielarguello.orders.model.Product;
import com.example.danielarguello.orders.sqlite.DBOperations;

import java.util.ArrayList;
import java.util.List;


public class ProductActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPrice;
    private EditText etStock;
    private Button btSaveProduct;
    private DBOperations operations;
    private Product product = new Product() ;
    private         List<Product> products = new ArrayList<Product>();
    private MediaPlayer correct;
    private MediaPlayer resorte;

    private ProductAdapter adapter;

    private RecyclerView rvProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        operations = DBOperations.getDBOperations(getApplicationContext());
        product.setIdProduct("");
        initComponents();
    }

    private void initComponents(){
        etName = (EditText)findViewById(R.id.et_name);
        etPrice = (EditText)findViewById(R.id.et_price);
        etStock = (EditText)findViewById(R.id.et_stock);
        rvProducts = (RecyclerView) findViewById(R.id.rv_product_list);
        rvProducts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);
        getProducts();


        adapter = new ProductAdapter(products);
        correct = MediaPlayer.create(ProductActivity.this, R.raw.correcto);
        resorte= MediaPlayer.create(ProductActivity.this, R.raw.resorte);
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.bt_delete_product:
                        resorte.start();
                        operations.deleteProduct(
                                products.get(rvProducts.getChildPosition((View)v.getParent().getParent())).getIdProduct());
                        getProducts();
                        adapter.notifyDataSetChanged();

                        break;
                    case R.id.bt_edit_product:
                        correct.start();

                        Cursor c = operations.getProductById(products.get(rvProducts.getChildPosition((View)v.getParent().getParent()))
                                .getIdProduct());

                        if(c!=null){
                            if(c.moveToFirst()){
                                product = new Product(c.getString(1),
                                        c.getString(2),c.getFloat(3), c.getInt(4));
                            }
                            etName.setText(product.getName());
                            etStock.setText(String.valueOf(product.getStock()));
                            etPrice.setText(String.valueOf(product.getPrice()));
                        }else{
                            //Toast.makeText(getApplicationContext(),
                              //      "Registro no encontrado", Toast)
                        }

                        break;
                    default:
                        break;

                }


                //operations.deleteProduct(products.get(rvProducts.getChildPosition(v))
                //        .getIdProduct());
                //products.remove(rvProducts.getChildPosition(v));
                //adapter.notifyDataSetChanged();
            }
        });
        rvProducts.setAdapter(adapter);
        btSaveProduct = (Button) findViewById(R.id.bt_save_product);



        btSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!product.getIdProduct().equals("")) {
                    product.setName(etName.getText().toString());
                    product.setPrice(Float.parseFloat(etPrice.getText().toString()));
                    product.setStock(Integer.parseInt(
                            etStock.getText().toString()

                    ));
                    operations.updateProduct(product);
                    correct.start();
                } else {
                    product = new Product("", etName.getText().toString(),
                            Float.parseFloat(etPrice.getText().toString()),
                            Integer.parseInt(etStock.getText().toString()));
                    operations.insertProduct(product);
                }
                // Log.d("Products","Products");
                // DatabaseUtils.dumpCursor(operations.getProducts());
                clearData();
                getProducts();
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void getProducts(){

        Cursor  c= operations.getProducts();
        products.clear();
        if(c!=null){
            while (c.moveToNext()){
                products.add(new Product(c.getString(1),
                        c.getString(2),c.getFloat(3), c.getInt(4)));

            }
        }
    }

    private void clearData(){
        etName.setText("");
        etPrice.setText("");
        etStock.setText("");
        product= null;
        product = new Product();
        product.setIdProduct("");
    }

}
