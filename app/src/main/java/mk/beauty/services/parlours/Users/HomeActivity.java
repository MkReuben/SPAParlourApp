package mk.beauty.services.parlours.Users;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.Users.Locations.Eldoret;
import mk.beauty.services.parlours.Users.Locations.Kisumu;
import mk.beauty.services.parlours.Users.Locations.Machakos;
import mk.beauty.services.parlours.Users.Locations.Meru;
import mk.beauty.services.parlours.Users.Locations.Mombasa;
import mk.beauty.services.parlours.Users.Locations.Nairobi;
import mk.beauty.services.parlours.Users.Locations.Nakuru;
import mk.beauty.services.parlours.Users.Locations.Thika;
import mk.beauty.services.parlours.WelcomeActivity;
import mk.beauty.services.parlours.model.Services;
import mk.beauty.services.parlours.model.Testimonials;
import mk.beauty.services.parlours.viewholder.ServiceViewHolder;
import mk.beauty.services.parlours.viewholder.TestViewHolder;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference ServicesRef, popularServicesRef, testRef;
    private RecyclerView recyclerView, testRecyclerView;
    private Button title, tvNairobi, tvNakuru, tvKisumu, tvEldoret, tvMombasa, tvMeru, tvThika, tvMachakos;
    RecyclerView.LayoutManager layoutManager, testlayoutManager;
    private String type = "";

    private ProgressBar spinner;


    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    String currentUserID;

    private int[] mImages = new int[]
            {
                    R.drawable.cargelnails, R.drawable.carmanicure,
            };
    private String[] mTitle = new String[]
            {
                    "Gel Nails", "Manicure",
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersRef.keepSynced(true);


        popularServicesRef = FirebaseDatabase.getInstance().getReference().child("Popular Services");
        popularServicesRef.keepSynced(true);


        testRef = FirebaseDatabase.getInstance().getReference().child("Testimonials");
        testRef.keepSynced(true);


        recyclerView = findViewById(R.id.recyler_popular_services);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);


        testRecyclerView = findViewById(R.id.recyler_test);
        testRecyclerView.setHasFixedSize(true);
        testlayoutManager = new LinearLayoutManager(this);
        testRecyclerView.setLayoutManager(testlayoutManager);
        LinearLayoutManager hLayoutManagaer = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        testRecyclerView.setLayoutManager(hLayoutManagaer);



        tvNairobi = findViewById(R.id.tv_nairobi);
        tvNakuru = findViewById(R.id.tv_Nakuru);
        tvKisumu = findViewById(R.id.tv_kisumu);
        tvEldoret = findViewById(R.id.tv_eldoret);
        tvMombasa = findViewById(R.id.tv_mombasa);
        tvMeru = findViewById(R.id.tv_meru);
        tvThika = findViewById(R.id.tv_thika);
        tvMachakos = findViewById(R.id.tv_machakos);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        tvNairobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nairobi = new Intent(HomeActivity.this, Nairobi.class);
                startActivity(nairobi);
                spinner.setVisibility(View.VISIBLE);

            }
        });
        tvNakuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nakuru = new Intent(HomeActivity.this, Nakuru.class);
                startActivity(nakuru);
                spinner.setVisibility(View.VISIBLE);

            }
        });
        tvKisumu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nakuru = new Intent(HomeActivity.this, Kisumu.class);
                startActivity(nakuru);
                spinner.setVisibility(View.VISIBLE);

            }
        });
        tvEldoret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent eldoret = new Intent(HomeActivity.this, Eldoret.class);
                startActivity(eldoret);
                spinner.setVisibility(View.VISIBLE);

            }
        });
        tvMombasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent eldoret = new Intent(HomeActivity.this, Mombasa.class);
                startActivity(eldoret);
                spinner.setVisibility(View.VISIBLE);

            }
        });

        tvMeru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent eldoret = new Intent(HomeActivity.this, Meru.class);
                startActivity(eldoret);
                spinner.setVisibility(View.VISIBLE);

            }
        });
        tvThika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent thika = new Intent(HomeActivity.this, Thika.class);
                startActivity(thika);
                spinner.setVisibility(View.VISIBLE);

            }
        });
        tvMachakos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent machakos = new Intent(HomeActivity.this, Machakos.class);
                startActivity(machakos);
                spinner.setVisibility(View.VISIBLE);

            }
        });


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
                Toast.makeText(HomeActivity.this, mTitle[position], Toast.LENGTH_SHORT).show();

            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            type = getIntent().getExtras().get("Admin").toString();
        }

        ServicesRef = FirebaseDatabase.getInstance().getReference().child("Services");
        ServicesRef.keepSynced(true);

        popularServicesRef = FirebaseDatabase.getInstance().getReference().child("Popular Services");

        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!type.equals("Admin")) {
//                    Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
//                    startActivity(intent);
                }

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        final TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        final CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        usersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("fullname")) {
                        String fullname = dataSnapshot.child("fullname").getValue().toString();
                        userNameTextView.setText(fullname);
                    }
                    if (dataSnapshot.hasChild("profileimage")) {
                        String image = dataSnapshot.child("profileimage").getValue().toString();

                        Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImageView);

                    } else {
                        Toast.makeText(HomeActivity.this, "Profile name do not exist..", Toast.LENGTH_SHORT).show();
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Services> options =
                new FirebaseRecyclerOptions.Builder<Services>()
                        .setQuery(popularServicesRef, Services.class)
                        .build();


        FirebaseRecyclerAdapter<Services, ServiceViewHolder> adapter =
                new FirebaseRecyclerAdapter<Services, ServiceViewHolder>(options) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull ServiceViewHolder serviceViewHolder, int i, @NonNull final Services services) {
                        serviceViewHolder.txtServiceName.setText(services.getName());
                        serviceViewHolder.txtServiceDescription.setText(services.getDescription());
                        serviceViewHolder.txtServiceProviderName.setText(services.getServiceProviderName());
                        serviceViewHolder.txtServiceLocation.setText(services.getLocation());
                        serviceViewHolder.txtServicePhoneNumber.setText(services.getPhonenumber());
                        Picasso.get().load(services.getImage()).into(serviceViewHolder.imageView);


                        serviceViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (type.equals("All Admins")) {

                                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                                    intent.putExtra("sid", services.getSid());
                                    startActivity(intent);

                                } else {
                                    Intent intent = new Intent(HomeActivity.this, PopularServicesDetailsActivity.class);
                                    intent.putExtra("sid", services.getSid());
                                    startActivity(intent);
                                }

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_services_layout, parent, false);
                        ServiceViewHolder holder = new ServiceViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();




    FirebaseRecyclerOptions<Testimonials> tests =
            new FirebaseRecyclerOptions.Builder<Testimonials>()
                    .setQuery(testRef, Testimonials.class)
                    .build();


    FirebaseRecyclerAdapter<Testimonials, TestViewHolder> firebaseRecyclerAdapter =
            new FirebaseRecyclerAdapter<Testimonials, TestViewHolder>(tests) {
                @SuppressLint("SetTextI18n")
                @Override
                protected void onBindViewHolder(@NonNull TestViewHolder testViewHolder, int i, @NonNull final Testimonials testimonials) {
                    testViewHolder.txtName.setText(testimonials.getName());
                    testViewHolder.txtDescription.setText(testimonials.getDescription());
                    Picasso.get().load(testimonials.getImage()).into(testViewHolder.imageView);


                }

                @NonNull
                @Override
                public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testimonials_layout, parent, false);
                    TestViewHolder holder = new TestViewHolder(view);
                    return holder;
                }
            };

        testRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


        FirebaseUser currentUser=mAuth.getCurrentUser();

        if (currentUser==null)
        {
            SendUserToLoginActivity();
        }
        else
        {
            CheckUserExistance();
        }
    }


    private void CheckUserExistance()
    {
        final String  current_user_id =mAuth.getCurrentUser().getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.hasChild(current_user_id))
                {
                    SendUserToSetupActivity();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToSetupActivity()
    {
        Intent setupInntent =new Intent(HomeActivity.this,Setup.class);
        setupInntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupInntent);
        finish();
    }

    private void SendUserToLoginActivity()
    {
        Intent loginIntent =new Intent(HomeActivity.this, WelcomeActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            mAuth.signOut();
            SendUserToLoginActivity();
            finish();
            return true;


        }

        if (id==R.id.action_locations)
        {
           Intent intent = new Intent(HomeActivity.this,LocationActivity.class);
           startActivity(intent);
           return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (!type.equals("Admin"))
            {

            }

        }

        else if (id == R.id.nav_location) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, LocationActivity.class);
                startActivity(intent);
            }


        }

        else if (id == R.id.nav_settings) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }


        }
        else if (id == R.id.nav_logout) {
            if (!type.equals("Admin")) {

            mAuth.signOut();
            SendUserToLoginActivity();
            }


        } else if (id == R.id.nav_aboutus) {
            if (!type.equals("Admin")) {

//                Intent intent = new Intent(HomeActivity.this,AddPostActivity.class);
//                startActivity(intent);

            }
        } else if (id == R.id.nav_help) {
            if (!type.equals("Admin")) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "remugane@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PETSU HELP CENTRE");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello Dear Client,welcome to petsu help centre");
                startActivity(Intent.createChooser(emailIntent, "Help Center"));

            }


        }else if (id == R.id.nav_profile) {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent);


            }


        }

        else if (id == R.id.nav_contactus) {
            if (!type.equals("Admin")) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+254718209341"));
                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    startActivity(intent);
                }

            }
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
