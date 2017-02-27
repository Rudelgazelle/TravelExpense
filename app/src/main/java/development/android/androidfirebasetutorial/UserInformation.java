package development.android.androidfirebasetutorial;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInformation {

    public String name;
    public String address;

    public String userMail;
    public String userName;

    private FirebaseAuth firebaseAuth;

    public UserInformation(){

    }

    // -----------------------------------------------------------------------------------------------------------------------------
    //HIER KÖNNTE AUCH NOCH DIE METHODE REIN UM DIE USERDATEN UPZUDATEN
    //------------------------------------------------------------------------------------------------------------------------------

    //Generated constructor (done via rightclick)
    public UserInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void getUserData(){

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        // GET name and mail adress and parse it into variables
        userMail = currentUser.getEmail();
        userName = currentUser.getDisplayName();
    }
}
