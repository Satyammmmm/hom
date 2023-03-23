package com.example.hom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    ImageButton imageButtonn;
    EditText editfrist, editlast;
    Button btninsertt;
    ProgressDialog progressDialog;
    private static final int Gallery_Code=1;
    Uri imageURL = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButtonn = findViewById(R.id.imageButton);
        editfrist = findViewById(R.id.EditFristname);
        editlast = findViewById(R.id.Editlastname);
        btninsertt = findViewById(R.id.btninsert);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("student");
        mStorage=FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);

        imageButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==Gallery_Code && resultCode==RESULT_OK){
                imageURL=data.getData();
                imageButtonn.setImageURI(imageURL);
            }

            btninsertt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fn = editfrist.getText().toString().trim();
                    String ln = editlast.getText().toString().trim();

                    if (!(fn.isEmpty() && ln.isEmpty() && imageURL != null)){
                        progressDialog.setTitle("Uploading...");
                        progressDialog.show();

                        StorageReference filepath = mStorage.getReference().child("imagePost").child(imageURL.getLastPathSegment());
                        filepath.putFile(imageURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> downloasUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String t = task.getResult().toString();
                                        DatabaseReference newPost = mRef.push();
                                        newPost.child("firstname").setValue(fn);
                                        newPost.child("Lastname").setValue(ln);
                                        newPost.child("image").setValue(task.getResult().toString());
                                        progressDialog.dismiss();

                                        Intent in = new Intent(MainActivity.this,RetriveDataInRecycle.class);
                                        startActivity(in);                                    }
                                });
                            }
                        });
                    }
                }
            });
    }
}