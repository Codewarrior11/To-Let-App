package com.infinity.tolet.appel.to_let;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SignUp extends AppCompatActivity {
    EditText reg_name, reg_email, reg_phone, reg_address, reg_pass, reg_retypepass;
    RadioGroup reg_gender, reg_type;
    RadioButton radioButton1, radioButton;
    Button reg_submit;
    ImageView reg_image;
    String gender, type;
    FirebaseAuth mAuth;
    String key;
    Uri selectedImage = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("/UserInfo/");
    StorageReference storage = FirebaseStorage.getInstance().getReference("profile_photos/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        reg_name = findViewById(R.id.reg_name);
        reg_email = findViewById(R.id.reg_email);
        reg_phone = findViewById(R.id.reg_phone);
        reg_address = findViewById(R.id.reg_address);
        reg_pass = findViewById(R.id.reg_pass);
        reg_retypepass = findViewById(R.id.reg_retypepass);
        reg_gender = findViewById(R.id.radio_gender);
        reg_type = findViewById(R.id.radio_regtype);
        reg_image = findViewById(R.id.reg_image);
        reg_submit = findViewById(R.id.reg_submit);
        mAuth = FirebaseAuth.getInstance();
        reg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = findViewById(i);
                gender = radioButton.getText().toString();
            }
        });
        reg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton1 = findViewById(i);
                type = radioButton1.getText().toString();
            }
        });


        reg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(reg_name.getText().toString())) {
                    reg_name.setError("Please Fill up the form");
                    return;
                }

                if (TextUtils.isEmpty(reg_email.getText().toString())) {
                    reg_email.setError("Please Fill up the form");
                    return;
                }
                if (TextUtils.isEmpty(reg_address.getText().toString())) {
                    reg_address.setError("Please Fill up the form");
                    return;
                }
                if (TextUtils.isEmpty(reg_phone.getText().toString())) {
                    reg_phone.setError("Please Fill up the form");
                    return;
                }

                if (TextUtils.isEmpty(reg_pass.getText().toString())) {
                    reg_pass.setError("Please Fill up the form");
                    return;
                }
                if (TextUtils.isEmpty(reg_retypepass.getText().toString())) {
                    reg_retypepass.setError("Please Fill up the form");
                    return;
                }
//                if (reg_pass.getText().toString()!=reg_retypepass.getText().toString()) {
//                    reg_retypepass.setError("Password Doesn't match");
//                    return;
//                }
                mAuth.createUserWithEmailAndPassword(reg_email.getText().toString(), reg_retypepass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        key = authResult.getUser().getUid();
                        myRef.child(key).child("name").setValue(reg_name.getText().toString());
                        myRef.child(key).child("email").setValue(reg_email.getText().toString());
                        myRef.child(key).child("phone").setValue(reg_phone.getText().toString());
                        myRef.child(key).child("address").setValue(reg_address.getText().toString());
                        myRef.child(key).child("password").setValue(reg_pass.getText().toString());
                        myRef.child(key).child("gender").setValue(gender);
                        myRef.child(key).child("reg_type").setValue(type);

                        storage.child(key).putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storage.child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        myRef.child(key).child("imagelink").setValue(uri.toString());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                                //Toast.makeText(SignUp.this, "" + downloadUrl, Toast.LENGTH_LONG).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(SignUp.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });


                        Toast.makeText(SignUp.this, "Registration Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUp.this, LoginActivity.class);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Registration Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            Picasso.get().load(selectedImage).resize(500, 400).into(reg_image);
        }
    }

    public void imageLoad(int reqCode) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, reqCode);
    }

    public void reg_image(View view) {

        imageLoad(1);
    }
}
