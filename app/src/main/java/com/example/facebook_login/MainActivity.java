package com.example.facebook_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private TextView e;
    private LoginButton b;
    private CallbackManager callbackManager;
    private ProfilePictureView p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        b = findViewById(R.id.login_button);
        e = findViewById(R.id.tv_name);
        p = findViewById(R.id.userProfilePicture);


        callbackManager = CallbackManager.Factory.create();

        b.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    AccessTokenTracker t = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null){
                e.setText("Chào mừng bạn đến với Facebook");
                p.setProfileId(null);
                Toast.makeText(MainActivity.this, "Logout complete", Toast.LENGTH_SHORT).show();
            }
            else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, (object, response) -> {
            if (object != null)
            {
                try {
                    String name = object.getString("name");
                    p.setProfileId(Profile.getCurrentProfile().getId());
                    e.setText(name);
                }
                 catch (JSONException ex) {
                    ex.printStackTrace();
                }}
        });
        Bundle paraBundle= new Bundle();
        paraBundle.putString("fields", "name");
        request.setParameters(paraBundle);
        request.executeAsync();
    }
}