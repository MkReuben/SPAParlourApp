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
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpBraidingKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpFacialKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpGelNailsKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpHairStylingKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpKeratinTreatmentKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpMakeUpKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpManicureKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpMassagingKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpNailExtensionKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpPedicureKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpPreBridalPackageKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpKisumuServices.SpWeavingKisumu;
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
import mk.beauty.services.parlours.ServiceProviders.SpMachakosServices.SpWeavingMachakos;
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

public class SpWorkMachakos extends AppCompatActivity {

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
        setContentView(R.layout.activity_sp_work_machakos);




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
                Toast.makeText(SpWorkMachakos.this, mTitle[position], Toast.LENGTH_SHORT).show();

            }
        });


        final ListView listView = (ListView) findViewById(R.id.list_sp_machakos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpWorkMachakos.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), SpManicureMachakos.class);
                    startActivityForResult(myIntent, 0);


                }else
                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), SpPedicureMachakos.class);
                    startActivityForResult(myIntent, 0);


                }else  if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), SpNailExtensionMachakos.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(), SpGelNailsMachakos.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(), SpFacialMachakos.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(), SpMassagingMachakos.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(), SpMakeUpMachakos.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 7) {
                    Intent myIntent = new Intent(view.getContext(), SpKeratinTreatmentMachakos.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 8) {
                    Intent myIntent = new Intent(view.getContext(), SpBraidingMachakos.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 9) {
                    Intent myIntent = new Intent(view.getContext(), SpHairStylingMachakos.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 10) {
                    Intent myIntent = new Intent(view.getContext(), SpPreBridalPackageMachakos.class);
                    startActivityForResult(myIntent, 0);
                }
            }


        });





    }
}
