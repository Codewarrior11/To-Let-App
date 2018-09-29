package com.infinity.tolet.appel.to_let;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText login_mail,login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_mail=findViewById(R.id.login_mail);
        login_pass=findViewById(R.id.login_pass);
        mAuth=FirebaseAuth.getInstance();
    }

    public void login_submit(View view) {

        if (TextUtils.isEmpty(login_mail.getText().toString())) {
            login_mail.setError("Please Fill up the form");
            return;
        }
        if (TextUtils.isEmpty(login_pass.getText().toString())) {
            login_pass.setError("Please Fill up the form");
            return;
        }
        mAuth.signInWithEmailAndPassword(login_mail.getText().toString(),login_pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("email",authResult.getUser().getEmail());
                intent.putExtra("name",authResult.getUser().getEmail());
                startActivity(intent);
                Toast.makeText(LoginActivity.this,"Login Success:"+authResult.getUser().getEmail(),Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Login Failed:"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
