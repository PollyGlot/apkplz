package com.example.pollyglot.apkplz.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pollyglot.apkplz.MainActivity;
import com.example.pollyglot.apkplz.R;
import com.example.pollyglot.apkplz.models.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "APKPLZ";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private User user;
    private EditText login;
    private EditText email;
    private EditText password;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;
    private Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClick(view);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        login = (EditText) findViewById(R.id.signup_login);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //This method sets up a new User by fetching the user entered details.
    protected void setUpUser() {
        user = new User();
        user.setLogin(login.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
    }

    public void onSignUpClick(View view) {
        createNewAccount(email.getText().toString(), password.getText().toString());
        showProgressDialog();
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createNewAccount(String email, String password) {
        Log.d(TAG, "createNewAccount:" + email);
        if (!validateForm()) {
            return;
        }
        setUpUser();

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            onAuthenticationSuccess(task.getResult().getUser());
                        }
                    }
                });
    }

    private void onAuthenticationSuccess(FirebaseUser mUser) {
        // Write new user
        saveNewUser(mUser.getUid(), user.getLogin(), user.getEmail(), user.getPassword(), user.getAvatar());
        startActivity(new Intent(SignupActivity.this, MainActivity.class));
        finish();
    }

    private void saveNewUser(String userId, String name, String email, String password, String avatar) {
        User user = new User(userId,name,email,password, avatar);

        mDatabaseReference.child("users").child(userId).setValue(user);
    }

//    private void signOut() {
//        FirebaseAuth.getInstance().signOut();
//        LoginManager.getInstance().logOut();
//    }

    private boolean validateForm() {
        boolean valid = true;

        String userName = login.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (userName.isEmpty() || userName.length() < 3) {
            login.setError("at least 3 characters");
            valid = false;
        } else {
            login.setError(null);
        }

        if (userEmail.isEmpty()) {
            email.setError("At least 3 characters.");
            valid = false;
        } else {
            email.setError(null);
        }

        if (userPassword.isEmpty() || userPassword.length() < 4) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }






    //    private EditText mInputEmail, mInputPassword;
//    private Button btnLogIn, btnSignUp, btnResetPassword;
//    private ProgressBar mProgressBar;
//    private FirebaseAuth mFirebaseAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        //Get Firebase mFirebaseAuth instance
//        mFirebaseAuth = FirebaseAuth.getInstance();
//
//        btnLogIn = (Button) findViewById(R.id.login_in_button);
//        btnSignUp = (Button) findViewById(R.id.sign_up_button);
//        mInputEmail = (EditText) findViewById(R.id.email);
//        mInputPassword = (EditText) findViewById(R.id.password);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
//
//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
//            }
//        });
//
//        btnLogIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent Intent = new Intent(v.getContext(), LoginActivity.class);
//                v.getContext().startActivity(Intent);
//            }
//        });
//
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String email = mInputEmail.getText().toString().trim();
//                String password = mInputPassword.getText().toString().trim();
//
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                mProgressBar.setVisibility(View.VISIBLE);
//                //create user
//                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                                mProgressBar.setVisibility(View.GONE);
//                                // If sign in fails, display a message to the user. If sign in succeeds
//                                // the mFirebaseAuth state listener will be notified and logic to handle the
//                                // signed in user can be handled in the listener.
//                                if (!task.isSuccessful()) {
//                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
//                                            Toast.LENGTH_SHORT).show();
//                                } else {
//                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                                    finish();
//                                }
//                            }
//                        });
//
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mProgressBar.setVisibility(View.GONE);
//    }
}