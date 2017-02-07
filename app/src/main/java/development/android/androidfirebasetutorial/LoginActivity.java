package development.android.androidfirebasetutorial;

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
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        /* Check if the current user is already logged in; if not, log in*/
        if (firebaseAuth.getCurrentUser() != null){
            //profile activity here
        }

        /* Link the views to the Interface objects / Initialize */
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void userlogin(){
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
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        /* call method sign in with mail and password by firebaseAuth; Declaration of the FirebaseAuth Instance is placed above*/
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            // start the profile activity
                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn){
            userlogin();
        }

        if (view == textViewSignUp){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
