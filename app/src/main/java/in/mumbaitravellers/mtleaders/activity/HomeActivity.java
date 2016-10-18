package in.mumbaitravellers.mtleaders.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import in.mumbaitravellers.mtleaders.R;
import in.mumbaitravellers.mtleaders.adapters.RealmToursAdapter;
import in.mumbaitravellers.mtleaders.adapters.ToursAdapter;
import in.mumbaitravellers.mtleaders.app.Prefs;
import in.mumbaitravellers.mtleaders.model.Tour;
import in.mumbaitravellers.mtleaders.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

public class HomeActivity extends AppCompatActivity {

    private ToursAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getTours());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inflater = HomeActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.activity_add_new_trip, null);
                final EditText editEvent = (EditText) content.findViewById(R.id.edTxt_EventName);
                final EditText editDivisionHead = (EditText) content.findViewById(R.id.edTxt_DivisionHead);
                final EditText editStartDate = (EditText) content.findViewById(R.id.edTxt_EventSDate);
                final EditText editEndDate = (EditText) content.findViewById(R.id.edTxt_EventEDate);
                final EditText editLeader = (EditText) content.findViewById(R.id.edTxt_Leader);
                final EditText editCashCarried = (EditText) content.findViewById(R.id.edTxt_CashCarried);
                final EditText editTourCost = (EditText) content.findViewById(R.id.edTxt_TourCost);
                final EditText editOnTour = (EditText) content.findViewById(R.id.edTxt_TourCollection);
                final EditText editPreRegistered = (EditText) content.findViewById(R.id.edTxt_PreTravellers);
                final EditText editOnSpot = (EditText) content.findViewById(R.id.edTxt_NewTravellers);
                final EditText editCancel = (EditText) content.findViewById(R.id.edTxt_Cancel);

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setView(content)
                        .setTitle("Add Event")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Tour tour = new Tour();

                                //book.setId(RealmController.getInstance().getBooks().size() + 1);
                                tour.setId((int) (RealmController.getInstance().getTours().size() + System.currentTimeMillis()));
                                tour.setEventName(editEvent.getText().toString());
                                tour.setDivisionHead(editDivisionHead.getText().toString());
                                tour.setEventStartDate(editStartDate.getText().toString());
                                tour.setEventEndDate(editEndDate.getText().toString());
                                tour.setLeaders(editLeader.getText().toString());
                                tour.setCashCarried(editCashCarried.getText().toString());
                                tour.setTourCost(editTourCost.getText().toString());
                                tour.setOnTourCollection(editOnTour.getText().toString());
                                tour.setPreRegistered(editPreRegistered.getText().toString());
                                tour.setOnSpotRegister(editOnSpot.getText().toString());
                                tour.setCancel(editCancel.getText().toString());

                                realm.beginTransaction();
                                realm.copyToRealm(tour);
                                realm.commitTransaction();

                                adapter.notifyDataSetChanged();
                                // scroll the recycler view to bottom
                                recycler.scrollToPosition(RealmController.getInstance().getTours().size() - 1);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void setRealmAdapter(RealmResults<Tour> tours) {

        RealmToursAdapter realmAdapter = new RealmToursAdapter(this.getApplicationContext(), tours, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new ToursAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<Tour> tours = new ArrayList<>();


       /* Tour tour = new Tour();
        tour.setId((int) (1 + System.currentTimeMillis()));
        tour.setEventName("Reto Meier".toString());
        tour.setDivisionHead("Android 4 Application Development".toString());
        tour.setLeaders("http://api.androidhive.info/images/realm/1.png".toString());
        tours.add(tour);

       /* book = new Book();
        book.setId(2 + System.currentTimeMillis());
        book.setAuthor("Itzik Ben-Gan");
        book.setTitle("Microsoft SQL Server 2012 T-SQL Fundamentals");
        book.setImageUrl("http://api.androidhive.info/images/realm/2.png");
        books.add(book);

        book = new Book();
        book.setId(3 + System.currentTimeMillis());
        book.setAuthor("Magnus Lie Hetland");
        book.setTitle("Beginning Python: From Novice To Professional Paperback");
        book.setImageUrl("http://api.androidhive.info/images/realm/3.png");
        books.add(book);

        book = new Book();
        book.setId(4 + System.currentTimeMillis());
        book.setAuthor("Chad Fowler");
        book.setTitle("The Passionate Programmer: Creating a Remarkable Career in Software Development");
        book.setImageUrl("http://api.androidhive.info/images/realm/4.png");
        books.add(book);

        book = new Book();
        book.setId(5 + System.currentTimeMillis());
        book.setAuthor("Yashavant Kanetkar");
        book.setTitle("Written Test Questions In C Programming");
        book.setImageUrl("http://api.androidhive.info/images/realm/5.png");
        books.add(book);
*/

        for (Tour t : tours) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(t);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);
    }

}
