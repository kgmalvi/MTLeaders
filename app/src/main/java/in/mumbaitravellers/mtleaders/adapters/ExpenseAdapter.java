package in.mumbaitravellers.mtleaders.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import in.mumbaitravellers.mtleaders.R;
import in.mumbaitravellers.mtleaders.model.Expense;
import in.mumbaitravellers.mtleaders.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 06-10-2016.
 */
public class ExpenseAdapter extends RealmRecyclerViewAdapater<Expense> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public ExpenseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_view, parent, false);
        // view.setOnCreateContextMenuListener(this);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        final Expense expense = getItem(position);
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        holder.textExpense.setText(expense.getAmount());
        holder.textDescription.setText(expense.getDescription());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.activity_add_new_expense, null);
                final EditText editAmount = (EditText) content.findViewById(R.id.edtxt_expense);
                final EditText editDescription = (EditText) content.findViewById(R.id.edtxt_description);

                editAmount.setText(expense.getAmount());
                editDescription.setText(expense.getDescription());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Event")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<Expense> results = realm.where(Expense.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setAmount(editAmount.getText().toString());
                                results.get(position).setDescription(editDescription.getText().toString());

                                realm.commitTransaction();

                                notifyDataSetChanged();
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

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textExpense;
        public TextView textDescription;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_expense);
            textExpense = (TextView) itemView.findViewById(R.id.text_amount);
            textDescription = (TextView) itemView.findViewById(R.id.text_description);
        }

    }
}
