package com.example.damasofc.city_world.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.damasofc.city_world.R;
import com.example.damasofc.city_world.models.City;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class EditActivity extends AppCompatActivity {

    ImageView imgView;
    Realm realm;
    EditText txtCityName;
    Button buttonPreview;
    EditText editTextCityImage;
    RatingBar ratingBarCity;
    FloatingActionButton fabSaveCity;
    EditText editText_description;
    String name,image,description;
    Bundle data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initComponents();
    }

    private void initComponents(){
        realm = Realm.getDefaultInstance();
        fabSaveCity = (FloatingActionButton) findViewById(R.id.fab_SaveCity);
        ratingBarCity = (RatingBar) findViewById(R.id.ratingBarCity);
        editTextCityImage = (EditText) findViewById(R.id.editTextCityImage);
        editText_description = (EditText) findViewById(R.id.editText_description);
        buttonPreview = (Button) findViewById(R.id.buttonPreview);
        txtCityName = (EditText) findViewById(R.id.txtCityName);
        imgView = (ImageView) findViewById(R.id.imgView);
        data = getIntent().getExtras();
        setData(data);

        fabSaveCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFull()){
                    realm.beginTransaction();
                    City cn = new City(image,name,description,ratingBarCity.getRating());
                    if(data.isEmpty()){
                        realm.copyToRealm(cn);
                    }
                    else {
                        realm.copyToRealmOrUpdate(cn);
                    }
                    realm.commitTransaction();
                    Toast.makeText(EditActivity.this, "Creado....", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(EditActivity.this, "Complete the Info.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCityImage.getText().toString().length() > 0)
                    cargarImagen(editTextCityImage.getText().toString());
            }
        });

    }
    boolean isFull(){
        name = txtCityName.getText().toString();
        image = editTextCityImage.getText().toString();
        description = editText_description.getText().toString();
        if(name.length() > 0 && image.length() > 0 && description.length() > 0){
            return true;
        }
        return false;
    }
    void cargarImagen(String url){
        Picasso.with(this).load(url).fit().into(imgView);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void setData(Bundle b){
        if(b.isEmpty()){

        }else{
            cargarImagen(b.getString("image"));
            txtCityName.setText(b.getString("name"));
            editTextCityImage.setText(b.getString("image"));
            editText_description.setText(b.getString("description"));
            ratingBarCity.setRating(b.getFloat("rating"));
        }
    }
}
