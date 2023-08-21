
package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class warden_detail extends AppCompatActivity {
    private EditText warden_n, warden_e, warden_mobile, warden_hostel, warden_post;
    private DatabaseReference databaseReference;
    private AppCompatButton _btn;
    private ProgressDialog pd;
    private String a_e_k,downloadurl="",wn,we,wh,wm,wp;
    private Bitmap bitmap;
    private StorageReference storageReference;
    private DatabaseReference db;
    private final  int REQ=1;
    private CircleImageView wardenIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_detail);
        pd = new ProgressDialog(this);

        pd.setMessage("Please wait...");
        warden_n = findViewById(R.id.warden_n);
        warden_e = findViewById(R.id.warden_e);
        wardenIV =findViewById(R.id.warden_IV);
        warden_hostel = findViewById(R.id.warden_hostel);
        warden_mobile = findViewById(R.id.warden_mobile);
        warden_post = findViewById(R.id.warden_post);
        _btn = findViewById(R.id._btn);

        storageReference= FirebaseStorage.getInstance().getReference();
        String admin_email = getIntent().getStringExtra("admin_email_k");
        if (admin_email.equals("boyshostel1984@gmail.com")) {
            a_e_k = "Boys Hostel";
        } else if (admin_email.equals("girlshostel1984@gmail.com")) {
            a_e_k = "Girls Hostel";
        }
        wardenIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 wn = warden_n.getText().toString();
                 we = warden_e.getText().toString();
                 wh = warden_hostel.getText().toString();
                 wm = warden_mobile.getText().toString();
                 wp = warden_post.getText().toString();

                if(wn.isEmpty() || we.isEmpty() || wh.isEmpty() || wm.isEmpty() || wp.isEmpty())
                {
                    Toast.makeText(warden_detail.this, "All fields are required..", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.show();
                    pd.setCanceledOnTouchOutside(false);
                    insertImage();
                }


            }
        });


    }

    private void openGallery() {
        Intent pickimage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK)
        {
            Uri uri= data.getData();
            try{
                bitmap =MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            wardenIV.setImageBitmap(bitmap);
        }
    }

    private void insertImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finaimg=baos.toByteArray();
        final  StorageReference filepath;
        filepath =storageReference.child("warden").child(a_e_k).child(finaimg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finaimg);
        uploadTask.addOnCompleteListener(warden_detail.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl=String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private void insertData() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("warden");
        warden_data warden_data = new warden_data(wn, we, wh, wm, wp,downloadurl);
        databaseReference.child(a_e_k).setValue(warden_data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(warden_detail.this, "Upload successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(warden_detail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}