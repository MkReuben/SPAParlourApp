package mk.beauty.services.parlours.ServiceProviders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.Users.HomeActivity;
import mk.beauty.services.parlours.Users.SettingsActivity;

public class SpSettingsActivity extends AppCompatActivity {

    private EditText userfullname, userphonenumber, useraddress,username, usergender,userStatus;
    private Button update;
    private CircleImageView userProfileimage;
    private ProgressDialog loadingBar;
    private StorageReference UserProfileImageRef;
    final static int Gallery_Pick = 1;



    private DatabaseReference settingsUserRef;
    private FirebaseAuth mAuth;
    private String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_settings);



        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        settingsUserRef= FirebaseDatabase.getInstance().getReference().child("Service Providers").child(currentUserID);
        UserProfileImageRef= FirebaseStorage.getInstance().getReference().child("profile images");

        userfullname =(EditText)findViewById(R.id.settings_profile_full_name);
        username=(EditText)findViewById(R.id.settings_username);
        userphonenumber =(EditText)findViewById(R.id.settings_phone_number);
        useraddress =(EditText)findViewById(R.id.settings_location);
        usergender =(EditText)findViewById(R.id.settings_gender);
        userProfileimage=(CircleImageView)findViewById(R.id.settings_profile_image);
        update=( Button) findViewById(R.id.settings_account_btn);
        userStatus=(EditText)findViewById(R.id.settings_status);
        loadingBar=new ProgressDialog(this);




        settingsUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String myProfileImage=dataSnapshot.child("profileimage").getValue().toString();
                    String myFullName=dataSnapshot.child("fullname").getValue().toString();
                    String myPhoneNumber=dataSnapshot.child("phonenumber").getValue().toString();
                    String myAddress=dataSnapshot.child("cityname").getValue().toString();
                    String myGender=dataSnapshot.child("gender").getValue().toString();
                    String myUserName=dataSnapshot.child("username").getValue().toString();
                    String myUserStatus=dataSnapshot.child("status").getValue().toString();

                    Picasso.get().load(myProfileImage).placeholder(R.drawable.profile).into(userProfileimage);

                    username.setText(myUserName);
                    userfullname.setText(myFullName);
                    userphonenumber.setText(myPhoneNumber);
                    useraddress.setText(myAddress);
                    usergender.setText(myGender);
                    userStatus.setText(myUserStatus);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ValidateAccountInfo();

            }
        });

        userProfileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent galleryIntent= new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            Uri ImageUri =data.getData();

            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode== RESULT_OK)


            {


                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please wait while we are updating your profile image");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();


                Uri resultUri=result.getUri();

                StorageReference filePath = UserProfileImageRef.child(currentUserID+ ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SpSettingsActivity.this, "Profile image stored successfully", Toast.LENGTH_SHORT).show();

                            final String downloadUrl=task.getResult().getDownloadUrl().toString();

                            settingsUserRef.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {

                                                Intent selfintent=new Intent(SpSettingsActivity.this,SettingsActivity.class);
                                                startActivity(selfintent);

                                                Toast.makeText(SpSettingsActivity.this, "Profile image stored successfully", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            else
                                            {
                                                loadingBar.dismiss();
                                                String message =task.getException().getMessage();
                                                Toast.makeText(SpSettingsActivity.this, "Error Occcurred: " +message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }

                    }
                });

            }
            else
            {

                loadingBar.dismiss();
                Toast.makeText(SpSettingsActivity.this, "Error Occcurred: Image cann't be cropped try again" , Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void ValidateAccountInfo()
    {
        String settingsusername =username.getText().toString();
        String settingprofname =userfullname.getText().toString();
        String settingsstatus=userStatus.getText().toString();
        String settinggender =usergender.getText().toString();
        String settingslocation =useraddress.getText().toString();
        String settingphonenumber=userphonenumber.getText().toString();

        if (TextUtils.isEmpty(settingsusername))
        {
            Toast.makeText(this, "Please write your username", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(settingprofname))
        {
            Toast.makeText(this, "Please write your Profile Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(settingsstatus))
        {
            Toast.makeText(this, "Please write your Profile Status", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(settinggender))
        {
            Toast.makeText(this, "Please write your Gender", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(settingslocation))
        {
            Toast.makeText(this, "Please write your Address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(settingphonenumber))
        {
            Toast.makeText(this, "Please write your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else
        {

            loadingBar.setTitle("Profile Image");
            loadingBar.setMessage("Please wait while we are updating your profile image");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            UpdateAccountInfo(settingsusername,settingprofname,settingsstatus,settinggender,settingslocation,settingphonenumber);

        }


    }

    private void UpdateAccountInfo(String settingsusername, String settingprofname, String settingsstatus, String settinggender, String settingslocation, String settingphonenumber)
    {
        HashMap userMap =new HashMap();
        userMap.put("username",settingsusername);
        userMap.put("fullname",settingprofname);
        userMap.put("status",settingsstatus);
        userMap.put("gender",settinggender);
        userMap.put("cityname",settingslocation);
        userMap.put("phonenumber",settingphonenumber);

        settingsUserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if (task.isSuccessful())
                {
                    SendUserToHomeActivity();
                    Toast.makeText(SpSettingsActivity.this, "Account Settings Updated Successfully", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
                else
                {
                    Toast.makeText(SpSettingsActivity.this, "Error Occured,while updating account settings information", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }
        });
    }

    private void SendUserToHomeActivity()
    {
        Intent intent =new Intent(SpSettingsActivity.this, SpHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}