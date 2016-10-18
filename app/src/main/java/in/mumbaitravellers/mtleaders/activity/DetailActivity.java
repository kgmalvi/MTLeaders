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
import in.mumbaitravellers.mtleaders.adapters.ExpenseAdapter;
import in.mumbaitravellers.mtleaders.adapters.RealmExpenseAdapter;
import in.mumbaitravellers.mtleaders.app.Prefs;
import in.mumbaitravellers.mtleaders.model.Expense;
import in.mumbaitravellers.mtleaders.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

public class DetailActivity extends AppCompatActivity {

    private ExpenseAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recycler = (RecyclerView) findViewById(R.id.recyclerExpense);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*String event = getIntent().getStringExtra("eventName");
        TextView tv = (TextView) findViewById(R.id.txt_EventName);
        tv.setText(event);*/

        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getExpenses());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inflater = DetailActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.activity_add_new_expense, null);
                final EditText editExpense = (EditText) content.findViewById(R.id.edtxt_expense);
                final EditText editDescription = (EditText) content.findViewById(R.id.edtxt_description);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setView(content)
                        .setTitle("Add Expense")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Expense expense = new Expense();

                                expense.setId((int) (RealmController.getInstance().getExpenses().size() + System.currentTimeMillis()));
                                expense.setAmount(editExpense.getText().toString());
                                expense.setDescription(editDescription.getText().toString());

                                realm.beginTransaction();
                                realm.copyToRealm(expense);
                                realm.commitTransaction();

                                adapter.notifyDataSetChanged();
                                // scroll the recycler view to bottom
                                recycler.scrollToPosition(RealmController.getInstance().getExpenses().size() - 1);
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

    public void setRealmAdapter(RealmResults<Expense> expenses) {

        RealmExpenseAdapter realmExpenseAdapter = new RealmExpenseAdapter(this.getApplicationContext(), expenses, true);
        adapter.setRealmAdapter(realmExpenseAdapter);
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
        adapter = new ExpenseAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<Expense> expenses = new ArrayList<>();

        for (Expense e : expenses) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(e);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);
    }
}