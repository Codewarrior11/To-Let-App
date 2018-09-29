package com.infinity.tolet.appel.to_let;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView pro_name,pro_emaill;
    ImageView pro_pic;
    FirebaseAuth mAuth;
    NavigationView navigationView;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView=findViewById(R.id.nabar);

        drawerLayout=findViewById(R.id.navdrwaer);
        actionBarDrawerToggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view=navigationView.getHeaderView(0);
        mAuth=FirebaseAuth.getInstance();
        pro_emaill=view.findViewById(R.id.pro_email);
        pro_name=view.findViewById(R.id.pro_name);
        pro_pic=view.findViewById(R.id.drawer_pro_pic);
        if (mAuth.getCurrentUser().getUid()==""){
            pro_name.setText("Guest");
            pro_emaill.setText("Email Address Not Avilabe");
            Picasso.get().load(R.drawable.cirle_user).into(pro_pic);

        }
        else{
            pro_name.setText(mAuth.getCurrentUser().getEmail().toString());
            pro_emaill.setText(mAuth.getCurrentUser().getEmail().toString());
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void findhome(View view) {
        Intent intent=new Intent(MainActivity.this,FindRent.class);
        startActivity(intent);
    }

    public void postAdv(View view) {
        Intent intent=new Intent(MainActivity.this,AddPost.class);
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.profile){

        }
        if (id==R.id.addpost){
            Intent intent=new Intent(MainActivity.this,AddPost.class);
            startActivity(intent);
        }

        if(id==R.id.your_post){

        }
        if (id==R.id.notification){

        }
        if(id==R.id.logout){

        }

        return true;
    }

    public void main_signup(View view) {
        Intent intent=new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);

    }

    public void main_login(View view) {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
