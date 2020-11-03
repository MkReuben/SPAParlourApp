package mk.beauty.services.parlours.ServiceProviders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mk.beauty.services.parlours.R;

public class SpLocationActivity extends AppCompatActivity {
    private TextView nairobi,nakuru,kisumu,eldoret,mombasa,meru,thika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_location);

        nairobi=(TextView)findViewById(R.id.tv_nairobi);
        nakuru=(TextView)findViewById(R.id.tv_Nakuru);
        kisumu=(TextView)findViewById(R.id.tv_kisumu);
        eldoret=(TextView)findViewById(R.id.tv_eldoret);
        mombasa=(TextView)findViewById(R.id.tv_mombasa);
        meru=(TextView)findViewById(R.id.tv_meru);
        thika=(TextView)findViewById(R.id.tv_thika);


        nairobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SpLocationActivity.this,SpCategoryActivity.class);
                intent.putExtra("Location","Nairobi");
                startActivity(intent);
            }
        });
        nakuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SpLocationActivity.this,SpCategoryActivity.class);
                intent.putExtra("Location","Nakuru");
                startActivity(intent);
            }
        });
        kisumu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SpLocationActivity.this,SpCategoryActivity.class);
                intent.putExtra("Location","Kisumu");
                startActivity(intent);
            }
        });
        eldoret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SpLocationActivity.this,SpCategoryActivity.class);
                intent.putExtra("Location","Eldoret");
                startActivity(intent);
            }
        });
        mombasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SpLocationActivity.this,SpCategoryActivity.class);
                intent.putExtra("Location","Meru");
                startActivity(intent);
            }
        });
        thika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(SpLocationActivity.this,SpCategoryActivity.class);
                intent.putExtra("Location","Thika");
                startActivity(intent);
            }
        });

    }
}
