package development.android.androidfirebasetutorial;

import java.util.Date;

/**
 * Created by lbuer on 19.02.2017.
 */

public class TravelExpenseData {

    public Date travelDate;
    public String expenseType;
    public Float expenseAmount;
    public String location;
    public String description;



    public TravelExpenseData(Date travelDate, String expenseType, Float expenseAmount, String location, String description) {

        this.travelDate = travelDate;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.location = location;
        this.description = description;
    }
}
