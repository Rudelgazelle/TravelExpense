package development.android.androidfirebasetutorial;

import android.content.Intent;
import android.support.design.internal.NavigationMenuView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

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

    // DIESE METHODEN MÜSSEN NOCH ANGEPASST WERDEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    /* Uses the Firebase Database to store data */
    private void SaveTravelExpenseData(){

        String name = editTextName.getText().toString().trim();
        String address = editTextAdress.getText().toString().trim();

        //define object for the class UserInformation
        UserInformation userInformation = new UserInformation(name, address);

        //Get the unique ID of the logged in user to store data in the Firebase Database
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Set the databasereference to the unique Id of the current user and pass the Userinformation to the database
        databaseReference.child(user.getUid()).setValue(userInformation);

        //NEU NACH DEM TUTORIAL HINZUGEFÜGT
        /* The TestEdit fields are being emptied after the database storage*/
        editTextName.setText("");
        editTextAdress.setText("");

        //Show message that the information was saved
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();

    }

    public void onClick(View view) {

        //if logout is pressed
        if (view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            Toast.makeText(ProfileActivity.this,"Logout successful",Toast.LENGTH_SHORT).show();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //if Save Information is pressed
        if (view == buttonSave){
            // call the method SaveUSerInformation
            SaveUserInformation();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Close the activity and go back to main activity
        finish();
        startActivity(new Intent(this, MainNavigationActivity.class));

    }
}
