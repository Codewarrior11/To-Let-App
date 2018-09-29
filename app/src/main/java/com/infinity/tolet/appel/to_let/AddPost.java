package com.infinity.tolet.appel.to_let;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPost extends AppCompatActivity {
    ImageView image1, image2, image3, image4;
    Spinner month, category;
    Uri selectedImage = null;
    Button submit, cancel;
    ProgressDialog progressDialog;
    String key, pOwnerName, pOwnerImage, pOwnerPhone;
    FirebaseAuth mAuth;

    EditText rentprice, address, bedroom, bathroom, drwaing, dinning, waterbill, gasbill, others;

    String selectedMonth, selectedCategory;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("/post/");
    DatabaseReference myRef2 = database.getReference("/UserInfo/");

    StorageReference storage = FirebaseStorage.getInstance().getReference("post_photos/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        image1 = findViewById(R.id.rentim1);
        image2 = findViewById(R.id.rentim2);
        image3 = findViewById(R.id.rentim3);
        image4 = findViewById(R.id.rentim4);
        month = findViewById(R.id.month);
        category = findViewById(R.id.category);
        submit = findViewById(R.id.submitPostBtn);
        cancel = findViewById(R.id.cancel);
        rentprice = findViewById(R.id.price);
        address = findViewById(R.id.address);
        bedroom = findViewById(R.id.bedroom);
        bathroom = findViewById(R.id.bathroom);
        drwaing = findViewById(R.id.drwaing);
        dinning = findViewById(R.id.dinning);
        waterbill = findViewById(R.id.waterbill);
        gasbill = findViewById(R.id.gasbill);
        others = findViewById(R.id.others);
        mAuth = FirebaseAuth.getInstance();

//Array list for month and category spinner
        String[] monthlist = {"January", "February", "March", "April", "May", "Jun", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, monthlist);
        String[] catrgorylist = {"Family", "Bachelor", "Sublet", "Single", "Male", "Female"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, catrgorylist);

        month.setAdapter(adapter);
        category.setAdapter(adapter2);

        //get month spinner value
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMonth = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddPost.this, "Please Select an Item", Toast.LENGTH_LONG).show();
            }
        });

        //get category spinner value
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddPost.this, "Please Select an Itmem", Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        //Submit button onClick Event Listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(rentprice.getText().toString())) {
                    rentprice.setError("Please Fill up the form");
                    return;
                } else if (TextUtils.isEmpty(address.getText().toString())) {
                    address.setError("Please Fill up the form");
                    return;
                } else if (selectedImage == null) {
                    Toast.makeText(AddPost.this, "Select your house picture", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    progressDialog = new ProgressDialog(AddPost.this);
                    progressDialog.setMessage("Please Wait.Post Uploading");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    key = myRef.push().getKey();
                    myRef.child(key).child("key").setValue(key);
                    myRef.child(key).child("p_owner_name").setValue(pOwnerName);
                    myRef.child(key).child("p_owner_phone").setValue(pOwnerPhone);
                    myRef.child(key).child("p_owner_image").setValue(pOwnerImage);
                    myRef.child(key).child("month").setValue(selectedMonth);
                    myRef.child(key).child("address").setValue(address.getText().toString());
                    myRef.child(key).child("category").setValue(selectedCategory);
                    myRef.child(key).child("rentprice").setValue(rentprice.getText().toString());
                    myRef.child(key).child("bedroom").setValue(bedroom.getText().toString());
                    myRef.child(key).child("bathroom").setValue(bathroom.getText().toString());
                    myRef.child(key).child("drawing").setValue(drwaing.getText().toString());
                    myRef.child(key).child("dinning").setValue(dinning.getText().toString());
                    myRef.child(key).child("waterbill").setValue(waterbill.getText().toString());
                    myRef.child(key).child("gasbill").setValue(gasbill.getText().toString());
                    myRef.child(key).child("posttime").setValue(new SimpleDateFormat("dd-M-yy", Locale.getDefault()).format(new Date()).toString());

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
                                    Toast.makeText(AddPost.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddPost.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(AddPost.this, "Your post Successfully uploaded", Toast.LENGTH_LONG).show();
                            clear();
                            Intent intent = new Intent(AddPost.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddPost.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }


            }
        });
    }

    public void clear() {
        selectedImage = null;
        //Picasso.get().load(selectedImage).into(image1);
        rentprice.setText("");
        address.setText("");
        bedroom.setText("");
        bathroom.setText("");
        dinning.setText("");
        drwaing.setText("");
        waterbill.setText("");
        gasbill.setText("");
        others.setText("");
    }

    //Start Activity result for image loading
    public void imageLoad(int reqCode) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, reqCode);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pOwnerName = dataSnapshot.child(mAuth.getUid()).child("name").getValue().toString();
                pOwnerPhone = dataSnapshot.child(mAuth.getUid()).child("phone").getValue().toString();
                pOwnerImage = dataSnapshot.child(mAuth.getUid()).child("imagelink").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //onActivity Result for Image load
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            Picasso.get().load(selectedImage).resize(500, 400).into(image1);
        }

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            Picasso.get().load(selectedImage).resize(500, 400).into(image2);
        }
        if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            Picasso.get().load(selectedImage).resize(500, 400).into(image3);
        }
        if (requestCode == 4 && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            Picasso.get().load(selectedImage).resize(500, 400).into(image4);
        }
    }
    //-------------------------------------------

    //Image Load method for addPost
    public void loadim1(View view) {
        imageLoad(1);
    }

    public void loadim2(View view) {
        imageLoad(2);
    }

    public void loadim3(View view) {
        imageLoad(3);
    }

    public void loadim4(View view) {
        imageLoad(4);
    }
    //-----------------------------------------
}
