package mk.beauty.services.parlours.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import mk.beauty.services.parlours.R;

public class ServicesActivity extends AppCompatActivity {


    private TextView tvtitle,tvdescription,tvcategory;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


        tvtitle=findViewById(R.id.tv_service_title);
        tvdescription=findViewById(R.id.tv_service_description);
        tvcategory=findViewById(R.id.tv_cat);
        img=findViewById(R.id.servicethumbnail);

        //receiving the data

        Intent intent =getIntent();

        String Title=intent.getExtras().getString("Title");
        String Description=intent.getExtras().getString("Description");
        int image=intent.getExtras().getInt("Thumbnail");

        //setting values

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        img.setImageResource(image);

    }
}
