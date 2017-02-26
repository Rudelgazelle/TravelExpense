package development.android.androidfirebasetutorial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lbuer on 25.02.2017.
 */

    // 1- implement methods by pressing alt + enter
public class Adapter_for_RecyclerView_ExpenseHistory extends RecyclerView.Adapter<Adapter_for_RecyclerView_ExpenseHistory.Viewholder>{

    private List<TravelExpenseData> listitems;
    private Context context;

    //Create a constructor for List and context
    public Adapter_for_RecyclerView_ExpenseHistory(List<TravelExpenseData> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    //this method will be called whenever the viewholder is created and creates an instance of class Viewholder
    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return the viewholder and inflate the ListItem layout
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recyclerview_expensehistory, parent, false);
        //return a new instance of the ViewHolder
        return new Viewholder(v);
    }

    //this method will bind the data to the viewholder object
    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        //Gets the specific position of the list item
        final TravelExpenseData listItem = listitems.get(position);
        //Sets the ilist items to the specific view object
        holder.textViewLocation.setText(listItem.getLocation());
        holder.textViewTravelDate.setText(listItem.getTravelDate());
        holder.textViewDescription.setText(listItem.getDescription());

        //initialize linear layout and attach OnClickListener to enable item click
        //whenever an Item with the position "position" is clicked, this method is being executed
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //As an example a toast object will be called to show a toast whenever an item is being clicked
                Toast.makeText(context, "you clicked Item "+ listItem.getLocation(), Toast.LENGTH_LONG).show();

            }
        });
    }

    //returns the number of items covered in the List
    @Override
    public int getItemCount() {
        return listitems.size();
    }

    // 2 - create constructor (alt + enter)
    public class Viewholder extends RecyclerView.ViewHolder{

        // Define the Textviews of the Card View
        public TextView textViewLocation;
        public TextView textViewTravelDate;
        public TextView textViewDescription;

        //Define a Linear Layout to enable item click
        public LinearLayout linearLayout;

        public Viewholder(View itemView) {
            super(itemView);

            textViewLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            textViewTravelDate = (TextView) itemView.findViewById(R.id.tvTravelDate);
            textViewDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.CardViewLinearLayout);
        }
    }

}
