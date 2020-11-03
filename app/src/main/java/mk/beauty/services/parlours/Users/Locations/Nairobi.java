package mk.beauty.services.parlours.Users.Locations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import mk.beauty.services.parlours.R;
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
import mk.beauty.services.parlours.Users.NairobiServices.Weaving;

public class Nairobi extends AppCompatActivity {

    String items[]=new String[]{"Manicure","Pedicure","NailExtension","GelNails","Weaving","Facial","Massaging","MakeUp","KeratinTreatment","Braiding","HairStyling","PreBridal"};


    private int[] mImages = new int[]
            {
                    R.drawable.cargelnails, R.drawable.carmanicure,
            };
    private String[] mTitle = new String[]
            {
                    "Gel Nails",  "Manicure",
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nairobi);




        CarouselView carouselView = findViewById(R.id.carousel_home);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);

            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(Nairobi.this, mTitle[position], Toast.LENGTH_SHORT).show();

            }
        });


        final ListView listView = (ListView) findViewById(R.id.list_nairobi);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Nairobi.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), Manicure.class);
                    startActivityForResult(myIntent, 0);


                }else
                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), Pedicure.class);
                    startActivityForResult(myIntent, 0);


                }else  if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), NailExtension.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(),GelNails.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(),Facial.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(),Massaging.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(),MakeUp.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 7) {
                    Intent myIntent = new Intent(view.getContext(),KeratinTreatment.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 8) {
                    Intent myIntent = new Intent(view.getContext(),Braiding.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 9) {
                    Intent myIntent = new Intent(view.getContext(),HairStyling.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 10) {
                    Intent myIntent = new Intent(view.getContext(),PreBridalPackages.class);
                    startActivityForResult(myIntent, 0);
                }
            }


        });





    }
}
