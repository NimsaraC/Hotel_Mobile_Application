package com.android.luxevista.adapter;

import android.app.Activity;
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
import com.android.luxevista.Room;
import com.android.luxevista.database.RoomDB;
import com.android.luxevista.userPages.RoomDetailsPage;
import com.squareup.picasso.Picasso;

import java.security.AccessController;
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
                .placeholder(R.drawable.home_screen)
                .error(R.drawable.home_screen)
                .into(holder.imvCover);

        holder.roomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = room.getRoomId();
                Intent intent = new Intent(context, RoomDetailsPage.class);
                intent.putExtra("roomId", id);
                context.startActivity(intent);
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
