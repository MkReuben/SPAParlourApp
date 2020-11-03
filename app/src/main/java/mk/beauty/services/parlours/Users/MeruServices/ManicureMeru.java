package mk.beauty.services.parlours.Users.MeruServices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import mk.beauty.services.parlours.R;

public class ManicureMeru extends AppCompatActivity {
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
        setContentView(R.layout.activity_manicure_meru);
    }
}
