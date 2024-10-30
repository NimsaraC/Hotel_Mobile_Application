package com.android.luxevista.adapter;

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
import com.android.luxevista.Explore;
import com.android.luxevista.userPages.ExploreDetailsPage;
import com.android.luxevista.userPages.SpaServiceDetailsPage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePageExploreAdapter extends RecyclerView.Adapter<HomePageExploreAdapter.ViewHolder> {
    private Context context;
    private List<Explore> exploreList;

    public HomePageExploreAdapter(Context context, List<Explore> exploreList) {
        this.context = context;
        this.exploreList = exploreList;
    }

    @NonNull
    @Override
    public HomePageExploreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_explore_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageExploreAdapter.ViewHolder holder, int position) {
        Explore explore = exploreList.get(position);
        holder.txtServiceTitle.setText(explore.getTitle());
        holder.txtServiceType.setText(explore.getType());
        holder.txtServicePrice.setText(String.format("%s Per Person", explore.getPrice()));

        Uri imageUri = Uri.parse(explore.getCoverImage());

        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(holder.imvCover);

        holder.exploreBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = explore.getId();
                Intent intent = new Intent(context, ExploreDetailsPage.class);
                intent.putExtra("exploreId", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (exploreList != null) ? exploreList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvCover;
        TextView txtServiceTitle, txtServiceType, txtServicePrice;
        LinearLayout exploreBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvCover = itemView.findViewById(R.id.coverImage);
            txtServiceTitle = itemView.findViewById(R.id.txtTitle);
            txtServiceType = itemView.findViewById(R.id.txtType);
            txtServicePrice = itemView.findViewById(R.id.txtServicePrice);
            exploreBtn = itemView.findViewById(R.id.btnService);
        }
    }
}
