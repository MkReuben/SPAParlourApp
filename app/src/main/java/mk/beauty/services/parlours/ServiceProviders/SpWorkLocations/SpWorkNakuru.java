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
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpBraidingMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpFacialMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpGelNailsMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpHairStylingMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpKeratinTreatmentMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpMakeUpMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpManicureMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpMassagingMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpNailExtensionMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpPedicureMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpPreBridalPackageMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpBraidingNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpFacialNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpGelNailsNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpHairStylingNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpKeratinTreatmentNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpMakeUpNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpManicureNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpMassagingNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpNailExtensionNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpPedicureNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpPreBridalPackageNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpWeavingNakuru;
import mk.beauty.services.parlours.Users.Locations.Nakuru;
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
import mk.beauty.services.parlours.Users.NakuruServices.WeavingNakuru;

public class SpWorkNakuru extends AppCompatActivity {

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
        setContentView(R.layout.activity_sp_work_nakuru);




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
                Toast.makeText(SpWorkNakuru.this, mTitle[position], Toast.LENGTH_SHORT).show();

            }
        });


        final ListView listView = (ListView) findViewById(R.id.list_sp_nakuru);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpWorkNakuru.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), SpManicureNakuru.class);
                    startActivityForResult(myIntent, 0);


                }else
                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), SpPedicureNakuru.class);
                    startActivityForResult(myIntent, 0);


                }else  if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), SpNailExtensionNakuru.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(), SpGelNailsNakuru.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(), SpFacialNakuru.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(), SpMassagingNakuru.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(), SpMakeUpNakuru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 7) {
                    Intent myIntent = new Intent(view.getContext(), SpKeratinTreatmentNakuru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 8) {
                    Intent myIntent = new Intent(view.getContext(), SpBraidingNakuru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 9) {
                    Intent myIntent = new Intent(view.getContext(), SpHairStylingNakuru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 10) {
                    Intent myIntent = new Intent(view.getContext(), SpPreBridalPackageNakuru.class);
                    startActivityForResult(myIntent, 0);
                }
            }


        });





    }
}
