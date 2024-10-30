package com.android.luxevista.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.luxevista.R;
import com.android.luxevista.LuxeService;
import com.android.luxevista.userPages.DiningServiceDetailsPage;
import com.android.luxevista.userPages.PoolServiceDetailsPage;
import com.android.luxevista.userPages.RoomDetailsPage;
import com.android.luxevista.userPages.SpaServiceDetailsPage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePageServiceAdapter extends RecyclerView.Adapter<HomePageServiceAdapter.ViewHolder> {
    private Context context;
    private List<LuxeService> serviceList;

    public HomePageServiceAdapter(Context context, List<LuxeService> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public HomePageServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_service_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageServiceAdapter.ViewHolder holder, int position) {
        LuxeService service = serviceList.get(position);
        holder.txtServiceTitle.setText(service.getServiceTitle());
        holder.txtServiceType.setText(service.getServiceType());

        Uri imageUri = Uri.parse(service.getCoverImage());

        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(holder.imvCover);

        holder.serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = service.getId();
                if(service.getServiceTitle().equals("Spa service")){
                    Intent intent = new Intent(context, SpaServiceDetailsPage.class);
                    intent.putExtra("serviceId", id);
                    context.startActivity(intent);
                }else if(service.getServiceTitle().equals("Dining service")){
                    Intent intent = new Intent(context, DiningServiceDetailsPage.class);
                    intent.putExtra("serviceId", id);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, PoolServiceDetailsPage.class);
                    intent.putExtra("serviceId", id);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return (serviceList != null) ? serviceList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvCover;
        TextView txtServiceTitle, txtServiceType;
        LinearLayout serviceBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvCover = itemView.findViewById(R.id.coverImage);
            txtServiceTitle = itemView.findViewById(R.id.txtTitle);
            txtServiceType = itemView.findViewById(R.id.txtType);
            serviceBtn = itemView.findViewById(R.id.btnService);
        }
    }
}
