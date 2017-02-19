package development.android.androidfirebasetutorial;

import android.content.Intent;
import android.support.design.internal.NavigationMenuView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TravelExpenseActivity extends AppCompatActivity {

    private EditText editTextDate;
    private Spinner spinnerExpenseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense);

        editTextDate = (EditText) findViewById(R.id.editTextDate);

        // Set the spinner Items
        spinnerExpenseType = (Spinner)findViewById(R.id.spinnerExpenseType);
        String[] items = new String[]{"Taxi", "Train", "Food"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerExpenseType.setAdapter(adapter);

        //Set default Travel date as today
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String travelDate = df.format(calendar.getTime());
        editTextDate.setText(travelDate);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Close the activity and go back to main activity
        finish();
        startActivity(new Intent(this, MainNavigationActivity.class));

    }
}
