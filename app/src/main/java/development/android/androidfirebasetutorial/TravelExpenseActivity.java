package development.android.androidfirebasetutorial;

import android.content.Intent;
import android.support.design.internal.NavigationMenuView;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TravelExpenseActivity extends AppCompatActivity {

    private Spinner spinnerExpenseType;
    private EditText editTextTravelDate, editTextExpenseAmount ,editTextTravelDescription, editTextTravelLocation;
    private Button btnSave;

    private String travelDate;

    private FirebaseAuth firebaseAuth;

    //Database Objects
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense);

        //enable Back-Button in ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //initializing firebase Database object;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Initializing view objects
        editTextTravelDate = (EditText) findViewById(R.id.editTextDate);
        editTextExpenseAmount = (EditText) findViewById(R.id.editTextExpenseAmount);
        editTextTravelDescription = (EditText)findViewById(R.id.editTextDescr);
        editTextTravelLocation = (EditText) findViewById(R.id.editTextLocation);

        // Set the spinner Items
        spinnerExpenseType = (Spinner)findViewById(R.id.spinnerExpenseType);
        String[] items = new String[]{"Taxi", "Train", "Food"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerExpenseType.setAdapter(adapter);

        //Set default Travel date as today
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        travelDate = df.format(calendar.getTime());
        editTextTravelDate.setText(travelDate);


        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create child in root object
                //Assign some value to the child object
                SaveTravelExpenseData();
            }
        });
    }

    /* Uses the Firebase Database to store data */
    private void SaveTravelExpenseData(){

        travelDate = editTextTravelDate.getText().toString().trim();
        String expenseType = spinnerExpenseType.getSelectedItem().toString();
        Float expenseAmount = Float.valueOf(editTextExpenseAmount.getText().toString().trim());
        String location = editTextTravelLocation.getText().toString().trim();
        String description = editTextTravelDescription.getText().toString().trim();

        //define object for the class UserInformation
        TravelExpenseData travelExpenseData = new TravelExpenseData(description, expenseAmount, expenseType, location, travelDate);

        //Get the unique ID of the logged in user to store data in the Firebase Database
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Set the databasereference to the unique Id of the current user and pass the Userinformation to the database
        databaseReference.child(user.getUid()).child("TravelExpenseData").push().setValue(travelExpenseData);

        //NEU NACH DEM TUTORIAL HINZUGEFÜGT
        /* The TestEdit fields are being emptied after the database storage*/
        editTextTravelDate.setText("");
        editTextExpenseAmount.setText("");
        editTextTravelDescription.setText("");
        editTextTravelLocation.setText("");

        //Show message that the information was saved
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Enables back navigation via (physical) back press on mobile
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Close the activity and go back to main activity
        finish();
        startActivity(new Intent(this, MainNavigationActivity.class));

    }
}
