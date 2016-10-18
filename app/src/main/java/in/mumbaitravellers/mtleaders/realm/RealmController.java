package in.mumbaitravellers.mtleaders.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import in.mumbaitravellers.mtleaders.model.Expense;
import in.mumbaitravellers.mtleaders.model.Tour;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 06-10-2016.
 */
public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(Tour.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Tour> getTours() {

        return realm.where(Tour.class).findAll();
    }

    //query a single item with the given id
    public Tour getTour(String id) {

        return realm.where(Tour.class).equalTo("id", id).findFirst();
    }

    //check if Tour.class is empty
    public boolean hasTour() {

        return !realm.allObjects(Tour.class).isEmpty();
    }

    //query example
    public RealmResults<Tour> queryedTours() {

        return realm.where(Tour.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }


    //find all objects in the Book.class
    public RealmResults<Expense> getExpenses() {

        return realm.where(Expense.class).findAll();
    }

    //query a single item with the given id
    public Expense getExpense(String id) {

        return realm.where(Expense.class).equalTo("id", id).findFirst();
    }

    //check if Tour.class is empty
    public boolean hasExpense() {

        return !realm.allObjects(Expense.class).isEmpty();
    }

    //query example
    public RealmResults<Expense> queryedExpenses() {

        return realm.where(Expense.class)
                .contains("expense", "Expense 0")
                .or()
                .contains("description", "Realm")
                .findAll();

    }
}
