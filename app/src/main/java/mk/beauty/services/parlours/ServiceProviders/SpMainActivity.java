package mk.beauty.services.parlours.ServiceProviders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.Users.LocationActivity;
import mk.beauty.services.parlours.Users.Posts;

public class SpMainActivity extends AppCompatActivity {

    private DatabaseReference ServicesRef, LikesRef, PostsRef;
    private RecyclerView postserviceslist;
    RecyclerView.LayoutManager layoutManager;
    private String type = "";
    private FirebaseAuth mAuth;
    private ImageView img_locations;

    RatingBar mRaringBar;

    String currentUserID;
    Boolean LikeChecker = false;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_main);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Services").child("Nairobi").child("Manicure");
        query = FirebaseDatabase.getInstance().getReference().child("Service Providers");
        img_locations=(ImageView)findViewById(R.id.locations);


        postserviceslist = findViewById(R.id.recycler_services);
        postserviceslist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postserviceslist.setLayoutManager(linearLayoutManager);

        img_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent =new Intent(SpMainActivity.this, LocationActivity.class);
                startActivity(intent);

            }
        });
        DisplayAllUsersPost();
    }

    private void DisplayAllUsersPost()
    {
        FirebaseRecyclerOptions<Posts> options=new FirebaseRecyclerOptions.Builder<Posts>().setQuery(PostsRef,Posts.class).build();
        FirebaseRecyclerAdapter<Posts, SpMainActivity.PostsViewholder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Posts, SpMainActivity.PostsViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SpMainActivity.PostsViewholder holder, int position, @NonNull Posts model) {


               final String PostKey= getRef(position).getKey();

                holder.username.setText(model.getFullname());
                holder.time.setText(model.getTime());
                holder.date.setText(model.getDate());
                holder.phonenumber.setText(model.getPhonenumber());
                holder.servicename.setText(model.getServicename());
//                holder.servicelocation.setText(model.getLocation());
                Picasso.get().load(model.getProfileimage()).into(holder.user_profile_image);
                Picasso.get().load(model.getServiceimage()).into(holder.post_image);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent clickPostIntent=new Intent(SpMainActivity.this,ClickPostActivity.class);
                        clickPostIntent.putExtra("PostKey",PostKey);
                        startActivity(clickPostIntent);
                    }
                });

            }
            @NonNull
            @Override
            public PostsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater inflater=LayoutInflater.from(parent.getContext());
                View view=inflater.inflate(R.layout.all_service_post_layout,parent,false);

                return new PostsViewholder(view);
            }
        };
        postserviceslist.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }


    public static class PostsViewholder extends RecyclerView.ViewHolder {

        TextView username,date,time,phonenumber,servicename,servicelocation;
        CircleImageView user_profile_image;
        ImageView post_image;

        public PostsViewholder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.post_date);
            time=itemView.findViewById(R.id.post_time);
            username=itemView.findViewById(R.id.post_user_name);
            phonenumber=itemView.findViewById(R.id.post_user_phone_number);
            servicename=itemView.findViewById(R.id.post_servicename);

            post_image=itemView.findViewById(R.id.post_image);
            user_profile_image=itemView.findViewById(R.id.post_profile_image);


        }


    }
}