package mk.beauty.services.parlours.ServiceProviders.SpWorkLocations;

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
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpBraidingEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpFacialEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpGelNailsEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpHairStylingEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpKeratinTreatmentEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpMakeUpEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpManicureEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpMassagingEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpNailExtensionEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpPedicureEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpPreBridalPackageEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpEldoretServices.SpWeavingEldoret;
import mk.beauty.services.parlours.Users.Locations.Eldoret;
import mk.beauty.services.parlours.Users.Locations.Meru;
import mk.beauty.services.parlours.Users.MeruServices.BraidingMeru;
import mk.beauty.services.parlours.Users.MeruServices.FacialMeru;
import mk.beauty.services.parlours.Users.MeruServices.GelNailsMeru;
import mk.beauty.services.parlours.Users.MeruServices.HairStylingMeru;
import mk.beauty.services.parlours.Users.MeruServices.KeratinTreatmentMeru;
import mk.beauty.services.parlours.Users.MeruServices.MakeUpMeru;
import mk.beauty.services.parlours.Users.MeruServices.ManicureMeru;
import mk.beauty.services.parlours.Users.MeruServices.MassagingMeru;
import mk.beauty.services.parlours.Users.MeruServices.NailExtensionMeru;
import mk.beauty.services.parlours.Users.MeruServices.PedicureMeru;
import mk.beauty.services.parlours.Users.MeruServices.PreBridalPackageMeru;
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

public class SpWorkEldoret extends AppCompatActivity {

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
        setContentView(R.layout.activity_sp_work_eldoret);




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
                Toast.makeText(SpWorkEldoret.this, mTitle[position], Toast.LENGTH_SHORT).show();

            }
        });


        final ListView listView = (ListView) findViewById(R.id.list_sp_eldoret);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpWorkEldoret.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), SpManicureEldoret.class);
                    startActivityForResult(myIntent, 0);


                }else
                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), SpPedicureEldoret.class);
                    startActivityForResult(myIntent, 0);


                }else  if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), SpNailExtensionEldoret.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(), SpGelNailsEldoret.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(), SpFacialEldoret.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(), SpMassagingEldoret.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(), SpMakeUpEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 7) {
                    Intent myIntent = new Intent(view.getContext(), SpKeratinTreatmentEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 8) {
                    Intent myIntent = new Intent(view.getContext(), SpBraidingEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 9) {
                    Intent myIntent = new Intent(view.getContext(), SpHairStylingEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 10) {
                    Intent myIntent = new Intent(view.getContext(), SpPreBridalPackageEldoret.class);
                    startActivityForResult(myIntent, 0);
                }
            }


        });





    }
}
