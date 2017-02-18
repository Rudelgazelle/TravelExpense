package development.android.androidfirebasetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private TextView textViewDisplayUserMail;
    private TextView textViewDisplayUserName;
    private Button btnLogout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //--------------------------------------------------------------------------------------

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

        if (firebaseAuth.getCurrentUser() != null){
            //*User is logged in*//*

            UserInformation userInformation = new UserInformation();
            userInformation.getUserData();

            //FirebaseUser user = firebaseAuth.getCurrentUser();
            //String mail = user.getEmail();

            //String name = "Lars" ;

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

            //if the variable is still empty, then pass a default value
            if (userInformation.userName == null){
                userInformation.userName = "Default User";
            }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
