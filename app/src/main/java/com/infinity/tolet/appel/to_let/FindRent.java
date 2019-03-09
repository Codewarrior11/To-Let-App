package com.infinity.tolet.appel.to_let;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infinity.tolet.appel.to_let.findview.models.Adapter;
import com.infinity.tolet.appel.to_let.findview.models.item;

import java.util.ArrayList;
import java.util.List;

public class FindRent extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("post/");
    List<item> list=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_rent);

        final RecyclerView rv=findViewById(R.id.recycler_view);
        progressDialog=new ProgressDialog(FindRent.this);
        progressDialog.setMessage("Content Loading .Please Wait.....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    //dataSnapshot.getChildren().iterator().next().child("name");
                    //Toast.makeText(FindRent.this,"Value: "+snapshot.child("address").getValue(),Toast.LENGTH_LONG).show();
                    list.add(new item(snapshot.child("key").getValue().toString(),snapshot.child("p_owner_image").getValue().toString(),
                            snapshot.child("p_owner_name").getValue().toString(),snapshot.child("category").getValue().toString(),snapshot.child("address").getValue().toString(),snapshot.child("month").getValue().toString(),Integer.parseInt(snapshot.child("rentprice").getValue().toString())));

                }
                Adapter adapter=new Adapter(FindRent.this,list);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(FindRent.this));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        list.add(new item("1",R.drawable.ic_launcher_background,"Appel Mahmud Akib","Single","Uttara,Ajompur","01-Oct-18",12000));
//        list.add(new item("2",R.drawable.ic_launcher_background,"Fiaz Alzi","Single","Uttara,Ajompur","01-Oct-18",2000));
//        list.add(new item("3",R.drawable.ic_launcher_background,"Farhana Mimi Shuma","Family","Uttara,Ajompur","01-Oct-18",15000));
//        list.add(new item("4",R.drawable.ic_launcher_background,"Yeamini Eva","Family","Uttara,Ajompur","01-Oct-18",14000));
//        list.add(new item("5",R.drawable.ic_launcher_background,"fahmida Onty","Hostel","Uttara,Ajompur","01-Oct-18",3000));
//        list.add(new item("6",R.drawable.ic_launcher_background,"Bappy","Single","Uttara,Ajompur","01-Oct-18",12000));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
