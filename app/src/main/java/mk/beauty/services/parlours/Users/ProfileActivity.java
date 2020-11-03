package mk.beauty.services.parlours.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import mk.beauty.services.parlours.R;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView profilefullname,profileusername,profilephonenumber,profilestatus,profilegender,profileAddress;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private  String currrentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        currrentUserID=mAuth.getCurrentUser().getUid();
        profileUserRef= FirebaseDatabase.getInstance().getReference().child("users").child(currrentUserID);

        profileImage=(CircleImageView)findViewById(R.id.my_profile_pic);
        profilefullname=(TextView)findViewById(R.id.my_profile_full_name);
        profileusername=(TextView)findViewById(R.id.my_profile_user_name);
        profilephonenumber=(TextView)findViewById(R.id.my_phone_number);
        profilestatus=(TextView)findViewById(R.id.my_profile_status);
        profilegender=(TextView)findViewById(R.id.my_gender);
        profileAddress=(TextView)findViewById(R.id.my_address);

        profileUserRef.addValueEventListener(new ValueEventListener() {
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

                    Picasso.get().load(myProfileImage).placeholder(R.drawable.profile).into(profileImage);

                    profileusername.setText("@"+ myUserName);
                    profilefullname.setText(myFullName);
                    profilephonenumber.setText(myPhoneNumber);
                    profileAddress.setText("Address: " + myAddress);
                    profilegender.setText("Gender: " + myGender);
                    profilestatus.setText(myUserStatus);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
