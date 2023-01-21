package com.android.internshipportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.WindowCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class faculty_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MaterialButton logout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_faculty_home);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(View -> {
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(faculty_home.this, R.style.ThemeOverlay_App_MaterialAlertDialog);
            dialogBuilder.setTitle("Logout");
            dialogBuilder.setMessage("Are you sure want to Logout");
            dialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
                mAuth.signOut();
                Toast.makeText(faculty_home.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(faculty_home.this, login.class));
            }).setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            dialogBuilder.show();
        });

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.home);


        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new main_faculty());
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new main_faculty());
            fragmentTransaction.commit();
        }

        if (item.getItemId() == R.id.about) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new about());
            fragmentTransaction.commit();
        }

        return true;
    }

}