package mk.beauty.services.parlours.Users.Locations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
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
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpPedicureEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.BraidingEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.FacialEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.GelNailsEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.HairstylingEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.KeratinTreatmentEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.MakeUpEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.ManicureEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.MassagingEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.NailExtensionEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.PedicureEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.PreBridalPackagesEldoret;
import mk.beauty.services.parlours.Users.EldoretServices.WeavingEldoret;
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
import mk.beauty.services.parlours.Users.NakuruServices.BraidingNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.FacialNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.GelNailsNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.HairStylingNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.KeratinTreatmentNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.MakeUpNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.ManicureNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.MassagingNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.NailExtensionNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.PedicureNakuru;
import mk.beauty.services.parlours.Users.NakuruServices.PreBridalPackagesNakuru;

public class Eldoret extends AppCompatActivity {

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
        setContentView(R.layout.activity_eldoret);




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
                Toast.makeText(Eldoret.this, mTitle[position], Toast.LENGTH_SHORT).show();

            }
        });


        final ListView listView = (ListView) findViewById(R.id.list_eldoret);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Eldoret.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), ManicureEldoret.class);
                    startActivityForResult(myIntent, 0);


                }else
                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), PedicureEldoret.class);
                    startActivityForResult(myIntent, 0);


                }else  if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), NailExtensionEldoret.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(),GelNailsEldoret.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(),FacialEldoret.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(),MassagingEldoret.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(),MakeUpEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 7) {
                    Intent myIntent = new Intent(view.getContext(),KeratinTreatmentEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 8) {
                    Intent myIntent = new Intent(view.getContext(),BraidingEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 9) {
                    Intent myIntent = new Intent(view.getContext(),HairstylingEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 10) {
                    Intent myIntent = new Intent(view.getContext(),PreBridalPackagesEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
            }


        });





    }
}
