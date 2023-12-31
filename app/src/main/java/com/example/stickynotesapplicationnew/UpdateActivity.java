package com.example.stickynotesapplicationnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.stickynotesapplicationnew.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class UpdateActivity extends AppCompatActivity {

    private String id,title,content;
    ActivityUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        binding.notetitle.setText(title);
        binding.notecontent.setText(content);

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setTitle("Deleting");
                FirebaseFirestore.getInstance()
                        .collection("notes")
                        .document(id)
                        .delete();
                progressDialog.show();
                finish();
            }
        });

        binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = binding.notetitle.getText().toString();
                content = binding.notecontent.getText().toString();
                updateNote();
            }
        });

    }



    private void updateNote() {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating");
        progressDialog.setMessage("Your Note");
        progressDialog.show();

       // String noteId = UUID.randomUUID().toString();
        NotesModel notesModel = new NotesModel(id,title,content,firebaseAuth.getUid());

        //FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("notes")
                .document(id)
                .set(notesModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateActivity.this,"Note Saved",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateActivity.this,"Note not Saved "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });
    }

}