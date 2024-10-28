package com.android.luxevista.userPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.luxevista.R;
import com.android.luxevista.User;
import com.android.luxevista.database.UserDB;

public class Registration extends AppCompatActivity {

    private TextView txtHaveAccount;
    private EditText edtUsername, edtFullName, edtEmail, edtPassword;
    private UserDB db;
    private LinearLayout btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new UserDB(this);

        txtHaveAccount = findViewById(R.id.txtHaveAccount);
        btnsignup = findViewById(R.id.btnsignup);
        edtUsername = findViewById(R.id.edtUsername);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationProcess();
            }
        });

        txtHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    public void RegistrationProcess(){
        String Name = edtFullName.getText().toString();
        String Username = edtUsername.getText().toString();
        String Email = edtEmail.getText().toString();
        String Password = edtPassword.getText().toString();

        User user = new User(
                Name,
                Username,
                Email,
                Password
        );

        Long result = db.insertUser(user);
        if (result != -1) {
            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Registration failed, try again", Toast.LENGTH_SHORT).show();
        }
    }
}