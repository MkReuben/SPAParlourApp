package mk.beauty.services.parlours.ServiceProviders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mk.beauty.services.parlours.R;

public class AddPostActivity extends AppCompatActivity {

    private TextView manicure,pedicure,nailextension,gelnails,weaving,facial,massaging,makeup,keratintreatment,braiding,hairstyling,prebridaltratment,barber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        manicure=(TextView)findViewById(R.id.manicure);
        pedicure=(TextView)findViewById(R.id.pedicure);
        nailextension=(TextView)findViewById(R.id.nail_extension);
        gelnails=(TextView)findViewById(R.id.gel_nails);
        weaving=(TextView)findViewById(R.id.weaving);
        facial=(TextView)findViewById(R.id.facial);
        massaging=(TextView)findViewById(R.id.massage);
        makeup=(TextView)findViewById(R.id.make_up);
        keratintreatment=(TextView)findViewById(R.id.keratin_services);
        braiding=(TextView)findViewById(R.id.braiding);
        hairstyling=(TextView)findViewById(R.id.hair_styling);
        prebridaltratment=(TextView)findViewById(R.id.prebridal);
        barber=(TextView)findViewById(R.id.barber_services);

        manicure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this, PostActivity.class);
                intent.putExtra("Category","Manicure");

                startActivity(intent);

            }
        });
        pedicure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Pedicure");
                startActivity(intent);

            }
        });

        nailextension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","NailExtension");
                startActivity(intent);

            }
        });

        gelnails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","GelNails");
                startActivity(intent);

            }
        });

        facial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Facial");
                startActivity(intent);

            }
        });

        massaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Massaging");
                startActivity(intent);

            }
        });

        makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","MakeUp");
                startActivity(intent);

            }
        });

        keratintreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Keratintreatment");
                startActivity(intent);

            }
        });

        braiding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Braiding");
                startActivity(intent);

            }
        });

        hairstyling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Hairstyling");
                startActivity(intent);

            }
        });

        prebridaltratment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Prebridal");
                startActivity(intent);

            }
        });

        barber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(AddPostActivity.this,PostActivity.class);
                intent.putExtra("Category","Barber");
                startActivity(intent);

            }
        });







    }
}
