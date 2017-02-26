package development.android.androidfirebasetutorial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_NavigationMenu_ExpenseHistory extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem_model_for_RecyclerView_ExpenseHistory> listItems;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //the the header noame in actionbar
        getActivity().setTitle("Expense History");

        //map the Recyclerview object to the xml RecyclerView
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_navigation_menu__expense_history, container, false);

        //Initiate the RecyclerView obejct
        recyclerView = (RecyclerView)view.findViewById(R.id.RecyclerViewExpenseHistory);
        //Every item of the recyclerview will have a fixed size
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initialize the listitems object
        listItems = new ArrayList<>();

        for (int i = 0; i<10; i++){
            ListItem_model_for_RecyclerView_ExpenseHistory listItem = new ListItem_model_for_RecyclerView_ExpenseHistory(
                    "Location " + (i+1),
                    "Date",
                    "Description"
            );

            //Add these listitems to the list
            listItems.add(listItem);
        }

        // Instanciate a new adapter for the Recycleview and parse the "listitem" and "Context"
        adapter = new Adapter_for_RecyclerView_ExpenseHistory(listItems, getContext());
        //set the adapter to the recyclerview
        recyclerView.setAdapter(adapter);

        return view;
    }


}
