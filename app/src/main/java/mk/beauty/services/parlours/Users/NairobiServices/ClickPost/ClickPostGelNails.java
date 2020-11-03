package mk.beauty.services.parlours.Users.NairobiServices.ClickPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import mk.beauty.services.parlours.R;

public class ClickPostGelNails extends AppCompatActivity {

    private ImageView serviceImage;
    private TextView serviceName,servicePhoneNumber;
    private Button deleteServiceButton,editServiceButton;
    private String PostKey,currentUserID,databaseServiceProviderID, servicesName,servicesPhoneNumber,  servicesImage ;
    private DatabaseReference ClickPostRef;
    private ImageView call,sms,whatsapp;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post_gel_nails);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        PostKey = getIntent().getExtras().get("PostKey").toString();
        ClickPostRef = FirebaseDatabase.getInstance().getReference().child("Services").child("Nairobi").child("GelNails").child(PostKey);

        serviceImage = (ImageView) findViewById(R.id.post_image_details);
        serviceName = (TextView) findViewById(R.id.post_description_details);
        servicePhoneNumber = (TextView) findViewById(R.id.post_phone_details);

        call=findViewById(R.id.call_details);
        sms=findViewById(R.id.sms_details);
        whatsapp=findViewById(R.id.whatsapp_details);



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +servicesPhoneNumber));
                if (ContextCompat.checkSelfPermission(ClickPostGelNails.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ClickPostGelNails.this, new String[]{Manifest.permission.CALL_PHONE},1);

                    Toast.makeText(ClickPostGelNails.this, "Please wait while we are calling"+servicePhoneNumber+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    startActivity(intent);
                }




            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("smsto:"+servicesPhoneNumber);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "");
                startActivity(intent);

            }
        });

        whatsapp.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startSupportChat ();
            }
        } );

        ClickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    servicesName = dataSnapshot.child("servicename").getValue().toString();
                    servicesPhoneNumber = dataSnapshot.child("phonenumber").getValue().toString();
                    servicesImage = dataSnapshot.child("serviceimage").getValue().toString();

                    databaseServiceProviderID = dataSnapshot.child("uid").getValue().toString();


                    serviceName.setText(servicesName);
                    servicePhoneNumber.setText(servicesPhoneNumber);

                    Picasso.get().load(servicesImage).into(serviceImage);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    private void startSupportChat()
    {

        try {
            String headerReceiver = "Parlour App";// Replace with your message.
            String bodyMessageFormal = "Hello this Parlour App";// Replace with your message.
            String whatsappContain = headerReceiver + bodyMessageFormal;
            String trimToNumner = "+254"+servicesPhoneNumber; //10 digit number
            Intent intent = new Intent ( Intent.ACTION_VIEW );
            intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "" ) );
            startActivity ( intent );
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }
}
