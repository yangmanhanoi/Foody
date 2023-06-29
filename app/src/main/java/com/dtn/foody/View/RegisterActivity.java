package com.dtn.foody.View;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dtn.foody.Controller.DangkyController;
import com.dtn.foody.Model.ThanhVienModel;
import com.dtn.foody.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private Button register;
    private EditText email, password, passwordAgain;
    DangkyController dangkyController;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        init();
    }
    public void init()
    {
        register = findViewById(R.id.Restore);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordAgain = findViewById(R.id.passwordagain);
        register.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        String em = email.getText().toString();
        String pass = password.getText().toString();
        String passAgain = passwordAgain.getText().toString();
        if(em.trim().length() <= 0)
        {
            Toast.makeText(this, getString(R.string.registerError) + " " + getString(R.string.email), Toast.LENGTH_SHORT).show();
        }
        else if(pass.trim().length() <= 0)
        {
            Toast.makeText(this, getString(R.string.registerError)+ " " + getString(R.string.password), Toast.LENGTH_SHORT).show();
        }
        else if(!passAgain.equals(pass))
        {
            Toast.makeText(this, getString(R.string.announce), Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setMessage(getString(R.string.message));
            Drawable drawable = getResources().getDrawable(R.drawable.icon2);
            progressDialog.setIndeterminateDrawable(drawable);
            progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            ThanhVienModel thanhVienModel = new ThanhVienModel();
                            thanhVienModel.setHoten(em);
                            thanhVienModel.setHinhanh("user.png");
                            String uid = task.getResult().getUser().getUid();
                            dangkyController = new DangkyController();
                            dangkyController.themThanhVienController(thanhVienModel, uid);

                            Toast.makeText(RegisterActivity.this, "Register successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Register unsuccessfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}
