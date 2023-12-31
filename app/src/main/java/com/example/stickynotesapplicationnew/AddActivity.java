package com.example.stickynotesapplicationnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stickynotesapplicationnew.databinding.ActivityAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    private String title="",content="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = binding.notetitle.getText().toString();
                content = binding.notecontent.getText().toString();

                saveNote();
            }
        });
    }

    private void saveNote() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving");
        progressDialog.setMessage("Your Note");
        progressDialog.show();

        String noteId = UUID.randomUUID().toString();
        NotesModel notesModel = new NotesModel(noteId,title,content,firebaseAuth.getUid());

        //FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("notes")
                .document(noteId)
                .set(notesModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Note Saved",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Note not Saved "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });
    }
}