package com.example.damasofc.city_world.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damasofc.city_world.App.MyApplication;
import com.example.damasofc.city_world.R;
import com.example.damasofc.city_world.models.City;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import io.realm.Realm;

/**
 * Created by damasofc on 08-19-17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<City> cities;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    Realm realm = Realm.getDefaultInstance();

    public MyAdapter(List<City> cities, int layout, OnItemClickListener itemClickListener) {
        this.cities = cities;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(cities.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cityImage;
        public TextView cityName;
        public TextView descriptionCity;
        public TextView ratingCity;
        public TextView delete;
        public ViewHolder(View itemView) {
            super(itemView);
            cityImage = (ImageView) itemView.findViewById(R.id.img_Recycler_item);
            cityName = (TextView) itemView.findViewById(R.id.name_city);
            descriptionCity = (TextView) itemView.findViewById(R.id.description_city);
            ratingCity = (TextView) itemView.findViewById(R.id.txtView_rating);
            delete = (TextView) itemView.findViewById(R.id.txtView_delete);

        }

        public void setData(final City city, final OnItemClickListener listener){
            Picasso.with(context).load(city.getImgCity()).fit().into(cityImage);
            cityName.setText(city.getNameCity());
            descriptionCity.setText(city.getDescription());
            ratingCity.setText(String.valueOf(city.getRating()));
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Aca ira lo que sucedera cuando den click a DELETE
                    showAlertDialog("Delete City","Are you sure you want to delete " + city.getNameCity(),
                            city);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(city,getAdapterPosition());
                }
            });


        }
    }

    public interface  OnItemClickListener{
        void onItemClick(City city, int position);
    }

    public void showAlertDialog(String title, String message, final City city){
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        if(title!= null) ad.setTitle(title);
        if(message!= null) ad.setMessage(message);

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.alert_dialog,null);
        ad.setView(viewInflated);
        ad.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realm.beginTransaction();
                city.deleteFromRealm();
                realm.commitTransaction();
                MyAdapter.this.notifyDataSetChanged();
                Toast.makeText(context, "Ha sido Borrado......", Toast.LENGTH_SHORT).show();
            }
        });
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.create().show();

    }
}
