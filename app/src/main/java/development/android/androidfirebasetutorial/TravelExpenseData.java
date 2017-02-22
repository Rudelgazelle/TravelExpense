package development.android.androidfirebasetutorial;

import java.util.List;

public class TravelExpenseData {

    public String travelDate;
    public String expenseType;
    public Float expenseAmount;
    public String location;
    public String description;


    public TravelExpenseData(){

    }

    public TravelExpenseData( String travelDate, String expenseType, Float expenseAmount, String location, String description) {

        this.travelDate = travelDate;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.location = location;
        this.description = description;
    }
}
