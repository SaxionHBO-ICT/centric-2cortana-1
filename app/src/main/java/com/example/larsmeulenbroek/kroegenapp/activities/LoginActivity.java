package com.example.larsmeulenbroek.kroegenapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.Model.Route;
import com.example.larsmeulenbroek.kroegenapp.Model.User;
import com.example.larsmeulenbroek.kroegenapp.Model.UserModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.CreateUserTask;
import com.example.larsmeulenbroek.kroegenapp.Tasks.GetFavoritesTask;
import com.example.larsmeulenbroek.kroegenapp.Tasks.GetRoutesTask;
import com.example.larsmeulenbroek.kroegenapp.Tasks.GetUsers;
import com.example.larsmeulenbroek.kroegenapp.Tasks.LoadProfile;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
Activity where a user can login or register with his google+ account
 */
public class LoginActivity extends AppCompatActivity implements OnConnectionFailedListener, View.OnClickListener, ConnectionCallbacks {

    //devine all variables
    private GoogleApiClient google_api_client;
    private GoogleApiAvailability google_api_availability;
    private SignInButton signIn_btn;

    private static final int SIGN_IN_CODE = 0;
    private static final int PROFILE_PIC_SIZE = 120;

    private ConnectionResult connection_result;
    private boolean is_intent_inprogress;
    private boolean is_signInBtn_clicked;
    private int request_code;

    private ProgressDialog progress_dialog;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buidNewGoogleApiClient();
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        //Customize sign-in button.a red button may be displayed when Google+ scopes are requested
        custimizeSignBtn();
        setBtnClickListeners();
        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Signing in....");

        //devine all id's
        FrameLayout flfavorites = (FrameLayout) findViewById(R.id.flFavorites);
        FrameLayout flmyroutes = (FrameLayout) findViewById(R.id.flMyRoutes);
        Button btnsignout = (Button) findViewById(R.id.btn_signout);

        //null asserts
        assert flfavorites != null;
        assert flmyroutes != null;
        assert btnsignout != null;

