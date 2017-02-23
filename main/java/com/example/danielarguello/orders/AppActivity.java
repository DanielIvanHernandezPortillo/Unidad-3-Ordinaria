package com.example.danielarguello.orders;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.danielarguello.orders.adapters.AppAdapter;
import com.example.danielarguello.orders.adapters.ProductAdapter;
import com.example.danielarguello.orders.model.App;
import com.example.danielarguello.orders.sqlite.DBOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DANIEL on 23/02/2017.
 */

public class AppActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private Button btSaveApp;
    private DBOperations operations;
    private App app = new App() ;
    private List<App> apps = new ArrayList<>();
    private MediaPlayer correct;
    private MediaPlayer resorte;

    private AppAdapter adapter;

    private RecyclerView rvApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        operations = DBOperations.getDBOperations(getApplicationContext());
        app.setIdApp("");
        initComponents();
    }
    private void initComponents(){
        etTitle = (EditText)findViewById(R.id.et_title);
        etDescription = (EditText)findViewById(R.id.et_description);
        rvApps = (RecyclerView) findViewById(R.id.rv_app_list);
        rvApps.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvApps.setLayoutManager(layoutManager);
        getApps();


        adapter = new AppAdapter(apps);
        correct = MediaPlayer.create(AppActivity.this, R.raw.correcto);
        resorte= MediaPlayer.create(AppActivity.this, R.raw.resorte);
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.bt_delete_app:
                        resorte.start();
                        operations.deleteApp(
                                apps.get(rvApps.getChildPosition((View)v.getParent().getParent())).getIdApp());
                        getApps();
                        adapter.notifyDataSetChanged();

                        break;
                    case R.id.bt_edit_app:
                        correct.start();

                        Cursor c = operations.getAppById(apps.get(rvApps.getChildPosition((View)v.getParent().getParent()))
                                .getIdApp());

                        if(c!=null){
                            if(c.moveToFirst()){
                                app = new App(c.getString(1),
                                        c.getString(2),c.getString(3));
                            }
                            etTitle.setText(app.getTitle());
                            etDescription.setText(app.getDescription());

                        }else{
                            //Toast.makeText(getApplicationContext(),
                            //      "Registro no encontrado", Toast)
                        }

                        break;
                    default:
                        break;

                }
            }
        });
        rvApps.setAdapter(adapter);
        btSaveApp = (Button) findViewById(R.id.bt_save_app);



        btSaveApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!app.getIdApp().equals("")) {
                    app.setTitle(etTitle.getText().toString());
                    app.setDescription(etDescription.getText().toString());

                    operations.updateApp(app);
                    correct.start();
                } else {
                    app = new App("", etTitle.getText().toString(),
                            etDescription.getText().toString());
                    operations.insertApp(app);
                }
                clearData();
                getApps();
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void getApps(){

        Cursor c= operations.getApps();
        apps.clear();
        if(c!=null){
            while (c.moveToNext()){
                apps.add(new App(c.getString(1),
                        c.getString(2),c.getString(3)));

            }
        }
    }

    private void clearData(){
        etTitle.setText("");
        etDescription.setText("");
        app= null;
        app = new App();
        app.setIdApp("");
    }




}
