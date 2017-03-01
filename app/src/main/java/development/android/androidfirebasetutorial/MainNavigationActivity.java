package development.android.androidfirebasetutorial;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "myTAG";

    //Firebase auth object
    private FirebaseAuth firebaseAuth;
    private TextView textViewDisplayUserMail;
    private TextView textViewDisplayUserName;
    private Button btnLogout;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize floating action button and add onclicklistener
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open TravelExpenseActivity
                finish();
                //starting login activity
                startActivity(new Intent(MainNavigationActivity.this, TravelExpenseActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //--------------------------------// ES KÖNNTE SEIN, DASS DIESE METHODE FÜR DIE USRDATEN ZU SPÄT ABGEFRAGT WIRD UND DESHALB IMMER BEIM START DIER ERROR KOMMT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //* check if the user is already logged in*//*
        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null){
            //*User is not logged in*//*
            //closing this activity
            finish();
            //* start login activity*//*
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        if (firebaseAuth.getCurrentUser() != null) {
            //*User is logged in*//*


            //----------------------------------------------------------------------------------------------------------------------------------------------------------
            // UPDATE USER PROFILE DATA FÜR TESTZWECKE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Diese METHODE KANN NOCH SEPERAT IN EINER NEUEN AKTIVITÄT "Userprofile" gelistet werden!!!! MUSS NICHT JEDES MAL AUSGEFÜHRT WERDEN
            //----------------------------------------------------------------------------------------------------------------------------------------------------------

            //Get the current User
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            //request a change for Username and profile picture
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Lars Bürkner")
                    .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                    .build();

            //update the changes requested above
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                            }
                        }
                    });

            //----------------------------------------------------------------------------------------------------------------------------------------------------------
            //----------------------------------------------------------------------------------------------------------------------------------------------------------

            UserInformation userInformation = new UserInformation();
            userInformation.getUserData();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            //Inflate the layout from NavigationView (to call the TextView at the right location
            //View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
            View navHeaderView = navigationView.getHeaderView(0);
            textViewDisplayUserName = (TextView) navHeaderView.findViewById(R.id.tvDisplayUserName);
            textViewDisplayUserMail = (TextView) navHeaderView.findViewById(R.id.tvDisplayUserMail);
            btnLogout = (Button) navHeaderView.findViewById(R.id.btnLogout);

            //sets onclick listener to Logout button
            btnLogout.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View view) {
                    if (view == btnLogout) {
                        //logging out the user
                        firebaseAuth.signOut();
                        Toast.makeText(MainNavigationActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
                        //closing activity
                        finish();
                        //starting login activity
                        startActivity(new Intent(MainNavigationActivity.this, LoginActivity.class));
                    }
                }
            });

            textViewDisplayUserName.setText(userInformation.userName);
            textViewDisplayUserMail.setText(userInformation.userMail);

            /* NAVIGATION DRAWER: Show a specific fragment as start screen upon loading (IN THIS CASE THE EXPENSEHISTORY FRAGMENT)*/
            displaySelectedScreen(R.id.nav_ExpenseHistory);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //call the method and parse the id of the selected item
        displaySelectedScreen(id);

        return true;
    }

    private void displaySelectedScreen(int id){

        //Initialize a new instance of fragment
        Fragment fragment = null;

        //define cases to determine item(navigation) ID // THIS CAN BE EXPANDED WITH FURTHER ITEMS OF THE NAVIGATIONBAR
        switch (id){
            case R.id.nav_ExpenseHistory:
                fragment = new Fragment_NavigationMenu_ExpenseHistory();
                break;
        }

        if (fragment != null){
            //create a FragmentTransaction that changes the displayed fragment // This initiates the screenswitching action
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //Replace the fragment within the main navigation layout
            ft.replace(R.id.content_main_navigation, fragment);
            //commit the changes
            ft.commit();
        }

        //the navigation drawer will be closed after selecting an item
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void onClick(View view) {

        //if logout is pressed
        if (view == btnLogout){
            //logging out the user
            firebaseAuth.signOut();
            Toast.makeText(MainNavigationActivity.this,"Logout successful",Toast.LENGTH_SHORT).show();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
    }



}
