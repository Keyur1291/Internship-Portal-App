package com.android.internshipportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

public class admin_home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;
    MaterialButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_admin_home);

        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(View -> {
            mAuth.signOut();
            Toast.makeText(admin_home.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(admin_home.this, login.class));
        });

        toolbar = findViewById(R.id.appbar);

        bottomNavigationView = findViewById(R.id.bnav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_profile) {
                startActivity(new Intent(admin_home.this, students_list.class));
            }
            return true;
        });
    }
}