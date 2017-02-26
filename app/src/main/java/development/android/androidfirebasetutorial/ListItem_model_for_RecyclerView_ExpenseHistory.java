package development.android.androidfirebasetutorial;

/**
 * Created by lbuer on 25.02.2017.
 */

public class ListItem_model_for_RecyclerView_ExpenseHistory {

    //define variables for the TextViews displayed on the card view
    private String location;
    private String traveldate;
    private String description;

    //Create constructor for the variables (Generate --> Constructor)
    public ListItem_model_for_RecyclerView_ExpenseHistory(String location, String traveldate, String description) {
        this.location = location;
        this.traveldate = traveldate;
        this.description = description;
    }

    //Create getter for the variables
    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getTraveldate() {
        return traveldate;
    }


}
