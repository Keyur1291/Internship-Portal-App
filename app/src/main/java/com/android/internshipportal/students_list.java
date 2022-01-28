package com.android.internshipportal;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class students_list extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        final ArrayList<NumbersView> arrayList = new ArrayList<NumbersView>();

        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "1", "One"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "2", "Two"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "3", "Three"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "4", "Four"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "5", "Five"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "6", "Six"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "7", "Seven"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "8", "Eight"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "9", "Nine"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "10", "Ten"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "11", "Eleven"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "12", "Twelve"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "13", "Thirteen"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "14", "Fourteen"));
        arrayList.add(new NumbersView(R.mipmap.ic_launcher, "15", "Fifteen"));

        NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(this, arrayList);
        ListView numbersListView = findViewById(R.id.listview);

        numbersListView.setAdapter(numbersArrayAdapter);

    }
}