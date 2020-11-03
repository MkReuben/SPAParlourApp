package mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.ClickPost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import mk.beauty.services.parlours.ServiceProviders.SpHomeLocation;
import mk.beauty.services.parlours.ServiceProviders.SpMainActivity;

public class SpClickPostKeratinTreatmentEldoret extends AppCompatActivity {

    private ImageView serviceImage;
    private TextView serviceName,servicePhoneNumber;
    private Button deleteServiceButton,editServiceButton;
    private String PostKey,currentUserID,databaseServiceProviderID, servicesName,servicesPhoneNumber,  servicesImage ;
    private DatabaseReference ClickPostRef;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_click_post_keratin_treatment_eldoret);

        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();

        PostKey =getIntent().getExtras().get("PostKey").toString();
        ClickPostRef= FirebaseDatabase.getInstance().getReference().child("Services").child("Eldoret").child("KeratinTreatment").child(PostKey);

        serviceImage= findViewById(R.id.maintain_service_image);
        serviceName= findViewById(R.id.maintain_service_name);
        servicePhoneNumber= findViewById(R.id.maintain_service_phone_number);
        deleteServiceButton= findViewById(R.id.maintain_delete);
        editServiceButton= findViewById(R.id.maintain_post);


        deleteServiceButton.setVisibility(View.INVISIBLE);
        editServiceButton.setVisibility(View.INVISIBLE);


        ClickPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {

                    servicesName =dataSnapshot.child("servicename").getValue().toString();
                    servicesPhoneNumber =dataSnapshot.child("phonenumber").getValue().toString();
                    servicesImage =dataSnapshot.child("serviceimage").getValue().toString();

                    databaseServiceProviderID =dataSnapshot.child("uid").getValue().toString();


                    serviceName.setText(servicesName);
                    servicePhoneNumber.setText(servicesPhoneNumber);

                    Picasso.get().load(servicesImage).into(serviceImage);


                    if (currentUserID.equals(databaseServiceProviderID))

                    {
                        deleteServiceButton.setVisibility(View.VISIBLE);
                        editServiceButton.setVisibility(View.VISIBLE);
                    }

                    editServiceButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            EditCurrentPost(servicesName,servicesPhoneNumber);

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {


            }
        });

        deleteServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DeleteCurrentPost();

            }
        });
    }

    private void EditCurrentPost(String servicesName, String servicesPhoneNumber)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(SpClickPostKeratinTreatmentEldoret.this);
        builder.setTitle("Edit Post");

        final EditText inputserviceName =new EditText(SpClickPostKeratinTreatmentEldoret.this);
//        final EditText inputservicePhoneNumber =new EditText(ClickPostActivity.this);

        inputserviceName.setText(servicesName);
//        inputservicePhoneNumber.setText(servicesPhoneNumber);

        builder.setView(inputserviceName);
//        builder.setView(inputservicePhoneNumber);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

//                ClickPostRef.child("phonenumber").setValue(inputservicePhoneNumber.getText().toString());

                ClickPostRef.child("servicename").setValue(inputserviceName.getText().toString());

                Toast.makeText(SpClickPostKeratinTreatmentEldoret.this, "Your Service Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();



            }
        });
        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.Goldenrod);

    }

    private void DeleteCurrentPost()
    {
        ClickPostRef.removeValue();
        sendtoHomeActity();

        Toast.makeText(this, "Service has been deleted", Toast.LENGTH_SHORT).show();
    }
    private void sendtoHomeActity()
    {
        Intent intent=new Intent(SpClickPostKeratinTreatmentEldoret.this, SpHomeLocation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}

