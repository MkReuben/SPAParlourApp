package mk.beauty.services.parlours.ServiceProviders;

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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.Users.HomeActivity;
import mk.beauty.services.parlours.Users.LoginActivity;
import mk.beauty.services.parlours.Users.Signup;

public class LoginServiceProviderActivity extends AppCompatActivity {
    private EditText serviceproviderEmail, serviceProviderPassword;
    private Button serviceProviderLoginbtn;
    private ImageView google;
    private ProgressDialog LoadingBar;
    private TextView RegisterNewAccount;
    private FirebaseAuth mAuth;

    private static int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleSignInClient;
    private static  final String TAG="loginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_service_provider);
        mAuth=FirebaseAuth.getInstance();

        serviceproviderEmail =(EditText) findViewById(R.id.service_provider_login_email_input);
        serviceProviderPassword =(EditText) findViewById(R.id.service_provider_login_password_input);
        serviceProviderLoginbtn =(Button) findViewById(R.id.service_provider_login_btn);
        RegisterNewAccount =(TextView) findViewById(R.id.service_provider_register_account_link);
        google=findViewById(R.id.google_signin_btn_sp);



        LoadingBar= new ProgressDialog(this);

        RegisterNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent =new Intent(LoginServiceProviderActivity.this, RegisterServiceProvider.class);
                startActivity(intent);

            }
        });

        RegisterNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendServiceProviderToRegisterActivity();
            }
        });

        serviceProviderLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AllowServiceProviderToLogin();

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
                        Toast.makeText(LoginServiceProviderActivity.this, "Connection to Google Signin Failed ", Toast.LENGTH_SHORT).show();


                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        google.setOnClickListener(new View.OnClickListener() {
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

                            sendServiceProviderToHomeActivity();
                            LoadingBar.dismiss();

                        } else {


                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            String message = task.getException().toString();
                            SendUserToLoginActivity();
                            Toast.makeText(LoginServiceProviderActivity.this, "Not Authenticated :" + message, Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();
                        }


                    }
                });

    }

    private void SendUserToHomeActivity()
    {
        Intent homeintent=new Intent(LoginServiceProviderActivity.this,SpHomeActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeintent);
        finish();

    }

    private void SendUserToLoginActivity()
        {
            Intent loginitent=new Intent(LoginServiceProviderActivity.this,LoginServiceProviderActivity.class);
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
            sendServiceProviderToHomeActivity();
        }
    }

    private void AllowServiceProviderToLogin()
    {
        String email= serviceproviderEmail.getText().toString();
        String password= serviceProviderPassword.getText().toString();

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
            LoadingBar.setMessage("Please wait while we are allowing you to login into your Account.");
            LoadingBar.show();
            LoadingBar.setCanceledOnTouchOutside(true);

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                sendServiceProviderToHomeActivity();

                                Toast.makeText(LoginServiceProviderActivity.this, "You are logged in successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(LoginServiceProviderActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                            }
                        }
                    });
        }
    }

    private void sendServiceProviderToHomeActivity()
    {
        Intent homeintent=new Intent(LoginServiceProviderActivity.this, SpHomeActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeintent);
        finish();
    }


    private void sendServiceProviderToRegisterActivity()
    {
        Intent registerIntent=new Intent(LoginServiceProviderActivity.this, RegisterServiceProvider.class);
        startActivity(registerIntent);

    }
}
