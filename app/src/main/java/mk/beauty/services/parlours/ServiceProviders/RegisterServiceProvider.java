package mk.beauty.services.parlours.ServiceProviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.Users.HomeActivity;
import mk.beauty.services.parlours.Users.Setup;

public class RegisterServiceProvider extends AppCompatActivity {
    private EditText serviceProviderEmail, serviceProviderPassword, serviceProviderConfirmPassword;
    private Button serviceProvideCreateAccount;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service_provider);



        mAuth=FirebaseAuth.getInstance();

        serviceProviderEmail =(EditText)findViewById(R.id.service_provider_register_email_input);
        serviceProviderPassword =(EditText) findViewById(R.id.service_provider_register_password_input);
        serviceProviderConfirmPassword =(EditText)findViewById(R.id.service_provider_confirm_password_input);
        loadingBar=new ProgressDialog(this);

        serviceProvideCreateAccount =(Button)findViewById(R.id.register_service_provider_btn);

        serviceProvideCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateNewAcount();

            }
        });



    }

    @Override
    protected void onStart()
    {

        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();

        if (currentUser!=null)
        {
            SendServiceProviderToHomeActivity();
        }
    }

    private void SendServiceProviderToHomeActivity()
    {
        Intent homeintent=new Intent(RegisterServiceProvider.this, HomeActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeintent);
        finish();
    }

    private void CreateNewAcount()
    {
        String email = serviceProviderEmail.getText().toString();
        String password= serviceProviderPassword.getText().toString();
        String confirmPassword = serviceProviderConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmPassword))
        {
            Toast.makeText(this, "Please confirm your password.", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmPassword))
        {
            Toast.makeText(this, "your password do not match with your confirm password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait while we are creating your new Account");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                SendServiceProviderToSetupActivity();

                                Toast.makeText(RegisterServiceProvider.this, "You are authenticated successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(RegisterServiceProvider.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }
    }

    private void SendServiceProviderToSetupActivity()
    {
        Intent setupIntent=new Intent(RegisterServiceProvider.this,ServiceProviderSetUpActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        startActivity(setupIntent);
        finish();
    }
}
