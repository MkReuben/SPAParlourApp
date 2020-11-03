package mk.beauty.services.parlours.viewholder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import mk.beauty.services.parlours.R;

public class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtDescription,txtName;
    public ImageView imageView;
    public ItemClickListener listener;

    public TestViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView= itemView.findViewById(R.id.test_image);
        txtDescription= itemView.findViewById(R.id.test_description);
        txtName = itemView.findViewById(R.id.test_name);


        Picasso.get().load(String.valueOf(imageView)).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
            @Override
            public void onSuccess()
            {

            }

            @Override
            public void onError(Exception e)
            {

            }
        });
    }

    @Override
    public void onClick(View view)
    {
        ItemClickListener.onClick(view,getAdapterPosition(),false);

    }
    public  void setItemClickListener(ItemClickListener listener)
    {
        this.listener=listener;
    }


}
