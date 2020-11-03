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
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpBraidingMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpFacialMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpGelNailsMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpHairStylingMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpKeratinTreatmentMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpMakeUpMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpManicureMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpMassagingMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpNailExtensionMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpPedicureMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpPreBridalPackageMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMeruServices.SpWeavingMeru;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpBraidingMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpFacialMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpGelNailsMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpHairStylingMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpKeratinTreatmentMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpMakeUpMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpManicureMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpMassagingMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpNailExtensionMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpPedicureMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpPreBridalPackageMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.SpWeavingMombasa;

public class SpWorkMeru extends AppCompatActivity {

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
        setContentView(R.layout.activity_sp_work_meru);




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
                Toast.makeText(SpWorkMeru.this, mTitle[position], Toast.LENGTH_SHORT).show();

            }
        });


        final ListView listView = (ListView) findViewById(R.id.list_sp_meru);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpWorkMeru.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), SpManicureMeru.class);
                    startActivityForResult(myIntent, 0);


                }else
                if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), SpPedicureMeru.class);
                    startActivityForResult(myIntent, 0);


                }else  if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), SpNailExtensionMeru.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(), SpGelNailsMeru.class);
                    startActivityForResult(myIntent, 0);


                }
                else  if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(), SpFacialMeru.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(), SpMassagingMeru.class);
                    startActivityForResult(myIntent, 0);

                }
                else  if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(), SpMakeUpMeru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 7) {
                    Intent myIntent = new Intent(view.getContext(), SpKeratinTreatmentMeru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 8) {
                    Intent myIntent = new Intent(view.getContext(), SpBraidingMeru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 9) {
                    Intent myIntent = new Intent(view.getContext(), SpHairStylingMeru.class);
                    startActivityForResult(myIntent, 0);
                }
                else  if (position == 10) {
                    Intent myIntent = new Intent(view.getContext(), SpPreBridalPackageMeru.class);
                    startActivityForResult(myIntent, 0);
                }
            }


        });





    }
}
