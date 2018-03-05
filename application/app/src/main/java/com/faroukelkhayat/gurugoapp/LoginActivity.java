package com.faroukelkhayat.gurugoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button registerBtn;
    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerBtn =  findViewById(R.id.signupBtn);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        auth = FirebaseAuth.getInstance();

        //Opens registration activity
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        emailInputEditText =  findViewById(R.id.emailTextInputEditText);
        passwordInputEditText = findViewById(R.id.passLoginEditText);

    }

    //Authenticate user credentials
    public void loginUser(View view){
        String email = emailInputEditText.getText().toString();
        String password = passwordInputEditText.getText().toString();

        //Regex to make sure email address is in correct format
        String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(email);

        if (!match.matches()){
            emailInputEditText.setError("Error: Incorrect E-mail format");
            return;
        }

        //Guarantee password is not empty
        if (password.equals("")){
            passwordInputEditText.setError("Error: Password missing");
            return;
        }

        //Custom dialog with progress bar while authenticating over network
        View progressBar = getLayoutInflater().inflate(R.layout.progressbar_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
        dialog.setView(progressBar);
        dialog.show();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

                        //Inform user of error or open Guru Maps Activity
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Error: Bad username or password", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(LoginActivity.this, GuruMapsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

    }
}
