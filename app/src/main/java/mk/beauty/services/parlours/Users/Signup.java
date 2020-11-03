package mk.beauty.services.parlours.Users;

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

public class Signup extends AppCompatActivity {
    private EditText userEmail,userPassword,userConfirmPassword;
    private Button CreateAccountButton;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();

        userEmail=(EditText)findViewById(R.id.register_email_input);
        userPassword=(EditText) findViewById(R.id.register_password_input);
        userConfirmPassword=(EditText)findViewById(R.id.confirm_password_input);
        loadingBar=new ProgressDialog(this);

    CreateAccountButton=(Button)findViewById(R.id.register_user_btn);

    CreateAccountButton.setOnClickListener(new View.OnClickListener() {
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
            SendUserToHomeActivity();
        }
    }

    private void SendUserToHomeActivity()
    {
        Intent homeintent=new Intent(Signup.this,HomeActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeintent);
        finish();
    }

    private void CreateNewAcount()
    {
        String email =userEmail.getText().toString();
        String password=userPassword.getText().toString();
        String confirmPassword =userConfirmPassword.getText().toString();

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
                              SendUserToSetupActivity();

                              Toast.makeText(Signup.this, "You are authenticated successfully", Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();
                          }
                          else
                          {
                             String message = task.getException().getMessage();
                              Toast.makeText(Signup.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();
                          }

                      }
                  });
      }
    }

    private void SendUserToSetupActivity()
    {
        Intent setupIntent=new Intent(Signup.this,Setup.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        startActivity(setupIntent);
        finish();
    }
}
