package mk.beauty.services.parlours.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.ServiceProviders.LoginServiceProviderActivity;
import mk.beauty.services.parlours.ServiceProviders.RegisterServiceProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText UserEmail, UserPassword;
    private Button LoginButton;
    private ProgressDialog LoadingBar;
    private TextView NeedNewAccountLink;
    private FirebaseAuth mAuth;
    private ImageView googleSigninButton;

    private static int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleSignInClient;
    private static  final String TAG="loginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        UserEmail=(EditText) findViewById(R.id.login_email_input);
        UserPassword=(EditText) findViewById(R.id.login_password_input);
        LoginButton=(Button) findViewById(R.id.login_btn);
        NeedNewAccountLink=(TextView) findViewById(R.id.register_account_link);

        googleSigninButton=(ImageView)findViewById(R.id.google_signin_btn);

        LoadingBar= new ProgressDialog(this);



        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendUserToRegisterActivity();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AllowingUserToLogin();

            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(LoginActivity.this, "Connection to Google Signin Failed ", Toast.LENGTH_SHORT).show();


                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        googleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signIn();

            }
        });


    }
    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            LoadingBar.setTitle("Google Sign In");
            LoadingBar.setMessage("Please wait while we are allowing you to login using your Google Account..");
            LoadingBar.setCanceledOnTouchOutside(true);
            LoadingBar.show();


            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess())
            {
                GoogleSignInAccount account =result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(this, "Please wait while we are getting your Auth result", Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(this, "Can't get Auth result", Toast.LENGTH_SHORT).show();
                LoadingBar.dismiss();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithCredential:success");

                            SendUserToHomeActivity();
                            LoadingBar.dismiss();

                        } else
                            {


                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            String message=task.getException().toString();
                            SendUserToLoginActivity();
                            Toast.makeText(LoginActivity.this, "Not Authenticated :" + message, Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                        }


                    }
                });
    }

    private void SendUserToLoginActivity()
    {
        Intent loginitent=new Intent(LoginActivity.this,LoginActivity.class);
        loginitent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginitent);
        finish();
    }

    @Override
    protected void onStart()
    {

        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();

        if (currentUser!=null)
        {
            SendUserToHomeActivity();
        }
    }

    private void AllowingUserToLogin()
    {
        String email=UserEmail.getText().toString();
        String password=UserPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email", Toast.LENGTH_SHORT).show();
        }
        else   if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.setTitle("Login");
            LoadingBar.setMessage("Please wait while we are allowing you to login into your Account..");
            LoadingBar.setCanceledOnTouchOutside(true);
            LoadingBar.show();


            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                SendUserToHomeActivity();

                                Toast.makeText(LoginActivity.this, "You are logged in successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                            }
                        }
                    });
        }
    }

    private void SendUserToHomeActivity()
    {
        Intent homeintent=new Intent(LoginActivity.this,HomeActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeintent);
        finish();
    }


    private void SendUserToRegisterActivity()
    {
        Intent registerIntent=new Intent(LoginActivity.this, Signup.class);
        startActivity(registerIntent);
     
    }
}