package com.infinity.tolet.appel.to_let;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findhome(View view) {
        Intent intent=new Intent(MainActivity.this,FindRent.class);
        startActivity(intent);
    }

    public void postAdv(View view) {
        Intent intent=new Intent(MainActivity.this,AddPost.class);
        startActivity(intent);

    }
}
