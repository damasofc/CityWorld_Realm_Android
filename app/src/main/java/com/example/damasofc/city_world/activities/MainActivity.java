package com.example.damasofc.city_world.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.damasofc.city_world.R;
import com.example.damasofc.city_world.adapter.MyAdapter;
import com.example.damasofc.city_world.models.City;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    RecyclerView rcView;
    Realm realm;
    public RealmResults<City> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents(){
        realm = Realm.getDefaultInstance();
        cities = realm.where(City.class).findAll();
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        rcView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        MyAdapter mAP = new MyAdapter(cities, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city, int position) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("name",city.getNameCity());
                intent.putExtra("rating",city.getRating());
                intent.putExtra("description",city.getDescription());
                intent.putExtra("image",city.getImgCity());
                intent.putExtra("position",position);
                intent.putExtra("FUENTE",1);
                startActivity(intent);
                finish();
            }
        });
        rcView.setLayoutManager(lm);
        rcView.setAdapter(mAP);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("FUENTE",2);
                startActivity(intent);
                finish();
            }
        });
    }
}
