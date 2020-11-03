package mk.beauty.services.parlours.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import mk.beauty.services.parlours.R;


public class CommentsActivity extends AppCompatActivity {


    private ImageButton postComment;
    private EditText commentInputText;
    private RecyclerView commentslist;
    private String PostKey, current_user_id, postServiceRandomKey ;
    private DatabaseReference UsersRef, ServiceRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        PostKey = getIntent().getExtras().get("PostKey").toString();

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        ServiceRef = FirebaseDatabase.getInstance().getReference().child("Comments").child(PostKey);

        commentslist = (RecyclerView) findViewById(R.id.comments_lists);
        commentslist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        commentslist.setLayoutManager(linearLayoutManager);

        postComment = (ImageButton) findViewById(R.id.post_comment_btn);
        commentInputText = (EditText) findViewById(R.id.comment_input);
        final CircleImageView profileImageView =findViewById(R.id.post_profile_image);

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsersRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String fullname = dataSnapshot.child("fullname").getValue().toString();


                            ValidateComment(fullname);

                            commentInputText.setText("");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }


    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<Comments>options=new FirebaseRecyclerOptions.Builder<Comments>().setQuery(ServiceRef,Comments.class).build();

        FirebaseRecyclerAdapter<Comments,CommentsViewHolder>adapter=new FirebaseRecyclerAdapter<Comments, CommentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CommentsViewHolder commentsViewHolder, int i, @NonNull Comments comments)
            {
                commentsViewHolder.fullname.setText(comments.getFullname());
                commentsViewHolder.comment.setText(comments.getComment());
                commentsViewHolder.date.setText(comments.getDate());
                commentsViewHolder.time.setText(comments.getTime());
            }

            @NonNull
            @Override
            public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewGroup)
            {



                LayoutInflater inflater=LayoutInflater.from(parent.getContext());
                View view=inflater.inflate(R.layout.all_comments_layout,parent,false);
                CommentsViewHolder viewHolder =new CommentsViewHolder(view);
                return viewHolder;

            }
        };
        commentslist.setAdapter(adapter);
        adapter.startListening();
    }

    public static class  CommentsViewHolder extends RecyclerView.ViewHolder
    {

        TextView comment,date,time,fullname;

        View mView;

        public CommentsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            mView=itemView;

            comment=itemView.findViewById(R.id.comment_text);
            date=itemView.findViewById(R.id.comment_date);
            time=itemView.findViewById(R.id.comment_time);
            fullname=itemView.findViewById(R.id.comment_username);
        }
    }

    private void ValidateComment(String fullname)
    {
        String commentText=commentInputText.getText().toString();

        if (TextUtils.isEmpty(commentText))
        {
            Toast.makeText(this, "Please write text to review ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Calendar calForDate=Calendar.getInstance();

            SimpleDateFormat currentDate= new SimpleDateFormat( "MMM dd,yyyy");
           final String saveCurrrentDate=currentDate.format(calForDate.getTime());

            Calendar calForTime=Calendar.getInstance();
            SimpleDateFormat currentTime= new SimpleDateFormat( "H:mm:a");
            final String saveCurrentTime=currentTime.format(calForDate.getTime());

            postServiceRandomKey =current_user_id + saveCurrentTime+saveCurrrentDate;

            HashMap commentsMap =new HashMap();
            commentsMap.put("uid",current_user_id);
            commentsMap.put("comment",commentText);
            commentsMap.put("date",saveCurrrentDate);
            commentsMap.put("time",saveCurrentTime);
            commentsMap.put("fullname",fullname);

            ServiceRef.child(postServiceRandomKey).updateChildren(commentsMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(CommentsActivity.this, "You have reviewed successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(CommentsActivity.this, "Error Occurred: Try Again", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }

    }
}