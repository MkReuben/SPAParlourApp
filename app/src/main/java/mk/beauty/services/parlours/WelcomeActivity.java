package mk.beauty.services.parlours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import mk.beauty.services.parlours.ServiceProviders.LoginServiceProviderActivity;
import mk.beauty.services.parlours.Users.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView Client,ServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Client = findViewById(R.id.tv_welcome_client);
        ServiceProvider = findViewById(R.id.service_provider);


        Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        ServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent =new Intent(WelcomeActivity.this, LoginServiceProviderActivity.class);
                startActivity(intent);


            }
        });
    }
}