        try {
            flfavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BarModel.clearFavoriteList();
                    Intent intent = new Intent(LoginActivity.this, FavoriteListActivity.class);
                    startActivity(intent);
                }
            });
            flmyroutes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, MyRouteActivity.class);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

        //when clicked the user signs out and all data will be reset
        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel.loggedInUser = new User();
                Toast.makeText(getApplicationContext(), "Sign Out from G+", Toast.LENGTH_SHORT).show();
                for (Bar b : BarModel.getInstance()) {
                    if (b.isFavorite()) {
                        b.setFavorite();
                    }
                }
                gPlusSignOut();
            }
        });
    }

    /*
    create and  initialize GoogleApiClient object to use Google Plus Api.
    While initializing the GoogleApiClient object, request the Plus.SCOPE_PLUS_LOGIN scope.
    */

    private void buidNewGoogleApiClient() {
        google_api_client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
    }

    /*
      Customize sign-in button. The sign-in button can be displayed in
      multiple sizes and color schemes. It can also be contextually
      rendered based on the requested scopes. For example. a red button may
      be displayed when Google+ scopes are requested, but a white button
      may be displayed when only basic profile is requested. Try adding the
      Plus.SCOPE_PLUS_LOGIN scope to see the  difference.
    */

    private void custimizeSignBtn() {
        assert signIn_btn != null : "button is null";
        signIn_btn = (SignInButton) findViewById(R.id.sign_in_button);
        signIn_btn.setSize(SignInButton.SIZE_STANDARD);
        signIn_btn.setScopes(new Scope[]{Plus.SCOPE_PLUS_LOGIN});

    }

    /*
      Set on click Listeners on the sign-in sign-out and disconnect buttons
     */

    private void setBtnClickListeners() {
        signIn_btn.setOnClickListener(this);
    }

    protected void onStart() {
        super.onStart();
        google_api_client.connect();
    }

    protected void onStop() {
        super.onStop();
        if (google_api_client.isConnected()) {
            google_api_client.disconnect();
        }
    }

    protected void onResume() {
        super.onResume();
        if (google_api_client.isConnected()) {
            google_api_client.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            google_api_availability.getErrorDialog(this, result.getErrorCode(), request_code).show();
            return;
        }

        if (!is_intent_inprogress) {

            connection_result = result;

            if (is_signInBtn_clicked) {

                resolveSignInError();
            }
        }

    }

    /*
      Will receive the activity result and check which request we are responding to
     */
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        // Check which request we're responding to
        if (requestCode == SIGN_IN_CODE) {
            request_code = requestCode;
            if (responseCode != RESULT_OK) {
                is_signInBtn_clicked = false;
                progress_dialog.dismiss();
            }

            is_intent_inprogress = false;

            if (!google_api_client.isConnecting()) {
                google_api_client.connect();
            }
        }

    }

    @Override
    public void onConnected(Bundle arg0) {
        is_signInBtn_clicked = false;

        // Get user's information and set it into the layout
        getProfileInfo();

        // Update the UI after signin
        changeUI(true);

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        google_api_client.connect();
        changeUI(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Toast.makeText(this, "start sign process", Toast.LENGTH_SHORT).show();
                gPlusSignIn();
                break;
        }
    }

    /*
      Sign-in into the Google + account
     */

    private void gPlusSignIn() {
        if (!google_api_client.isConnecting()) {
            Log.d("user connected", "connected");
            is_signInBtn_clicked = true;
            progress_dialog.show();
            resolveSignInError();
        }
    }


    /*
      Method to resolve any signin errors
     */

    private void resolveSignInError() {
        if (connection_result.hasResolution()) {
            try {
                is_intent_inprogress = true;
                connection_result.startResolutionForResult(this, SIGN_IN_CODE);
                Log.d("resolve error", "sign in error resolved");
            } catch (SendIntentException e) {
                is_intent_inprogress = false;
                google_api_client.connect();
            }
        }
    }

    /*
      Sign-out from Google+ account
     */

    private void gPlusSignOut() {
        if (google_api_client.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(google_api_client);
            google_api_client.disconnect();
            google_api_client.connect();
            changeUI(false);
        }
    }

    /*
     get user's information name, email and profile picture, also load favorites, routes for user
     */

    private void getProfileInfo() {
        LoadProfile loadProfileTask = new LoadProfile(new LoadProfile.AsyncResponse() {
            @Override
            public void processFinish(final Person person) {
                GetUsers getUsers = new GetUsers(new GetUsers.AsyncResponse() {
                    @Override
                    public void processFinish(Boolean isAnUser) {
                        //user already exitst in database
                        if (isAnUser) {
                            UserModel.loggedInUser.setEmail(Plus.AccountApi.getAccountName(google_api_client));
                            UserModel.loggedInUser.setUsername(person.getDisplayName());
                            GetFavoritesTask getFavoritesTask = new GetFavoritesTask();
                            getFavoritesTask.execute(UserModel.loggedInUser.getEmail());
                            GetRoutesTask getRoutesTask = new GetRoutesTask();
                            getRoutesTask.execute(UserModel.loggedInUser.getEmail());

                        } else {
                            //create a new User in database
                            CreateUserTask createUserTask = new CreateUserTask();
                            createUserTask.execute(Plus.AccountApi.getAccountName(google_api_client));
                            UserModel.loggedInUser.setEmail(Plus.AccountApi.getAccountName(google_api_client));
                            UserModel.loggedInUser.setUsername(person.getDisplayName());
                            List<Bar> favorites = new ArrayList<>();
                            List<Route> myRoutes = new ArrayList<>();
                            UserModel.loggedInUser.setFavorites(favorites);
                            UserModel.loggedInUser.setMyRoutes(myRoutes);
                        }
                    }
                });
                getUsers.execute(Plus.AccountApi.getAccountName(google_api_client));
                setPersonalInfo(person);
            }

        }, this);
        loadProfileTask.execute(google_api_client);
    }

    /*
     set the User information into the views defined in the layout
     */

    private void setPersonalInfo(Person currentPerson) {
        String personName = currentPerson.getDisplayName();
        String personPhotoUrl = currentPerson.getImage().getUrl();
        String email = Plus.AccountApi.getAccountName(google_api_client);

        //devine id's
        TextView user_name = (TextView) findViewById(R.id.tvProfileName);
        TextView gemail_id = (TextView) findViewById(R.id.tvProfileEmail);

        //null asserts
        assert user_name != null;
        assert gemail_id != null;

        user_name.setText(personName);
        gemail_id.setText(email);

        //create his profile picture from url
        setProfilePic(personPhotoUrl);

        progress_dialog.dismiss();

        Toast.makeText(this, "Person information is shown!", Toast.LENGTH_LONG).show();
    }

    /*
     By default the profile pic url gives 50x50 px image.
     If you need a bigger image we have to change the query parameter value from 50 to the size you want
    */

    private void setProfilePic(String profile_pic) {
        profile_pic = profile_pic.substring(0,
                profile_pic.length() - 2)
                + PROFILE_PIC_SIZE;
        ImageView user_picture = (ImageView) findViewById(R.id.profile_pic);
        new LoadProfilePic(user_picture).execute(profile_pic);
    }

    /*
     Show and hide of the Views according to the user login status
     */

    private void changeUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

   /*
    Perform background operation asynchronously, to load user profile picture with new dimensions from the modified url
    */

    private class LoadProfilePic extends AsyncTask<String, Void, Bitmap> {

        ImageView bitmap_img;

        public LoadProfilePic(ImageView bitmap_img) {
            this.bitmap_img = bitmap_img;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap new_icon = null;
            try {
                InputStream in_stream = new java.net.URL(url).openStream();
                new_icon = BitmapFactory.decodeStream(in_stream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return new_icon;
        }

        protected void onPostExecute(Bitmap result_img) {
            bitmap_img.setImageBitmap(result_img);
        }
    }
}
