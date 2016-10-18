package in.mumbaitravellers.mtleaders.app;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Object that controls all aspect of how realm is created.
 * */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1000)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
