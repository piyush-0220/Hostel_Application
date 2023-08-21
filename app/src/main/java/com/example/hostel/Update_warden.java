package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCanceledListener;
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

public class Update_warden extends AppCompatActivity {
    private EditText w_name,w_email,w_phone,w_post,w_hostel;
    private AppCompatButton btn_update;
    private String name_key,email_key,phone_key,post_key,hostel_key,img_key;
    private DatabaseReference databaseReference;
    private ProgressBar pd;
    private SharedPreferences sharedPreferences;

    private static final String SHARE_PREF_NAME = "Login";

    private String admin_email_warden_key,downloadurl="",n,e,p,h,post;
    private CircleImageView w_img;
    private Bitmap bitmap;
    private StorageReference storageReference;

    private final  int REQ=1;
    private static final String KEY_EMAIL = "email_a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_warden);
        w_name=findViewById(R.id.warden_name_updt);
        w_email=findViewById(R.id.warden_email_updt);
        w_phone=findViewById(R.id.warden_phone_updt);
        w_post=findViewById(R.id.warden_post_updt);
        w_hostel=findViewById(R.id.warden_of_updt);
        btn_update=findViewById(R.id.updt_btn);
        pd=findViewById(R.id.pd_updt_warden);
        w_img=findViewById(R.id.w_img);

        sharedPreferences = this.getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
       String admin=sharedPreferences.getString(KEY_EMAIL,"");

   storageReference= FirebaseStorage.getInstance().getReference();


        if (admin.equals("boyshostel1984@gmail.com")) {

            admin_email_warden_key = "Boys Hostel";
        } else if (admin.equals("girlshostel1984@gmail.com")) {
            admin_email_warden_key= "Girls Hostel";
        }

        Intent i=getIntent();
        name_key=i.getStringExtra("w_name");
        email_key=i.getStringExtra("w_email");
        post_key=i.getStringExtra("w_post");
        phone_key=i.getStringExtra("w_phone");
        hostel_key=i.getStringExtra("w_hostel");

        img_key=i.getStringExtra("w_image");



        w_name.setText(name_key);
        w_email.setText(email_key);
        w_phone.setText(phone_key);
        w_post.setText(post_key);
        w_hostel.setText(hostel_key);
        try {
            if(img_key.equals(""))
            {
                w_img.setBackgroundResource(R.drawable.dp);
            }else
            {
                Glide.with(Update_warden.this).load(img_key).into(w_img);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        w_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = w_name.getText().toString();
               e = w_email.getText().toString();
               p= w_phone.getText().toString();
                post= w_post.getText().toString();
                h= w_hostel.getText().toString();
                if(n.isEmpty() || e.isEmpty() || p.isEmpty() || post.isEmpty() || h.isEmpty())
                {
                    Toast.makeText(Update_warden.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    pd.setVisibility(View.VISIBLE);

                    btn_update.setVisibility(View.GONE);
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
            w_img.setImageBitmap(bitmap);
        }
    }

    private void insertImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finaimg=baos.toByteArray();
        final StorageReference filepath;
        filepath =storageReference.child("warden").child(admin_email_warden_key).child(finaimg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finaimg);
        uploadTask.addOnCompleteListener(Update_warden.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("warden").child(admin_email_warden_key);
        HashMap map=new HashMap();
        map.put("warden_name",n);
        map.put("warden_email",e);
        map.put("warden_phone",p);
        map.put("warden_post",post);
        map.put("warden_hostel",h);
        map.put("warden_image",downloadurl);
        databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                pd.setVisibility(View.GONE);
                btn_update.setVisibility(View.VISIBLE);
                Toast.makeText(Update_warden.this, "Successfully Update", Toast.LENGTH_SHORT).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                pd.setVisibility(View.GONE);
                btn_update.setVisibility(View.VISIBLE);
                Toast.makeText(Update_warden.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}