package com.android.luxevista.userPages;

import static com.android.luxevista.SharedPreference.USER_ID;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.luxevista.Booking;
import com.android.luxevista.LuxeService;
import com.android.luxevista.MainActivity;
import com.android.luxevista.R;
import com.android.luxevista.SharedPreference;
import com.android.luxevista.User;
import com.android.luxevista.database.BookingDB;
import com.android.luxevista.database.RoomDB;
import com.android.luxevista.database.ServicesDB;
import com.android.luxevista.database.UserDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SpaBookingPage extends AppCompatActivity {
    private TextView txtSpaType, txtTime, txtDuration, txtTotalPrice, txtFee, txtFinalPrice, txtDate;
    private LinearLayout btnPayNow;
    private Spinner spinnerDuration;
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
    private String userID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spa_booking_page);
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
        setTime();
        setCalendar();
        setDuration();
        setGuestDetails();
        setServiceDetails();
        saveBooking();

    }

    private void setServiceDetails() {
        txtSpaType.setText(service.getServiceType());
        Picasso.get()
                .load(service.getCoverImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.home_screen)
                .into(coverImage);
    }
    private void saveBooking() {
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTime.getText().toString().isEmpty() || txtDate.getText().toString().isEmpty() ||
                        edtName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() ||
                        edtPhone.getText().toString().isEmpty())
                {
                    Toast.makeText(SpaBookingPage.this, "Please fill all the required fields", Toast.LENGTH_SHORT).show();
                }else{
                    if(checkbox.isChecked()){
                        double totalPrice = service.getPrice();
                        double tax = (totalPrice*6)/100;
                        if(spinnerDuration.getSelectedItem().toString().equals("60 Minute")){
                            totalPrice = service.getPrice();
                        }else{
                            totalPrice = totalPrice * 1.5;
                            tax = (totalPrice*6)/100;
                        }

                        String serviceID = String.valueOf(serviceId);
                        booking = new Booking(
                                "Spa Booking",
                                service.getServiceType(),
                                txtDate.getText().toString(),
                                "confirmed",
                                edtSpecialReq.getText().toString(),
                                totalPrice,
                                tax,
                                edtName.getText().toString(),
                                edtEmail.getText().toString(),
                                edtPhone.getText().toString(),
                                userID,
                                txtTime.getText().toString(),
                                serviceID,
                                "0"
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
                        Toast.makeText(SpaBookingPage.this, "Please Accept the therms and condition", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void setDuration() {
        String[] durations = new String[]{
                "60 Minute",
                "90 Minute"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_selected_item,
                durations
        );

        adapter.setDropDownViewResource(R.layout.spinner_selected_item_list);
        spinnerDuration.setAdapter(adapter);
        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                double basePrice = service.getPrice();
                double durationPrice;
                fee = 6;

                if (spinnerDuration.getSelectedItem().toString().equals("60 Minute")) {
                    durationPrice = basePrice;
                    txtDuration.setText(String.format("$ %.2f for 60 minutes", durationPrice));
                } else {
                    durationPrice = basePrice * 1.5;
                    txtDuration.setText(String.format("$ %.2f for 90 minutes", durationPrice));
                }

                fee = (durationPrice * fee)/100;
                txtTotalPrice.setText(String.format("$ %.2f", durationPrice));
                txtFee.setText(String.format("$ %.2f", fee));
                txtFinalPrice.setText(String.format("$ %.2f", durationPrice + fee));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setCalendar() {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                String formattedDate = String.format("%04d-%02d-%02d", date.getYear(), date.getMonth() + 1, date.getDay());
                txtDate.setText(formattedDate);
            }
        });
    }
    private void setTime() {
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                int minHour = 9;
                int maxHour = 19;

                TimePickerDialog timePickerDialog = new TimePickerDialog(SpaBookingPage.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (minute < 15) {
                                    minute = 0;
                                } else if (minute >= 15 && minute <= 45) {
                                    minute = 30;
                                } else {
                                    minute = 0;
                                    hourOfDay = (hourOfDay + 1) % 24;
                                }

                                if (hourOfDay >= minHour && hourOfDay <= maxHour) {
                                    txtTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                                } else {
                                    Toast.makeText(SpaBookingPage.this, "Selected time is outside of available hours (9:00 AM to 7:00 PM)", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });
    }
    private void findComponents() {

        txtSpaType = findViewById(R.id.txtSpaType);
        txtDuration = findViewById(R.id.txtDuration);
        txtTime = findViewById(R.id.txtTime);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtFee = findViewById(R.id.txtFee);
        txtFinalPrice = findViewById(R.id.txtFinalPrice);
        txtDate = findViewById(R.id.txtDate);
        spinnerDuration = findViewById(R.id.spinnerDuration);
        calendarView = findViewById(R.id.calendarView);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtSpecialReq = findViewById(R.id.edtSpecialReq);
        checkbox = findViewById(R.id.checkbox);
        btnPayNow = findViewById(R.id.btnPayNow);
        coverImage = findViewById(R.id.coverImage);

    }
    private void setGuestDetails(){
        user = userDB.getUserById(1);

        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        if(user.getPhone() != null){
            edtPhone.setText(user.getPhone());
        }
    }
}