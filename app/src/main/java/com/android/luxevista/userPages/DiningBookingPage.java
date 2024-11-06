package com.android.luxevista.userPages;

import static com.android.luxevista.SharedPreference.USER_ID;

import android.content.Intent;
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

import java.util.List;

public class DiningBookingPage extends AppCompatActivity {
    private TextView txtType, txtCuisine, txtPrice, txtTotalPrice, txtFee, txtFinalPrice, txtDate;
    private LinearLayout btnPayNow;
    private Spinner spinnerGuests;
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
    double basePrice = 50;
    double totalPrice;
    private String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dining_booking_page);
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

        findComponents();
        setCalendar();
        setDiningPrice();
        setGuestDetails();
        setServiceDetails();
        saveBooking();

    }

    private void saveBooking() {
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceID = String.valueOf(serviceId);
                if (txtDate.getText().toString().isEmpty() ||
                        edtName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() ||
                        edtPhone.getText().toString().isEmpty())
                {
                    Toast.makeText(DiningBookingPage.this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(checkbox.isChecked()){

                        booking = new Booking(
                                "Dining",
                                service.getServiceType(),
                                txtDate.getText().toString(),
                                "confirmed",
                                edtSpecialReq.getText().toString(),
                                totalPrice,
                                fee,
                                edtName.getText().toString(),
                                edtEmail.getText().toString(),
                                edtPhone.getText().toString(),
                                userID,
                                serviceID,
                                spinnerGuests.getSelectedItem().toString()
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
                        Toast.makeText(DiningBookingPage.this, "Please Accept the therms and condition", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void setServiceDetails() {
        txtType.setText(service.getServiceType());
        txtCuisine.setText(service.getCuisine());
        String imageUrl = service.getCoverImage();

        if (imageUrl.startsWith("drawable/")) {
            String drawableName = imageUrl.replace("drawable/", "").replace(".jpg", "");
            int drawableResId = this.getResources().getIdentifier(drawableName, "drawable", this.getPackageName());

            if (drawableResId != 0) {
                coverImage.setImageResource(drawableResId);
            } else {
                coverImage.setImageResource(R.drawable.home_screen);
            }
        }else{
            Picasso.get()
                    .load(service.getCoverImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.home_screen)
                    .into(coverImage);
        }
    }
    private void setGuestDetails() {
        user = userDB.getUserById(1);

        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        if(user.getPhone() != null){
            edtPhone.setText(user.getPhone());
        }
    }
    private void setDiningPrice() {
        String[] Guests = new String[]{
                "1 Guest",
                "2 Guests",
                "3 Guests",
                "4 Guests"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                Guests
        );

        adapter.setDropDownViewResource(R.layout.spinner_selected_item_list);
        spinnerGuests.setAdapter(adapter);
        spinnerGuests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerGuests.getSelectedItem().toString().equals("1 Guest")){
                    calTotal(1);
                }else if (spinnerGuests.getSelectedItem().toString().equals("2 Guests")){
                    calTotal(2);
                } else if (spinnerGuests.getSelectedItem().toString().equals("3 Guests")) {
                    calTotal(3);
                }else {
                    calTotal(4);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void calTotal(int guests){
        service = db.getServiceById(serviceId);

        fee = 12;
        double basePrice = 50;
        double totalPrice = basePrice * guests;
        fee = (totalPrice * fee)/100;
        double finalPrice = totalPrice + fee;

        txtPrice.setText(String.format("$ %.2f per persone", basePrice));
        txtTotalPrice.setText(String.format("$ %.2f", totalPrice));
        txtFee.setText(String.format("$ %.2f", fee));
        txtFinalPrice.setText(String.format("$ %.2f", finalPrice));

    }
    private void findComponents() {

        txtType = findViewById(R.id.txtType);
        txtPrice = findViewById(R.id.txtPrice);
        txtCuisine = findViewById(R.id.txtCuisine);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtFee = findViewById(R.id.txtFee);
        txtFinalPrice = findViewById(R.id.txtFinalPrice);
        txtDate = findViewById(R.id.txtDate);
        spinnerGuests = findViewById(R.id.spinnerGuests);
        calendarView = findViewById(R.id.calendarView);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtSpecialReq = findViewById(R.id.edtSpecialReq);
        checkbox = findViewById(R.id.checkbox);
        btnPayNow = findViewById(R.id.btnPayNow);
        coverImage = findViewById(R.id.coverImage);
    }
    private void setCalendar() {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                String formattedDate = String.format("%04d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());
                //String formattedDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay();
                txtDate.setText(formattedDate);
            }
        });
    }
}