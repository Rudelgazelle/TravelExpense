package development.android.androidfirebasetutorial;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_NavMenu_ExpenseHistory extends Fragment {

    private RecyclerView recyclerView;

    private List<TravelExpenseData> listItems;

    //Firebase auth object
    private String currentUserID;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //the the header name in actionbar
        getActivity().setTitle("Expense History");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //inflate the fragment layout
        View fragmentView = inflater.inflate(R.layout.fragment_nav_menu_expensehistory, container, false);

        //Initiate the RecyclerView object //map the Recyclerview object to the xml RecyclerView
        recyclerView = (RecyclerView)fragmentView.findViewById(R.id.RecyclerViewExpenseHistory);
        //Every item of the recyclerview will have a fixed size
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Fill the recycleview with data from firebase
        updateRecycleView();

        return fragmentView;
    }

    private void updateRecycleView(){
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Show progress bar to indicate data transfer
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Retrieving data...");
        progressDialog.show();

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //* check if the user is already logged in*//*
        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null){
            //*User is not logged in*//*
            //* start login activity*//*
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

        /* Prepare steps to do a data collection of data from database ------------------------------------------------------*/

        //Get the user ID of the current user (Logged In user Account)
        currentUserID = firebaseAuth.getCurrentUser().getUid();

        // set the database reference to the correct child object (TravelExpenseData)
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference = databaseReference.child(currentUserID).child("TravelExpenseData");

        // Create a new instance of a ArrayList; Initialize the above defined listitem object
        listItems = new ArrayList<>();

        //set a ValueEventlistener to the database reference that listens if changes are being made to the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //returns a collection of the children under the set database reference
                //datasnapshot.getChlidren();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // Shake hands with each of the collected childrens
                //Iterate over the collection of "children" specified above and put it into a variable called "child"
                for (DataSnapshot child : children ) {
                    //child.getValue(TravelExpenseData.class); "VOR STRG + ALT +V"
                    TravelExpenseData travelExpenseData = child.getValue(TravelExpenseData.class);
                    //add the retrieved data to the ArrayList
                    listItems.add(travelExpenseData);
                }

                // Instanciate a new adapter for the Recycleview and parse the "listitem" and "Context"
                final Adapter_for_RecyclerView_ExpenseHistory adapter = new Adapter_for_RecyclerView_ExpenseHistory(listItems, getContext());

                //set the adapter to the recyclerview
                recyclerView.setAdapter(adapter);

                // notify the adapter that data has been changed and needs to be refreshed
                //adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Deflate progress bar
        progressDialog.dismiss();
    }


}
