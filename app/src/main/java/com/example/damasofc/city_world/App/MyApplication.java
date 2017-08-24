package com.example.damasofc.city_world.App;

import android.app.Application;

import com.example.damasofc.city_world.models.City;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by damasofc on 08-23-17.
 */

public class MyApplication extends Application {
    public static AtomicInteger cityId = new AtomicInteger();

    @Override
    public void onCreate() {
        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();
        cityId = getIdByTable(realm, City.class);
        realm.close();
    }

    void setUpRealmConfig(){
        RealmConfiguration config = new RealmConfiguration
                .Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return results.size() > 0? new AtomicInteger(results.max("id").intValue()): new AtomicInteger();
    }
}





