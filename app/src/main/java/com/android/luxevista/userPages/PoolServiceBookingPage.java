package com.android.luxevista.userPages;

import static com.android.luxevista.SharedPreference.USER_ID;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.Booking;
import com.android.luxevista.EventDecorator;
import com.android.luxevista.LuxeService;
import com.android.luxevista.R;
import com.android.luxevista.SharedPreference;
import com.android.luxevista.User;
import com.android.luxevista.database.BookingDB;
import com.android.luxevista.database.ServicesDB;
import com.android.luxevista.database.UserDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PoolServiceBookingPage extends AppCompatActivity {
    private TextView txtType, txtCapacity, txtPrice, txtHalfPrice, txtDate, txtTotalPrice, txtFee, txtFinalPrice;
    private LinearLayout btnPayNow;
    private Spinner spinnerType;
    private MaterialCalendarView calendarView;
    private EditText edtName, edtEmail, edtPhone, edtSpecialReq ;
    private CheckBox checkbox;
    private ImageView coverImage;
    private ServicesDB db;
    private BookingDB bookingDB;
    private UserDB userDB;
    private List<LuxeService> serviceList;
    private int serviceId;
    private LuxeService service;
    private User user;
    private Booking booking;
    private double fee;
    private double totalPrice, finalPrice;
    private List<Booking> bookings;
    private List<String> bookingDates;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pool_service_booking_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new ServicesDB(this);
        bookingDB = new BookingDB(this);
        userDB = new UserDB(this);
        SharedPreference sharedPreference = new SharedPreference();
        userID = String.valueOf(sharedPreference.GetInt(this, USER_ID));


        serviceId = getIntent().getIntExtra("serviceId", 0);

        service = db.getServiceById(serviceId);
        fee = 12;

        findComponents();
        setCalendar();
        setBookingPrice();
        setServiceDetails();
        setGuestDetails();
        saveBooking();

    }
    private void setCalendar(){
        bookingDB = new BookingDB(this);
        bookings = bookingDB.getAllBookingsByServiceId(serviceId);
        bookingDates = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Booking booking : bookings) {
            String checkInDateStr = booking.getBookingDate();
            bookingDates.add(checkInDateStr);
        }
        HashSet<CalendarDay> dates = new HashSet<>();
        for (String dateString : bookingDates) {
            String[] parts = dateString.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int day = Integer.parseInt(parts[2]);
            dates.add(CalendarDay.from(year, month, day));
        }

        calendarView.setEnabled(false);
        calendarView.addDecorator(new EventDecorator(Color.RED, dates));

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                String formattedDate = String.format("%04d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());
                //String formattedDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay();
                if (bookingDates.contains(formattedDate)) {
                    Toast.makeText(PoolServiceBookingPage.this, "Date is not available", Toast.LENGTH_SHORT).show();
                }else{
                    txtDate.setText(formattedDate);

                }
            }
        });
    }
    private void saveBooking() {
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceID = String.valueOf(serviceId);
                String capacity = String.valueOf(service.getCapacity());
                String dayType = spinnerType.getSelectedItem().toString();
                if (txtDate.getText().toString().isEmpty() ||
                        edtName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() ||
                        edtPhone.getText().toString().isEmpty())
                {
                    Toast.makeText(PoolServiceBookingPage.this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(checkbox.isChecked()){

                        booking = new Booking(
                                "Pool",
                                service.getServiceType(),
                                txtDate.getText().toString(),
                                "confirmed",
                                edtSpecialReq.getText().toString(),
                                totalPrice,
                                edtName.getText().toString(),
                                edtEmail.getText().toString(),
                                edtPhone.getText().toString(),
                                userID,
                                serviceID,
                                capacity,
                                dayType
                        );

                        Long result = bookingDB.insertBooking(booking);
                        if (result != -1) {
                            Toast.makeText(getApplicationContext(), "Booking successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Booking failed", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(PoolServiceBookingPage.this, "Please Accept the therms and condition", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void setBookingPrice() {
        String[] Type = new String[]{
                "Full Day",
                "Half Day"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                Type
        );

        adapter.setDropDownViewResource(R.layout.spinner_selected_item_list);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerType.getSelectedItem().toString().equals("Full Day")){
                    txtTotalPrice.setText(String.format("$ %.2f", totalPrice));
                }else{
                    totalPrice = totalPrice/2;
                    txtTotalPrice.setText(String.format("$ %.2f", totalPrice));
                    txtFee.setText(String.format("$ %.2f", fee/2));
                    finalPrice = (fee/2) + totalPrice;
                    txtFinalPrice.setText(String.format("$ %.2f", finalPrice));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setServiceDetails() {
        txtType.setText(service.getServiceType());
        txtCapacity.setText(String.format("Up to %s guests", service.getCapacity()));
        txtPrice.setText(String.format("$%s for full day", service.getPrice()));
        txtHalfPrice.setText(String.format("$%s for half-day", service.getPrice()/2));
        totalPrice = service.getPrice();
        fee = (totalPrice * fee)/100;
        finalPrice = totalPrice + fee;
        txtTotalPrice.setText(String.format("$ %.2f", totalPrice));
        txtFee.setText(String.format("$ %.2f", fee));
        txtFinalPrice.setText(String.format("$ %.2f", finalPrice));

        Picasso.get()
                .load(service.getCoverImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(coverImage);
    }
    private void setGuestDetails() {
        user = userDB.getUserById(1);

        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        if(user.getPhone() != null){
            edtPhone.setText(user.getPhone());
        }
    }
    private void findComponents() {
        txtType = findViewById(R.id.txtType);
        txtPrice = findViewById(R.id.txtPrice);
        txtHalfPrice = findViewById(R.id.txtHalfPrice);
        txtCapacity = findViewById(R.id.txtCapacity);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtFee = findViewById(R.id.txtFee);
        txtFinalPrice = findViewById(R.id.txtFinalPrice);
        txtDate = findViewById(R.id.txtDate);
        spinnerType = findViewById(R.id.spinnerType);
        calendarView = findViewById(R.id.calendarView);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtSpecialReq = findViewById(R.id.edtSpecialReq);
        checkbox = findViewById(R.id.checkbox);
        btnPayNow = findViewById(R.id.btnPayNow);
        coverImage = findViewById(R.id.coverImage);
    }
}