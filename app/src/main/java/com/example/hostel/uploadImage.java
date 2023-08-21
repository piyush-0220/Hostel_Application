package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.ArrayList;

public class uploadImage extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView title_toolbar_tv;
    private CardView selectImage;

    private Button uploadImageBtn;
    private RecyclerView recyclerView;
    private TextView textView;

    private final int REQ = 2;
    private Bitmap bitmap;
    private ProgressDialog pd;
    private StorageReference storageReference;
    private String downloadurl = "";
    private DatabaseReference reference, dfreference;

    private ArrayList<Uri> uri = new ArrayList<>();
    private RecyclerAdapter_uploadimage adapter;
    private int count = 0;
    private String a_e_k;

    private static final int Read_Permission = 101;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        settoolbar();
        selectImage = findViewById(R.id.addGalleryImage);

        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.selectPhotos);

        adapter = new RecyclerAdapter_uploadimage(uri);
        recyclerView.setLayoutManager(new GridLayoutManager(uploadImage.this, 4));
        recyclerView.setAdapter(adapter);


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(uploadImage.this);


        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri == null) {
                    Toast.makeText(uploadImage.this, "Please upload image", Toast.LENGTH_SHORT).show();
                } else {
                    pd.setMessage("Uploading....");
                    pd.show();
                    pd.setCanceledOnTouchOutside(false);
                    uploadImage();
                }
            }
        });
    }

    private void settoolbar() {
        toolbar = findViewById(R.id.toolbar1);
        title_toolbar_tv = findViewById(R.id.title_toolbar);
        title_toolbar_tv.setText("Full Image ");
    }

    private void uploadImage() {
        String admin_email = getIntent().getStringExtra("admin_email_k");
        if (admin_email.equals("boyshostel1984@gmail.com")) {
            a_e_k = "Boys Hostel";
        } else if (admin_email.equals("girlshostel1984@gmail.com")) {
            a_e_k = "Girls Hostel";
        }

        for (int i = 0; i < uri.size(); i++) {

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri.get(i));

            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);


            byte[] finalimg = baos.toByteArray();


            final StorageReference filePath;


            filePath = storageReference.child("Gallery").child(a_e_k).child("Gallery" + System.currentTimeMillis() + ".jpg");
            final UploadTask uploadTask = filePath.putBytes(finalimg);
            uploadTask.addOnCompleteListener(uploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        downloadurl = String.valueOf(uri);
                                        uploadData();
                                        ;
                                    }
                                });
                            }
                        });

                    } else {
                        pd.dismiss();
                        Toast.makeText(uploadImage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void uploadData() {
        String admin_email = getIntent().getStringExtra("admin_email_k");
        if (admin_email.equals("boyshostel1984@gmail.com")) {
            a_e_k = "Boys Hostel";
        } else if (admin_email.equals("girlshostel1984@gmail.com")) {
            a_e_k = "Girls Hostel";
        }
        dfreference = reference.child("Gallery").child(a_e_k);

        final String uniqueKey = dfreference.push().getKey();
        dfreference.child(uniqueKey).setValue(downloadurl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                count += 1;
                if (count == uri.size()) {
                    Toast.makeText(uploadImage.this, "Images uploaded successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(uploadImage.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void openGallery() {
        if (ContextCompat.checkSelfPermission(uploadImage.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(uploadImage.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permission);
            return;

        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);


        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK && null != data
            ) {
                if (data.getClipData() != null) {
                    int x = data.getClipData().getItemCount();
                    for (int i = 0; i < x; i++) {


                        Uri imageuri = data.getClipData().getItemAt(i).getUri();
                        uri.add(imageuri);

                    }
                    adapter.notifyDataSetChanged();
                    textView.setText("Select images(" + uri.size() + ")");
                } else {
                    Uri imageuri = data.getData();
                    uri.add(imageuri);


                }
                adapter.notifyDataSetChanged();
                textView.setText("Select images(" + uri.size() + ")");


            } else {
                Toast.makeText(this, "You haven't select any images!", Toast.LENGTH_SHORT).show();
            }


        }
    }

}