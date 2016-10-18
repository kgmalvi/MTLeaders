package in.mumbaitravellers.mtleaders.adapters;

import android.content.Context;

import in.mumbaitravellers.mtleaders.model.Tour;
import io.realm.RealmResults;

/**
 * Created by Administrator on 06-10-2016.
 */
public class RealmToursAdapter extends RealmModelAdapter<Tour> {

    public RealmToursAdapter(Context context, RealmResults<Tour> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}