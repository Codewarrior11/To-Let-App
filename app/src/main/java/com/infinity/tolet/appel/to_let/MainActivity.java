package com.infinity.tolet.appel.to_let;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView pro_name, pro_emaill;
    ImageView pro_pic;
    FirebaseAuth mAuth;
    NavigationView navigationView;
    View view;
    String id,type;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("UserInfo/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nabar);

        drawerLayout = findViewById(R.id.navdrwaer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        pro_emaill = view.findViewById(R.id.pro_email);
        pro_name = view.findViewById(R.id.pro_name);
        pro_pic = view.findViewById(R.id.drawer_pro_pic);
        if (mAuth.getCurrentUser() == null) {
            pro_name.setText("Guest");
            pro_emaill.setText("Email Address Not Avilabe");
            Picasso.get().load(R.drawable.cirle_user).into(pro_pic);

        } else {
            id = mAuth.getUid();
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    type=dataSnapshot.child(id).child("reg_type").getValue().toString();
                    pro_name.setText(dataSnapshot.child(id).child("name").getValue().toString());
                    pro_emaill.setText(mAuth.getCurrentUser().getEmail());
                    Picasso.get().load(dataSnapshot.child(id).child("imagelink").getValue().toString()).into(pro_pic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        //pro_emaill.setText(getIntent().getStringExtra("email"));
        //pro_name.setText(getIntent().getStringExtra("name"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void findhome(View view) {
        Intent intent = new Intent(MainActivity.this, FindRent.class);
        startActivity(intent);
    }

    public void postAdv(View view) {
        Intent intent = new Intent(MainActivity.this, AddPost.class);
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {

        } else if (id == R.id.addpost) {
            if(mAuth.getCurrentUser()==null||type=="Registration as Renter"){
                Toast.makeText(MainActivity.this,"You are not eligible to Add a new post.\nYou need to registration as a Owner.",Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(MainActivity.this, AddPost.class);
                startActivity(intent);
            }
        } else if (id == R.id.your_post) {

        } else if (id == R.id.notification) {

        } else if (id == R.id.logout) {
            mAuth.signOut();
            Intent intent=new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void main_signup(View view) {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);

    }

    public void main_login(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
