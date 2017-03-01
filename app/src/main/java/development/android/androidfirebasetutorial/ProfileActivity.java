package development.android.androidfirebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    //Database Objects
    private DatabaseReference databaseReference;
    private EditText editTextName, editTextAdress;
    private Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        /* check if the user is already logged in*/
        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null){
            /*User is not logged in*/
            //closing this activity
            finish();
            /* start login activity*/
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Initialize FirebaseDatabase /* get the instance of the account that is logged in??? */
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //Initialize the two data fields and the Save button
        editTextAdress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        /* If the user is loggend in proceed with this code*/
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    /* Uses the Firebase Database to store data */
    private void SaveUserInformation(){

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


    @Override
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
}
