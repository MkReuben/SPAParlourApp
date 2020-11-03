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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import mk.beauty.services.parlours.R;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String LocationName, CategoryName, serviceName, spinnerLocation, downloadUrl, name, saveCurrrentDate, saveCurrentTime, current_user_id;
    private ImageButton selectpostimage;
    private Button addservicepostbtn;
    private EditText servicename, servicelocation;
    private static final int Gallery_Pick = 1;
    private Uri imageUri;
    private StorageReference PostServiceImagesReference;
    private String postServiceRandomKey;
    private DatabaseReference usersRef, postServiceRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private Spinner spin;

    String[] locations ={"Nairobi","Nakuru","Kisumu","Eldoret","Mombasa","Meru","Thika","Machakos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        PostServiceImagesReference = FirebaseStorage.getInstance().getReference();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Service Providers");
        postServiceRef = FirebaseDatabase.getInstance().getReference().child("Services");


        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();



        loadingbar = new ProgressDialog(this);
        selectpostimage = (ImageButton) findViewById(R.id.select_post_image);
        addservicepostbtn = (Button) findViewById(R.id.add_service_post_button);
        servicename = (EditText) findViewById(R.id.add_service_name);
        CategoryName = getIntent().getExtras().get("Category").toString();


        selectpostimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();

            }
        });

        addservicepostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateServicePostInfo();


            }
        });
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, locations);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), locations[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }
    private void validateServicePostInfo()
    {

        serviceName=servicename.getText().toString();
         spinnerLocation = spin.getSelectedItem().toString();


        if (imageUri == null)
    {
        Toast.makeText(this, "Please select past service image", Toast.LENGTH_SHORT).show();
    }
        if (TextUtils.isEmpty(serviceName))
    {
        Toast.makeText(this, "Please write the service name you are offering", Toast.LENGTH_SHORT).show();
    }

        else
        {

            loadingbar.setTitle("Add New Service");
            loadingbar.setMessage("Please wait while we are updating your new service");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);
            StoringImageToFirebaseStorage();
        }
}

    private void StoringImageToFirebaseStorage()
    {
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat( "MMM dd,yyyy");
        saveCurrrentDate=currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime= new SimpleDateFormat( "H:mm:a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        postServiceRandomKey =saveCurrentTime+saveCurrrentDate;
        StorageReference filePath=PostServiceImagesReference.child("Post Images").child(imageUri.getLastPathSegment()+ postServiceRandomKey+ ".jpg");
        filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if (task.isSuccessful())
                {

                    downloadUrl=task.getResult().getDownloadUrl().toString();
                    Toast.makeText(PostActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                    savingPostServiceInformationToDatabase();
                    loadingbar.dismiss();

                }
                else {
                    String message =task.getException().getMessage();
                    Toast.makeText(PostActivity.this, "Error Occurred:"+message, Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }

            }
        });
    }

    private void savingPostServiceInformationToDatabase()
    {

        usersRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String userfullname=dataSnapshot.child("fullname").getValue().toString();
                    String userphonenumber=dataSnapshot.child("phonenumber").getValue().toString();
                    String userprofileimage=dataSnapshot.child("profileimage").getValue().toString();

                    HashMap postMap =new HashMap();
                    postMap.put("uid",current_user_id);
                    postMap.put("date",saveCurrrentDate);
                    postMap.put("time",saveCurrentTime);
                    postMap.put("servicename",serviceName);
                    postMap.put("Location",spinnerLocation);
                    postMap.put("Category",CategoryName);
                    postMap.put("serviceimage",downloadUrl);
                    postMap.put("profileimage",userprofileimage);
                    postMap.put("phonenumber",userphonenumber);
                    postMap.put("fullname",userfullname);

                    postServiceRef.child(spinnerLocation).child(CategoryName).child(current_user_id + postServiceRandomKey).updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)

                                {
                                    if (task.isSuccessful())

                                    {
                                        Intent intent =new Intent(PostActivity.this, SpHomeActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(PostActivity.this, "Your new service is posted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(PostActivity.this, "Error occurred while updating your post", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void OpenGallery()
    {
        Intent galleryIntent= new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            selectpostimage.setImageURI(imageUri);
        }

    }
}
