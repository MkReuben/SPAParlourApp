package mk.beauty.services.parlours.Users;

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
import mk.beauty.services.parlours.Prevalent.Prevalent;
import mk.beauty.services.parlours.R;

public class Setup extends AppCompatActivity {

    private EditText usersname,fullName,cityName,phonenumber;
    private Button saveinformationbtn;
    private CircleImageView profileImage;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private StorageReference UserProfileImageRef;
    private ProgressDialog loadingBar;
    final static int Gallery_Pick = 1;

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        usersname=(EditText)findViewById(R.id.setup_user_name);
        fullName=(EditText)findViewById(R.id.setup_full_names);
        cityName=(EditText)findViewById(R.id.set_up_location);
        phonenumber=(EditText)findViewById(R.id.setup_phone_number_input);
        saveinformationbtn=(Button)findViewById(R.id.setup_save_button);
        profileImage=(CircleImageView)findViewById(R.id.setup_profile_image);
        loadingBar=new ProgressDialog(this);



        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);
        UserProfileImageRef= FirebaseStorage.getInstance().getReference().child("profile images");
        saveinformationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SaveAccountSetupInformation();

            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent galleryIntent= new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);



            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("profileimage"))
                    {

                        String image =dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImage);
                    }
                    else
                    {
                        Toast.makeText(Setup.this, "Please select profile image first", Toast.LENGTH_SHORT).show();

                    }


       ;

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                            Toast.makeText(Setup.this, "Profile image stored successfully", Toast.LENGTH_SHORT).show();

                            final String downloadUrl=task.getResult().getDownloadUrl().toString();

                            userRef.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {

                                                Intent selfintent=new Intent(Setup.this,Setup.class);
                                                startActivity(selfintent);

                                                Toast.makeText(Setup.this, "Profile image stored successfully", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            else
                                            {
                                                loadingBar.dismiss();
                                                String message =task.getException().getMessage();
                                                Toast.makeText(Setup.this, "Error Occcurred: " +message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Setup.this, "Error Occcurred: Image cann't be cropped try again" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveAccountSetupInformation()
    {
        String username = usersname.getText().toString();
        String fullname = fullName.getText().toString();
        String cityname = cityName.getText().toString();
        String userphonenumber=phonenumber.getText().toString();

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please write username..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(fullname))
        {
            Toast.makeText(this, "Please write full names..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityname))
        {
            Toast.makeText(this, "Please write city name..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userphonenumber))
        {
            Toast.makeText(this, "Please write your phone number..", Toast.LENGTH_SHORT).show();
        }
        else
        {

            loadingBar.setTitle("Saving Information");
            loadingBar.setMessage("Please wait while we are saving your information");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            HashMap userMap=new HashMap();
            userMap.put("username",username);
            userMap.put("fullname",fullname);
            userMap.put("cityname",cityname);
            userMap.put("phonenumber",userphonenumber);
            userMap.put("gender","none");
            userMap.put("dob","none");
            userMap.put("relationshipstatus","none");
            userMap.put("status","Hey there,am using parlour app,developed by MK Dev Ltd.");

            userRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if (task.isSuccessful())
                    {
                        SendUserToHomeActivity();
                        Toast.makeText(Setup.this, "Your Account is created Successfully...", Toast.LENGTH_LONG).show();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        String message =task.getException().getMessage();
                        Toast.makeText(Setup.this, "Error Occured: "+message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }

                }
            });
        }
    }

    private void SendUserToHomeActivity()
    {
        Intent homeintent=new Intent(Setup.this,HomeActivity.class);
        homeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeintent);
        finish();
    }
}
