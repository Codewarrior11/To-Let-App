package com.infinity.tolet.appel.to_let;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RentView extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    String postId;
    ProgressDialog progressDialog;

    TextView rentType,rentPrice,rentMonth,rentAddress,otherDetails;
    ImageView rentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_view);
        rentType=findViewById(R.id.des_title);
        rentPrice=findViewById(R.id.rent_price);
        rentMonth=findViewById(R.id.rent_month1);
        rentAddress=findViewById(R.id.rent_address1);
        otherDetails=findViewById(R.id.other_details);
        rentImage=findViewById(R.id.rent_image);
        progressDialog=new ProgressDialog(RentView.this);
        progressDialog.setMessage("Content Loading.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        postId=getIntent().getStringExtra("post_id");
        myRef = database.getReference("post/"+postId);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rentType.setText(dataSnapshot.child("category").getValue().toString());
                rentPrice.setText(dataSnapshot.child("rentprice").getValue().toString());
                rentMonth.setText(dataSnapshot.child("month").getValue().toString());
                rentAddress.setText(dataSnapshot.child("address").getValue().toString());
                otherDetails.setText("Bedroom: "+dataSnapshot.child("bedroom").getValue().toString()+"\n"+"Bathroom: "+dataSnapshot.child("bathroom").getValue().toString()+"\n"
                +"Drawing Room: "+dataSnapshot.child("drawing").getValue().toString()+"\n"+"Dinning Space: "+dataSnapshot.child("dinning").getValue().toString()+"\n"
                +"Water Bill"+dataSnapshot.child("waterbill").getValue().toString()+"\n"+"Gas Bill"+dataSnapshot.child("gasbill").getValue().toString()+"\n");
                Picasso.get().load(dataSnapshot.child("imagelink").getValue().toString()).into(rentImage);
                //String value=dataSnapshot.getValue(String.class);
                //Toast.makeText(RentView.this,value,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();
    }
}
