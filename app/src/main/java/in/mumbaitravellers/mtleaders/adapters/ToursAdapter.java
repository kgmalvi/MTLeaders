package in.mumbaitravellers.mtleaders.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import in.mumbaitravellers.mtleaders.R;
import in.mumbaitravellers.mtleaders.activity.DetailActivity;
import in.mumbaitravellers.mtleaders.model.Tour;
import in.mumbaitravellers.mtleaders.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 06-10-2016.
 */


public class ToursAdapter extends RealmRecyclerViewAdapater<Tour>/* implements View.OnCreateContextMenuListener*/ {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public ToursAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_recycler_view, parent, false);
        // view.setOnCreateContextMenuListener(this);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        final Tour tour = getItem(position);
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        holder.textEvent.setText(tour.getEventName());
        holder.textEventDate.setText(tour.getEventStartDate());
        holder.textLeaders.setText(tour.getLeaders());

        /*// load the background image
        if (tour.getImageUrl() != null) {
            Glide.with(context)
                    .load(tour.getImageUrl().replace("https", "http"))
                    .asBitmap()
                    .fitCenter()
                    .into(holder.imageBackground);
        }*/

       /* //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<Tour> results = realm.where(Tour.class).findAll();

                // Get the book title to show it in toast message
                Tour b = results.get(position);
                String title = b.getEventName();

                // All changes to data must happen in a transaction
                realm.beginTransaction();

                // remove single match
                results.remove(position);
                realm.commitTransaction();

                if (results.size() == 0) {
                    Prefs.with(context).setPreLoad(false);
                }

                notifyDataSetChanged();

                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

                editEvent.setText(tour.getEventName());
                editDivisionHead.setText(tour.getDivisionHead());
                editStartDate.setText(tour.getEventStartDate());
                editEndDate.setText(tour.getEventEndDate());
                editLeader.setText(tour.getLeaders());
                editCashCarried.setText(tour.getCashCarried());
                editTourCost.setText(tour.getTourCost());
                editOnTour.setText(tour.getOnTourCollection());
                editPreRegistered.setText(tour.getPreRegistered());
                editOnSpot.setText(tour.getOnSpotRegister());
                editCancel.setText(tour.getCancel());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Event")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmResults<Tour> results = realm.where(Tour.class).findAll();

                                realm.beginTransaction();
                                results.get(position).setEventName(editEvent.getText().toString());
                                results.get(position).setDivisionHead(editDivisionHead.getText().toString());
                                results.get(position).setEventStartDate(editStartDate.getText().toString());
                                results.get(position).setEventEndDate(editEndDate.getText().toString());
                                results.get(position).setLeaders(editLeader.getText().toString());
                                results.get(position).setCashCarried(editCashCarried.getText().toString());
                                results.get(position).setTourCost(editTourCost.getText().toString());
                                results.get(position).setOnTourCollection(editOnTour.getText().toString());
                                results.get(position).setPreRegistered(editPreRegistered.getText().toString());
                                results.get(position).setOnSpotRegister(editOnSpot.getText().toString());
                                results.get(position).setCancel(editCancel.getText().toString());

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

                return false;
            }
        });

        //update single match from realm
        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String event = getItem(position).getEventName().toString();
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("eventName", event);
                v.getContext().startActivity(intent);
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

   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "SMS");

    }*/


    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textEvent;
        public TextView textEventDate;
        public TextView textLeaders;
        public ImageView imageBackground;

        public CardViewHolder(View itemView)    {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_tours);
            textEvent = (TextView) itemView.findViewById(R.id.text_event_title);
            textEventDate = (TextView) itemView.findViewById(R.id.text_event_date);
            textLeaders = (TextView) itemView.findViewById(R.id.text_leaders);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
        }

    }
}