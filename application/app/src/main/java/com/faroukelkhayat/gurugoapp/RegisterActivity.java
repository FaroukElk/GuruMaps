package com.faroukelkhayat.gurugoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Farouk on 3/2/2018.
 */

public class RegisterActivity extends AppCompatActivity{

    private TextInputEditText emailInputEditText;
    private TextInputEditText passwordInputEditText;
    private TextInputEditText passConfirmInputEditText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");

        auth = FirebaseAuth.getInstance();

        emailInputEditText = findViewById(R.id.emailRegisterEditText);
        passwordInputEditText = findViewById(R.id.pass1EditText);
        passConfirmInputEditText = findViewById(R.id.pass2EditText);

    }

    public void registerAccount(View view){
        String email = emailInputEditText.getText().toString();
        String password = passwordInputEditText.getText().toString();
        String passConfirm = passConfirmInputEditText.getText().toString();

        //Regex to make sure email address is in correct format
        String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(email);

        if (!match.matches()){
            emailInputEditText.setError("Error: Incorrect E-mail format");
            return;
        }

        if (!password.equals(passConfirm)){
            passConfirmInputEditText.setError("Error: Passwords do not match");
            passwordInputEditText.setError("Error: Passwords do not match");
            return;
        }

        View progressBar = getLayoutInflater().inflate(R.layout.progressbar_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
        dialog.setView(progressBar);
        dialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

                        if (!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Authentication failed: " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Authentication successful. Please login.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                });
    }

}
