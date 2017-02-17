package development.android.androidfirebasetutorial;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by lbuer on 08.02.2017.
 */

public class UserInformation {

    public String name;
    public String address;

    public String userMail;
    public String userName;
    public String userName2;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;


    public UserInformation(){

    }

    //Generated constructor (done via rightclick)
    public UserInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void getUserData(){

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userMail = user.getEmail();

        mDatabase = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("name"); // PRÜFEN OB CHILD KORREKT IST!!!!!!!

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieves the value of the datasnapshot and assignes it to String "userMail"
                userName = dataSnapshot.getValue().toString();
                userName2 = "Test Name";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //Show error message
            }
        });
    }
}
