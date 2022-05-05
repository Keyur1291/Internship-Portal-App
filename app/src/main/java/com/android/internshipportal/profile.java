package com.android.internshipportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class profile extends AppCompatActivity {

    MaterialTextView pname, penrollment, pmobile, pdepartment, pemail;
    MaterialButton edit, delete;
    MaterialToolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser firebaseUser;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        pname = findViewById(R.id.profileName);
        penrollment = findViewById(R.id.profileEnrollment);
        pdepartment = findViewById(R.id.profileDepartment);
        pmobile = findViewById(R.id.profileMobileNumber);
        pemail = findViewById(R.id.profileEmail);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        edit = findViewById(R.id.editprofilebtn);
        edit.setOnClickListener(View -> {
            startActivity(new Intent(profile.this, edit_profile.class));
        });

        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    pname.setText(documentSnapshot.getString("name"));
                    penrollment.setText(documentSnapshot.getString("enrollment"));
                    pdepartment.setText(documentSnapshot.getString("department"));
                    pmobile.setText(documentSnapshot.getString("mobile"));
                    pemail.setText(documentSnapshot.getString("email"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        delete = findViewById(R.id.deleteprofilebtn);
        delete.setOnClickListener(v -> {
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(profile.this, R.style.ThemeOverlay_App_MaterialAlertDialog);
            dialogBuilder.setTitle("Delete Profile");
            dialogBuilder.setIcon(R.drawable.ic_baseline_delete_forever_24);
            dialogBuilder.setMessage("Are you sure want to delete your profile? this action can't be reverted.");
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAuth.getCurrentUser().delete();
                    Task<Void> documentReference = fStore.collection("Users").document(userID).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(profile.this, "Profile Deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(profile.this, login.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(profile.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogBuilder.show();
        });

    }
}