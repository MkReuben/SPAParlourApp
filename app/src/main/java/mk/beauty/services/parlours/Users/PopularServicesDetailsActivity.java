package mk.beauty.services.parlours.Users;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.model.Services;

public class PopularServicesDetailsActivity extends AppCompatActivity {

    private ImageView sImage;
    private TextView serviceProviderName, serviceName, serviceDescription,servicePhoneNumber,serviceLocation;
    private String serviceID = "";
    private ImageView call,sms,whatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_services_details);

        serviceID = getIntent().getStringExtra("sid");


        sImage = findViewById(R.id.popular_image_details);
        serviceProviderName = findViewById(R.id.popular_sp_name_details);
        serviceName = findViewById(R.id.popular_service_name_details);
        serviceDescription = findViewById(R.id.popular_description_details);
        servicePhoneNumber= findViewById(R.id.popular_phone_number_details);
        serviceLocation = findViewById(R.id.popular_location_details);
        call=findViewById(R.id.call_details);
        sms=findViewById(R.id.sms_details);
        whatsapp=findViewById(R.id.whatsapp_details);



        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse("smsto:"+servicePhoneNumber.getText());
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





        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+servicePhoneNumber.getText()));
                if (ContextCompat.checkSelfPermission(PopularServicesDetailsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PopularServicesDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);

                    Toast.makeText(PopularServicesDetailsActivity.this, "Please wait while we are calling"+serviceProviderName+"", Toast.LENGTH_LONG).show();
                }
                else
                {
                    startActivity(intent);
                }




            }
        });



        getServiceDetails(serviceID);



    }

    private void startSupportChat()
    {

        try {
            String headerReceiver = "GoKenya";// Replace with your message.
            String bodyMessageFormal = "Hello this GoKenya Safari";// Replace with your message.
            String whatsappContain = headerReceiver + bodyMessageFormal;
            String trimToNumner = "+254"+servicePhoneNumber.getText(); //10 digit number
            Intent intent = new Intent ( Intent.ACTION_VIEW );
            intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "" ) );
            startActivity ( intent );
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    private void getServiceDetails(String serviceID) {



        DatabaseReference serviceRef = FirebaseDatabase.getInstance().getReference().child("Popular Services");

        serviceRef.child(serviceID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Services services = dataSnapshot.getValue(Services.class);

                    serviceDescription.setText(services.getDescription());
                    serviceProviderName.setText(services.getServiceProviderName());
                    serviceName.setText(services.getName());
                    servicePhoneNumber.setText(services.getPhonenumber());
                    serviceLocation.setText(services.getLocation());
                    Picasso.get().load(services.getImage()).into(sImage);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}

