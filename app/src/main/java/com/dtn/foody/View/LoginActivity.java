package com.dtn.foody.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dtn.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button logIn, loginFacebook, loginGoogle;
    private TextView email, password, forgotPass, register;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");

        firebaseAuth = FirebaseAuth.getInstance();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("Debug", "onDataChange");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Debug", "onCancelled: " + error.getMessage());
            }
        });
        init();
    };
    public void init()
    {
        logIn = (Button) findViewById(R.id.Restore);
        loginFacebook = (Button) findViewById(R.id.loginFacebook);
        loginGoogle = (Button) findViewById(R.id.loginGoogle);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        forgotPass = (TextView) findViewById(R.id.forgotPass);
        register = (TextView) findViewById(R.id.Register);

        register.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        logIn.setOnClickListener(this);
        loginGoogle.setOnClickListener(this);
        loginFacebook.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch(id)
        {
            case R.id.Restore:
                signInActivity();
                break;
            case R.id.loginFacebook:
                break;
            case R.id.Register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forgotPass:
                Intent intent1 = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent1);
                break;
        }
    }
    public void signInActivity()
    {
        firebaseAuth.signOut();
        String em = email.getText().toString();
        String pass = password.getText().toString();
        if(em.trim().length() <= 0)
        {
            Toast.makeText(this, getString(R.string.registerError) + " " + getString(R.string.email), Toast.LENGTH_SHORT).show();
        }
        else if(pass.trim().length() <= 0)
        {
            Toast.makeText(this, getString(R.string.registerError) + " " + getString(R.string.password), Toast.LENGTH_SHORT).show();
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


class Users
{
    String name;
    Boolean gioitinh;
    String tuoi;
    public Users(){}
    public Users(String hoten, Boolean gioitinh, String tuoi)
    {
        this.name = hoten;
        this.gioitinh = gioitinh;
        this.tuoi = tuoi;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(Boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }
};