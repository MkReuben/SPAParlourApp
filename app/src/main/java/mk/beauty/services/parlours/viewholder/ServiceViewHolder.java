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

public class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtServiceProviderName, txtServiceName, txtServiceDescription, txtServicePhoneNumber, txtServiceLocation;
    public ImageView imageView;
    public ItemClickListener listener;

    public ServiceViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView= itemView.findViewById(R.id.popular_image);
        txtServiceName = itemView.findViewById(R.id.popular_service_name_details);
        txtServiceProviderName = itemView.findViewById(R.id.popular_service_provider_name);
        txtServiceDescription = itemView.findViewById(R.id.popular_description_details);
        txtServicePhoneNumber = itemView.findViewById(R.id.popular_contacts);
        txtServiceLocation =itemView.findViewById(R.id.popular_location);


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
