package mk.beauty.services.parlours.ServiceProviders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.Users.HomeActivity;
import mk.beauty.services.parlours.Users.Locations.Nairobi;
import mk.beauty.services.parlours.Users.NairobiServices.Braiding;
import mk.beauty.services.parlours.Users.NairobiServices.Facial;
import mk.beauty.services.parlours.Users.NairobiServices.GelNails;
import mk.beauty.services.parlours.Users.NairobiServices.HairStyling;
import mk.beauty.services.parlours.Users.NairobiServices.KeratinTreatment;
import mk.beauty.services.parlours.Users.NairobiServices.MakeUp;
import mk.beauty.services.parlours.Users.NairobiServices.Manicure;
import mk.beauty.services.parlours.Users.NairobiServices.Massaging;
import mk.beauty.services.parlours.Users.NairobiServices.NailExtension;
import mk.beauty.services.parlours.Users.NairobiServices.Pedicure;
import mk.beauty.services.parlours.Users.NairobiServices.PreBridalPackages;
import mk.beauty.services.parlours.Users.Setup;
import mk.beauty.services.parlours.WelcomeActivity;

public class SpHomeActivity extends AppCompatActivity {

    String currentUserID;

    String items[]=new String[]{"Home Page","Add Service","My Profile","Locations","Contact us","Whatsapp us","Settings","Sign out"};

    private FirebaseAuth mAuth;
    private DatabaseReference spRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_home);


        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        spRef= FirebaseDatabase.getInstance().getReference().child("Service Providers");


      final TextView  spprofilename=findViewById(R.id.sp_profile_name);
      final  CircleImageView spprofileimage=findViewById(R.id.sp_profile_image);


        final ListView listView = (ListView) findViewById(R.id.list_sp_home);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpHomeActivity.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), SpHomeLocation.class);
                    startActivityForResult(myIntent, 0);


                }else
                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), SpCategoryActivity.class);
                    startActivityForResult(myIntent, 0);


                }else  if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), SpProfileActivity.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(), SpHomeLocation.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 4) {


                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+254718209341"));
                    if (ContextCompat.checkSelfPermission(SpHomeActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SpHomeActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                    else
                    {
                        startActivity(intent);
                    }



                }
                else  if (position == 5) {

                    startSupportChat ();
                }
                else  if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(), SpSettingsActivity.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 7) {

                    mAuth.signOut();
                    finish();
                    SendSpToLoginActivity();

                }

            }


        });



        spRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("fullname"))
                    {
                        String fullname =dataSnapshot.child("fullname").getValue().toString();
                        spprofilename.setText(fullname);
                    }
                    if (dataSnapshot.hasChild("profileimage"))
                    {
                        String image =dataSnapshot.child("profileimage").getValue().toString();

                        Picasso.get().load(image).placeholder(R.drawable.profile).into(spprofileimage);

                    }

                    else
                    {
                        Toast.makeText(SpHomeActivity.this, "Profile name do not exist..", Toast.LENGTH_SHORT).show();
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


    }

    private void SendSpToLoginActivity()
    {
        Intent loginIntent =new Intent(SpHomeActivity.this, WelcomeActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void startSupportChat()
    {
        try {
            String headerReceiver = "Parlour App";// Replace with your message.
            String bodyMessageFormal = "Hello this Parlour App";// Replace with your message.
            String whatsappContain = headerReceiver + bodyMessageFormal;
            String trimToNumner = "+254718209341"; //10 digit number
            Intent intent = new Intent ( Intent.ACTION_VIEW );
            intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "" ) );
            startActivity ( intent );
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();

        if (currentUser==null)
        {
            SendServiceProviderToLoginActivity();
        }
        else
        {
            CheckServiceProviderExistance();
        }
    }

    private void CheckServiceProviderExistance()

    {
        final String  current_user_id =mAuth.getCurrentUser().getUid();

        spRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.hasChild(current_user_id))
                {
                    SendServiceProviderToSetUpActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {


            }
        });
    }

    private void SendServiceProviderToSetUpActivity()

    {
        Intent setupInntent =new Intent(SpHomeActivity.this, ServiceProviderSetUpActivity.class);
        setupInntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupInntent);
        finish();
    }

    private void SendServiceProviderToLoginActivity()
    {

        Intent loginIntent =new Intent(SpHomeActivity.this,WelcomeActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}
