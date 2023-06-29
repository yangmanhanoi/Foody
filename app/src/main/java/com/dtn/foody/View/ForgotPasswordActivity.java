package com.dtn.foody.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dtn.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private Button restore;
    private TextView newRegister;
    private EditText restoreEmail;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);

        firebaseAuth = FirebaseAuth.getInstance();
        init();

    }
    public void init()
    {
        restore = findViewById(R.id.Restore);
        newRegister = findViewById(R.id.newRegister);
        restoreEmail = findViewById(R.id.restoreEmail);

        restore.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.Restore:
                String email = restoreEmail.getText().toString();
                if(checkEmail(email))
                {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPasswordActivity.this, "Đã gửi tin nhắn đến email của bạn.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.newRegister:
                Intent intent = new Intent(ForgotPasswordActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
    private boolean checkEmail(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
