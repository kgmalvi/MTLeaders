package in.mumbaitravellers.mtleaders.adapters;

import android.content.Context;

import in.mumbaitravellers.mtleaders.model.Expense;
import io.realm.RealmResults;

/**
 * Created by Administrator on 06-10-2016.
 */
public class RealmExpenseAdapter extends RealmModelAdapter<Expense> {
    public RealmExpenseAdapter(Context context, RealmResults<Expense> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
