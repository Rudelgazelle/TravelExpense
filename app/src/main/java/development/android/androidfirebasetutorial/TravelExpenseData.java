package development.android.androidfirebasetutorial;

import java.util.List;

public class TravelExpenseData {

    public String description;
    public Float expenseAmount;
    public String expenseType;
    public String location;
    public String travelDate;

    public TravelExpenseData(){

    }

    public TravelExpenseData(String description,Float expenseAmount, String expenseType, String location, String travelDate ) {

        this.description = description;
        this.expenseAmount = expenseAmount;
        this.expenseType = expenseType;
        this.location = location;
        this.travelDate = travelDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Float expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }


}
