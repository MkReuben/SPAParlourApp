package mk.beauty.services.parlours.ServiceProviders.SpMombasaServices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import mk.beauty.services.parlours.R;
import mk.beauty.services.parlours.ServiceProviders.SpCommentActivity;

import mk.beauty.services.parlours.ServiceProviders.SpMombasaServices.ClickPost.SpClickPostPrebridalPackageMombasa;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.ClickPost.SpClickPostBraidingNakuru;
import mk.beauty.services.parlours.ServiceProviders.SpNakuruServices.SpBraidingNakuru;
import mk.beauty.services.parlours.Users.LocationActivity;
import mk.beauty.services.parlours.Users.Posts;

public class SpPreBridalPackageMombasa extends AppCompatActivity {

    private DatabaseReference ServicesRef, LikesRef, PostsRef,CommentsRef;
    private RecyclerView postserviceslist;
    RecyclerView.LayoutManager layoutManager;
    private String type = "";
    private FirebaseAuth mAuth;
    private ImageView img_locations;

    RatingBar mRaringBar;

    String currentUserID;
    String manicure;
    Boolean LikeChecker = false;
    private Query query;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_pre_bridal_package_mombasa);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        CommentsRef=FirebaseDatabase.getInstance().getReference().child("Comments");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Services").child("Mombasa").child("PreBridalPackage");
        PostsRef.keepSynced(true);
        query = FirebaseDatabase.getInstance().getReference().child("users");
        query.keepSynced(true);
        img_locations= findViewById(R.id.locations);


        postserviceslist = findViewById(R.id.recycler_sp_prebridal_packages_mombasa);
        postserviceslist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postserviceslist.setLayoutManager(linearLayoutManager);

        mSwipeRefreshLayout = findViewById(R.id.swipenya);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // do something
                        Toast.makeText(SpPreBridalPackageMombasa.this, "Refreshed successfully", Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        img_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent =new Intent(SpPreBridalPackageMombasa.this, LocationActivity.class);
                startActivity(intent);

            }
        });
        DisplayAllUsersPost();
    }

    private void DisplayAllUsersPost()
    {
        FirebaseRecyclerOptions<Posts> options=new FirebaseRecyclerOptions.Builder<Posts>().setQuery(PostsRef,Posts.class).build();
        FirebaseRecyclerAdapter<Posts, SpPreBridalPackageMombasa.PostsViewholder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Posts, SpPreBridalPackageMombasa.PostsViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SpPreBridalPackageMombasa.PostsViewholder holder, int position, @NonNull Posts model) {
                final String PostKey= getRef(position).getKey();

                holder.username.setText(model.getFullname());
                holder.time.setText(model.getTime());
                holder.date.setText(model.getDate());
                holder.phonenumber.setText(model.getPhonenumber());
                holder.servicename.setText(model.getServicename());

//                holder.servicelocation.setText(model.getLocation());
                Picasso.get().load(model.getProfileimage()).into(holder.user_profile_image);
                Picasso.get().load(model.getServiceimage()).into(holder.post_image);

                holder.setLikesButtonStatus(PostKey);
                holder.setCommentPostButtonStatus(PostKey);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent clickPostIntent=new Intent(SpPreBridalPackageMombasa.this, SpClickPostPrebridalPackageMombasa.class);
                        clickPostIntent.putExtra("PostKey",PostKey);
                        startActivity(clickPostIntent);
                    }
                });

//                holder.bookus.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//
//                        Intent clickPostIntent=new Intent(SpBraidingNairobi.this, SpClickPostBraidingNairobi.class);
//                        clickPostIntent.putExtra("PostKey",PostKey);
//                        startActivity(clickPostIntent);
//                    }
//                });


                holder.commentPostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        Intent intent=new Intent(SpPreBridalPackageMombasa.this, SpCommentActivity.class);
                        intent.putExtra("PostKey",PostKey);
                        startActivity(intent);

                    }
                });

                holder.likePostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        LikeChecker = true;

                        LikesRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                if (LikeChecker.equals(true))
                                {

                                    if (dataSnapshot.child(PostKey).hasChild(currentUserID))
                                    {
                                        LikesRef.child(PostKey).child(currentUserID).removeValue();
                                        LikeChecker=false;
                                    }
                                    else
                                    {
                                        LikesRef.child(PostKey).child(currentUserID).setValue(true);
                                        LikeChecker=false;
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {


                            }
                        });

                    }
                });
            }
            @NonNull
            @Override
            public SpPreBridalPackageMombasa.PostsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater inflater=LayoutInflater.from(parent.getContext());
                View view=inflater.inflate(R.layout.sp_all_post_layout,parent,false);

                return new SpPreBridalPackageMombasa.PostsViewholder(view);
            }


        };
        postserviceslist.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }


    public static class PostsViewholder extends RecyclerView.ViewHolder {

        TextView username, date, time, phonenumber, servicename, servicelocation,bookus;
        CircleImageView user_profile_image;
        ImageView post_image;

        ImageButton likePostButton, commentPostButton;
        TextView numberOfLikes, viewReviews, numberOfReviews;
        int countLikes, countReviews;
        String currentUserId;
        DatabaseReference LikesRef, CommentsRef;

        public PostsViewholder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.post_date);
            time = itemView.findViewById(R.id.post_time);
            username = itemView.findViewById(R.id.post_user_name);
            phonenumber = itemView.findViewById(R.id.post_user_phone_number);
            servicename = itemView.findViewById(R.id.post_servicename);
            bookus=itemView.findViewById(R.id.tv_book_us_now);

            post_image = itemView.findViewById(R.id.post_image);
            user_profile_image = itemView.findViewById(R.id.post_profile_image);

            likePostButton = itemView.findViewById(R.id.like_button);
            commentPostButton = itemView.findViewById(R.id.comment_button);
            numberOfLikes = itemView.findViewById(R.id.display_no_of_likes);
            viewReviews = itemView.findViewById(R.id.view_comments);
            numberOfReviews=itemView.findViewById(R.id.view_comments);
            LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
            CommentsRef=FirebaseDatabase.getInstance().getReference().child("Comments");
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        }

        public void setLikesButtonStatus(final String PostKey) {
            LikesRef.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(PostKey).hasChild(currentUserId)) {
                        countLikes = (int) dataSnapshot.child(PostKey).getChildrenCount();
                        likePostButton.setImageResource(R.drawable.ic_liked);
                        numberOfLikes.setText((countLikes + (" Likes")));
                    } else {
                        countLikes = (int) dataSnapshot.child(PostKey).getChildrenCount();
                        likePostButton.setImageResource(R.drawable.ic_like);
                        numberOfLikes.setText((countLikes + (" Likes")));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
        }

        public void setCommentPostButtonStatus(final String PostKey) {


            CommentsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.child(PostKey).hasChild(currentUserId))
                    {
                        countReviews=(int)dataSnapshot.child(PostKey).getChildrenCount();
                        numberOfReviews.setText((countReviews +(" Reviews")));
                    }
                    else
                    {
                        countReviews=(int)dataSnapshot.child(PostKey).getChildrenCount();
                        numberOfReviews.setText((countReviews +(" Reviews")));


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }}
