package com.android.luxevista.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.luxevista.Booking;
import com.android.luxevista.R;
import com.android.luxevista.userPages.DiningBookingView;
import com.android.luxevista.userPages.PoolBookingView;
import com.android.luxevista.userPages.RoomBookingView;
import com.android.luxevista.userPages.SpaBookingView;

import java.util.List;

public class BookingPageAdapter extends RecyclerView.Adapter<BookingPageAdapter.ViewHolder> {

    private Context context;
    private List<Booking> bookingList;

    public BookingPageAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingPageAdapter.ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.txtBookingType.setText(booking.getBookingType());
        holder.txtBookingId.setText(String.valueOf(booking.getBookingId()));
        holder.txtBookingDate.setText(booking.getBookingDate());
        holder.bookingType.setText(booking.getBookingTitle());
        if(booking.getBookingTitle().equals("room")){
            if(booking.getBookingStatus().equals("confirmed")){
                holder.btnRoomBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, RoomBookingView.class);
                        intent.putExtra("bookingId", booking.getBookingId());
                        context.startActivity(intent);
                    }
                });
            }
        }else
            if(booking.getBookingTitle().equals("Spa Booking")){
                holder.btnRoomBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SpaBookingView.class);
                        intent.putExtra("bookingId", booking.getBookingId());
                        context.startActivity(intent);
                    }
                });
            }else
                if(booking.getBookingTitle().equals("Dining")){
                    holder.btnRoomBooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, DiningBookingView.class);
                            intent.putExtra("bookingId", booking.getBookingId());
                            context.startActivity(intent);
                        }
                    });
                }else
                    if(booking.getBookingTitle().equals("Pool")){
                        holder.btnRoomBooking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, PoolBookingView.class);
                                intent.putExtra("bookingId", booking.getBookingId());
                                context.startActivity(intent);
                            }
                        });
                    }else
                        if(booking.getBookingTitle().equals("Explore")){
                            holder.btnRoomBooking.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, DiningBookingView.class);
                                    intent.putExtra("bookingId", booking.getBookingId());
                                    context.startActivity(intent);
                                }
                            });
                        }
    }

    @Override
    public int getItemCount() {
        return (bookingList != null) ? bookingList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBookingType, txtBookingId, txtBookingDate, bookingType;
        LinearLayout btnRoomBooking;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBookingType = itemView.findViewById(R.id.bookingTitle);
            txtBookingId = itemView.findViewById(R.id.bookingId);
            txtBookingDate = itemView.findViewById(R.id.bookingDate);
            bookingType = itemView.findViewById(R.id.bookingType);
            btnRoomBooking = itemView.findViewById(R.id.btnRoomBooking);
        }
    }
}
