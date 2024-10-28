package com.android.luxevista.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
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
import com.android.luxevista.Room;
import com.android.luxevista.database.RoomDB;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePageRoomAdapter extends RecyclerView.Adapter<HomePageRoomAdapter.ViewHolder> {
    private Context context;
    private List<Room> roomList;

    public HomePageRoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public HomePageRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_room_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageRoomAdapter.ViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.txtRoomType.setText(room.getRoomType());
        holder.txtRoomPrice.setText(String.format("$%s / Night", room.getRate()));
        holder.txtBedType.setText(room.getBedType());

        Uri imageUri = Uri.parse(room.getCoverImage());

        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(holder.imvCover);

        holder.roomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (roomList != null) ? roomList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvCover;
        TextView txtRoomType, txtRoomPrice, txtBedType;
        LinearLayout roomBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvCover = itemView.findViewById(R.id.coverImage);
            txtRoomType = itemView.findViewById(R.id.txtRoomType);
            txtRoomPrice = itemView.findViewById(R.id.txtRoomPrice);
            txtBedType = itemView.findViewById(R.id.txtBedType);
            roomBtn = itemView.findViewById(R.id.btnRoom);
        }
    }
}