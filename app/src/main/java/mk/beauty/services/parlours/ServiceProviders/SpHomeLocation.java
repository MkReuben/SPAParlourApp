package mk.beauty.services.parlours.ServiceProviders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkEldoret;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkKisumu;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkMachakos;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkMeru;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkNairobi;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpWorkLocations.SpWorkThika;
import mk.beauty.services.parlours.Users.HomeActivity;
import mk.beauty.services.parlours.Users.Locations.Eldoret;
import mk.beauty.services.parlours.Users.Locations.Kisumu;
import mk.beauty.services.parlours.Users.Locations.Nairobi;
import mk.beauty.services.parlours.Users.Locations.Nakuru;
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

public class SpHomeLocation extends AppCompatActivity {

    String items[]=new String[]{"Nairobi","Nakuru","Kisumu","Eldoret","Mombasa","Meru","Thika","Machakos"};
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_home_location);


        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        final ListView listView = (ListView) findViewById(R.id.list_sp_locations);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpHomeLocation.this, items[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(),SpWorkNairobi.class);
                    startActivityForResult(myIntent, 0);


                } else if (position == 1) {

                    Intent myIntent = new Intent(view.getContext(), SpWorkNakuru.class);
                    startActivityForResult(myIntent, 0);


                } else if (position == 2) {

                    Intent myIntent = new Intent(view.getContext(), SpWorkKisumu.class);
                    startActivityForResult(myIntent, 0);


                } else if (position == 3) {

                    Intent myIntent = new Intent(view.getContext(), SpWorkEldoret.class);
                    startActivityForResult(myIntent, 0);


                } else if (position == 4) {
                    Intent myIntent = new Intent(view.getContext(),SpWorkMombasa.class);
                    startActivityForResult(myIntent, 0);

                } else if (position == 5) {
                    Intent myIntent = new Intent(view.getContext(), SpWorkMeru.class);
                    startActivityForResult(myIntent, 0);

                } else if (position == 6) {
                    Intent myIntent = new Intent(view.getContext(), SpWorkThika.class);
                    startActivityForResult(myIntent, 0);
                } else if (position == 7) {
                    Intent myIntent = new Intent(view.getContext(), SpWorkMachakos.class);
                    startActivityForResult(myIntent, 0);
                }
            }


        });

    }
    }
