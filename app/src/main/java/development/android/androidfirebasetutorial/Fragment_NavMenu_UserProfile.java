package development.android.androidfirebasetutorial;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class Fragment_NavMenu_UserProfile extends Fragment {


    //--------------------------------------
    //--------------------------------------
    // TO-DO: implement upload for profile picture
    //--------------------------------------


    private static final String TAG = "userProfileTAG";

    String userDisplayName;

    EditText etUserName;
    TextView tvUserMail;
    Button buttonSave;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //the the header name in actionbar
        getActivity().setTitle("User Profile");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflate the fragment layout
        View fragmentView = inflater.inflate(R.layout.fragment_nav_menu_userprofile, container, false);

        //Initialize views
        etUserName = (EditText)fragmentView.findViewById(R.id.editTextUserName);
        tvUserMail = (TextView)fragmentView.findViewById(R.id.textViewUserEmail);
        buttonSave = (Button)fragmentView.findViewById(R.id.buttonSaveProfile);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Update the user profile with the displayed name entered in the editView
                userDisplayName = etUserName.getText().toString().trim();
                //Call the method to update the data at firebase
                updateUserProfile();
            }
        });

        return fragmentView;
    }

    private void initializeUserData(){

        //get the userdata from firebase
        UserInformation userInformation = new UserInformation();
        userInformation.getUserData();

        //If userName exist set existing username
        if (userInformation.userName != null){

            etUserName.setText(userInformation.userName);
        }else {
            etUserName.setHint("Enter a user name...");
        }

        // set the registrated mailadress of the user
        tvUserMail.setText(userInformation.userMail);
    }

    private void updateUserProfile(){

        final ProgressDialog progressDialogSave;
        progressDialogSave = new ProgressDialog(getContext());
        progressDialogSave.setMessage("Saving user data...");
        progressDialogSave.show();

        //Get the current User
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //request a change for Username and profile picture
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userDisplayName)
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        //update the changes requested above
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "User profile updated!",Toast.LENGTH_LONG).show();
                            Log.d(TAG, "User profile updated.");
                            //dismiss the progress dialog
                            progressDialogSave.dismiss();
                        }
                    }
                });

    }

}
