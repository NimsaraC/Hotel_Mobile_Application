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
import com.android.luxevista.SharedPreference;
import com.android.luxevista.database.UserDB;

public class Login extends AppCompatActivity {

    private TextView txtDontHaveAccount;
    private LinearLayout btnsignin;
    private EditText edtEmail, edtPassword;
    private UserDB db;
    private Boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new UserDB(this);

        txtDontHaveAccount = findViewById(R.id.txtDontHaveAccount);
        btnsignin = findViewById(R.id.btnsignin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProcess();
            }
        });
        txtDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });
    }

    public void loginProcess (){
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
        int result = db.userLogin(email, password);
        if(result != 0 ){

            SharedPreference sharedPreference = new SharedPreference();
            status = true;
            sharedPreference.SaveBoolean(getApplicationContext(),status,SharedPreference.STATUS);
            sharedPreference.SaveInt(getApplicationContext(),result,SharedPreference.USER_ID);

            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
        }

    }
}