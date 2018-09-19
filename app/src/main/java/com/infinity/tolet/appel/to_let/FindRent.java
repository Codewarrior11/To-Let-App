package com.infinity.tolet.appel.to_let;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.infinity.tolet.appel.to_let.findview.models.Adapter;
import com.infinity.tolet.appel.to_let.findview.models.item;

import java.util.ArrayList;
import java.util.List;

public class FindRent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_rent);

        RecyclerView rv=findViewById(R.id.recycler_view);
        List<item> list=new ArrayList<>();
        list.add(new item(R.drawable.ic_launcher_background,"Appel Mahmud Akib","Single","Uttara,Ajompur","01-Oct-18",12000));
        list.add(new item(R.drawable.ic_launcher_background,"Fiaz Alzi","Single","Uttara,Ajompur","01-Oct-18",2000));
        list.add(new item(R.drawable.ic_launcher_background,"Farhana Mimi Shuma","Family","Uttara,Ajompur","01-Oct-18",15000));
        list.add(new item(R.drawable.ic_launcher_background,"Yeamini Eva","Family","Uttara,Ajompur","01-Oct-18",14000));
        list.add(new item(R.drawable.ic_launcher_background,"fahmida Onty","Hostel","Uttara,Ajompur","01-Oct-18",3000));
        list.add(new item(R.drawable.ic_launcher_background,"Bappy","Single","Uttara,Ajompur","01-Oct-18",12000));
        Adapter adapter=new Adapter(this,list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
