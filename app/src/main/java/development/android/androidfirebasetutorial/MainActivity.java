package development.android.androidfirebasetutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    /*DECLARATION OF variables*/
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;

    private TextView textViewSignin;

    private ProgressDialog progressDialog; // will be shown during database interactions

    private FirebaseAuth firebaseAuth; // Difinging Firebase Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create a new instance of the Firebase Authentication*/
        firebaseAuth = FirebaseAuth.getInstance();

        /* If the current user is logged in show the ProfileActivity*/
        if (firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //open profile activity here
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        /* initialize views */
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        /* Create a new instance of the progressbar*/
        progressDialog = new ProgressDialog(this);


        /* ATTACH OnClickListener TO THE BUTTON */
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        /* Check if the strings we want to receive are empty*/
        if (TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            // stopping the function execution
            return;
        }

        if (TextUtils.isEmpty(password)){
            //Password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            // stopping the function execution
            return;
        }

        /*IF VALIDATIONS ARE OK A PROGRESS BAR IS BEING SHOWN*/
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Check if successful
                        if (task.isSuccessful()) {
                            //user is successfully registered and logged in
                            //we will start the profile activity here
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                            progressDialog.dismiss(); // HAB ICH IM NACHHINEIN NOCH EINGEFÜGT!!!!!!!!!!!
                            Toast.makeText(MainActivity.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                        }else {
                            //display some message here
                            Toast.makeText(MainActivity.this,"Could not register. Please try again.",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    /* CHECKS WHICH FIELD HAS BEEN CLICKED AND EXECUTES METHOD */
    @Override
    public void onClick(View view) {

        if (view == buttonSignup){

            registerUser();
        }

        if (view == textViewSignin){

            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, LoginActivity.class));


        }
    }
}
