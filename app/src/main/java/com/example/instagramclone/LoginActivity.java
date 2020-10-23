package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i(TAG, "login button clicked");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username,password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i(TAG, "signup button clicked");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signupUser(username,password);
            }
        });

    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "attempting to log in");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!= null) {
                    if (e.getCode() == 101 ) {
                        Toast.makeText(LoginActivity.this, "Incorrect username or password",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Log.e(TAG, "issue with log in " + e.getCode() + e);
                    return;
                }
                //successful login - navigate to main activity
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Logging in",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signupUser(String username, String password) {
        Log.i(TAG, "attempting to sign up");
        final ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e!= null) {
                    if (e.getCode() == 202 ) {
                        Log.e(TAG, "caught login error " + e);
                        Toast.makeText(LoginActivity.this, "Account already exists with this username",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Try again", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "issue with sign up " + e.getCode() + e);
                    return;
                }

                Log.i(TAG, "User created successfully: " + user.getUsername());
                Toast.makeText(LoginActivity.this, "Sign up successful!", Toast.LENGTH_LONG).show();
                // Hooray! Let them use the app now.

            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish(); //remove login activity from stack
    }
}
