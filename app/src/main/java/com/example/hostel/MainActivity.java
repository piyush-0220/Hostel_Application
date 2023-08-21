package com.example.hostel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private ImageView room_iv, apply_leave_iv, fee_iv, gallery_iv;
    private TextView user_name_tv, see_gallery_tv,rqst_see_all,empty_notes;
    private FirebaseUser user;
    private Bitmap bitmap;
    private StorageReference storageReference;
    private DatabaseReference databaseReference, reference, db;
    private String userid, user_name, user_email, user_parent_name, user_phone;
    private String user_name_var, user_parent_var, user_email_var, user_phone_var, user_hostel_var, hostel_name;
    //   private int[] images;
    //  private SliderAdapter adapter;

    //  private SliderView sliderView;
    private RecyclerView recyclerView;
    private ArrayList<user_request_fragment_data> arrayList;
    private user_request_fragment_Adapter adapter_1;

    private final static String apj_hostel = "APJ Boys Hostel";
    private final static String sv_hostel = "S V Boys Hostel";
    private final static String girls_hostel = "K C Girls Hostel";
    private Dialog dialog;
    private CircleImageView userImage, user_img,img_view;
    private ImageSlider imageSlider;
    private ProgressBar progressBar,pd_view;
    private final  int REQ=1;
    private int s = 1;
    private String downloadurl="",user_img_var;
    private LinearLayout ll_view;

//    @Override
//    protected void onStart() {
//        super.onStart();
//         user=FirebaseAuth.getInstance().getCurrentUser();
//
//
//    }

    private ArrayList<SlideModel> slideModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        adapter_1 = new user_request_fragment_Adapter(arrayList, MainActivity.this);
        adapter_1.notifyDataSetChanged();
         empty_notes=findViewById(R.id.empty_notes);
        dialog = new Dialog(MainActivity.this);
        imageSlider = findViewById(R.id.slider_);
        rqst_see_all=findViewById(R.id.rqst_see);
        progressBar=findViewById(R.id.pd_main);
        pd_view=findViewById(R.id.pd_view);
        ll_view=findViewById(R.id.ll_view);
        storageReference= FirebaseStorage.getInstance().getReference();



        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        empty_notes.setVisibility(View.VISIBLE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            openLogin();
            finish();

        }
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        userImage = findViewById(R.id.userImage);
        userid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");


        room_iv = findViewById(R.id.room_iv);
        apply_leave_iv = findViewById(R.id.appply_leave_iv);
        fee_iv = findViewById(R.id.fee_iv);
        gallery_iv = findViewById(R.id.gallery_iv);
        user_name_tv = findViewById(R.id.user_name_tv);
        empty_notes=findViewById(R.id.empty_notes);
        see_gallery_tv = findViewById(R.id.see_gallery_tv);
        drawerLayout.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);


        View nav_header = navigationView.getHeaderView(0);
        TextView user_name = nav_header.findViewById(R.id.user_name_tv);
        TextView user_email = nav_header.findViewById(R.id.user_email_tv);
        user_img = nav_header.findViewById(R.id.user_img);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        slideModels.add(new SlideModel(R.drawable.s1,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.s2,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.s5,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.s3,ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, user_profile.class);
//                i.putExtra("name", user_name_var);
//                i.putExtra("parent", user_parent_var);
//                i.putExtra("phone", user_phone_var);
//                i.putExtra("email", user_email_var);
//                startActivity(i);
                Dialog dialog1=new Dialog(MainActivity.this);
                dialog1.setContentView(R.layout.dialog_user_profile);


                 img_view=dialog1.findViewById(R.id.user_profile_img) ;
                EditText user_email_profile = dialog1.findViewById(R.id.user_email_profile);
                user_email_profile.setText(user_email_var);
                try {if(user_img_var.equals(""))
                {
                    img_view.setBackgroundResource(R.drawable.dp);

                }else {
                    Glide.with(MainActivity.this).load(user_img_var).into(img_view);
                }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                AppCompatButton user_img_btn =dialog1.findViewById(R.id.user_btn_img);
                img_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });

                user_img_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String user_email_pro = user_email_profile.getText().toString();
                        if(user_email_pro.isEmpty())
                        {
                           user_email_profile.setError("Required");
                           user_email_profile.requestFocus();
                        }else {
                            dialog.show();
                            insertImage(user_email_pro);
                            dialog1.dismiss();
                        }

                    }
                });

            dialog1.show();
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_img.performClick();
            }
        });


        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User profile = snapshot.getValue(User.class);
                    if (profile != null) {

                        user_name_var = profile.name;
                        user_parent_var = profile.parent_name;
                        user_phone_var = profile.phone;
                        user_email_var = profile.email;
                        user_img_var=profile.user_img;
//                       user_hostel_var=profile.

                        user_name_tv.setText(user_name_var);
                        user_name.setText(user_name_var);
                        user_email.setText(user_email_var);
                        try {if(user_img_var.equals(""))
                        {
//                            user_img.setBackgroundResource(R.drawable.dp);
//                            userImage.setBackgroundResource(R.drawable.dp);
                        }else {
                            Glide.with(MainActivity.this).load(user_img_var).into(user_img);
                            Glide.with(MainActivity.this).load(user_img_var).into(userImage);

                        }

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }

                request();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();

            }
        });


        room_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, room_detail.class);
                intent.putExtra("name", user_name_var);
                intent.putExtra("phone", user_phone_var);
                intent.putExtra("parent_name", user_parent_var);
                startActivity(intent);


            }
        });


        apply_leave_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ApplyLeave.class);
                intent.putExtra("name", user_name_var);
                intent.putExtra("phone", user_phone_var);
                intent.putExtra("parent_name", user_parent_var);
                intent.putExtra("userId", userid);
                startActivity(intent);
            }
        });


        fee_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeeDetail.class);
                intent.putExtra("name", user_name_var);
                intent.putExtra("phone", user_phone_var);
                intent.putExtra("parent_name", user_parent_var);
                startActivity(intent);
            }
        });



        gallery_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Gallery_user.class);
                intent.putExtra("name", user_name_var);
                intent.putExtra("phone", user_phone_var);
                intent.putExtra("parent_name", user_parent_var);
                startActivity(intent);
            }

        });
        see_gallery_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery_iv.performClick();
            }
        });
        rqst_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, user_request_activity.class);
                intent.putExtra("name", user_name_var);
                intent.putExtra("phone", user_phone_var);
                intent.putExtra("parent_name", user_parent_var);
                intent.putExtra("userId", userid);
                startActivity(intent);
            }
        });


    }

    private void insertImage(String email) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finaimg=baos.toByteArray();
        final StorageReference filepath ;
        if(hostel_name== null)
        {
            hostel_name="GirlsHostel";
        }
       filepath = storageReference.child("student").child(hostel_name).child(finaimg + "jpg");
        final UploadTask uploadTask = filepath.putBytes(finaimg);
        uploadTask.addOnCompleteListener(MainActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    insertData(email,downloadurl);
                                }
                            });
                        }
                    });
                }


            }
        });

    }

    private void insertData(String e,String img) {
        HashMap m=new HashMap();
        m.put("email",e);
        m.put("user_img",img);
        databaseReference.child(userid).updateChildren(m).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Information updated successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
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
            img_view.setImageBitmap(bitmap);
        }
    }


    private void request() {



        recyclerView = findViewById(R.id.rv_main);


        reference = FirebaseDatabase.getInstance().getReference().child("Hostel");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {


                for (DataSnapshot snapshot3 : snapshot2.getChildren()) {
                    for (DataSnapshot snapshot4 : snapshot3.getChildren()) {

                        for (DataSnapshot snapshot5 : snapshot4.child("student").getChildren()) {

                            student_room_data user_room_data = snapshot5.getValue(student_room_data.class);
                            if (user_room_data.getSt_name().equals(user_name_var) || user_room_data.getParent_name().equals(user_parent_var) && user_room_data.getSt_phone().equals(user_phone_var)) {
                                user_room_request_data user_room_request_data = new user_room_request_data(user_room_data.getSt_name(), user_room_data.getSt_phone(), user_room_data.getParent_name(), user_room_data.getHostel_name(), user_room_data.getRoom_no());
                                if (user_room_request_data != null) {

                                    String st_hostel_x = user_room_request_data.getHostel_name();
                                    fetch_leave(st_hostel_x);
                                    fetch_Gallery(st_hostel_x);

                                    pd_view.setVisibility(View.GONE);
                                    ll_view.setVisibility(View.VISIBLE);

                                } else {

                                    pd_view.setVisibility(View.GONE);
                                    ll_view.setVisibility(View.VISIBLE);
                                    slideModels.add(new SlideModel(R.drawable.s1,ScaleTypes.FIT));
                                    slideModels.add(new SlideModel(R.drawable.s2,ScaleTypes.FIT));
                                    slideModels.add(new SlideModel(R.drawable.s5,ScaleTypes.FIT));
                                    slideModels.add(new SlideModel(R.drawable.s3,ScaleTypes.FIT));
                                    imageSlider.setImageList(slideModels, ScaleTypes.FIT);

                                }
                            }


//                                }else
//                                {
//                                    slideModels.add(new SlideModel(R.drawable.s1, ScaleTypes.FIT));
//                                    slideModels.add(new SlideModel(R.drawable.s2, ScaleTypes.FIT));
//                                    slideModels.add(new SlideModel(R.drawable.s3, ScaleTypes.FIT));
//                                    slideModels.add(new SlideModel(R.drawable.s4, ScaleTypes.FIT));
//                                    imageSlider.setImageList(slideModels,ScaleTypes.FIT);
//                                    break;
//
//                                }


                   }

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


    }


    private void fetch_Gallery(String h) {


        if (h.equals(apj_hostel) || h.equals(sv_hostel)) {
            hostel_name = "Boys Hostel";
        } else if (h.equals(girls_hostel)) {
            hostel_name = "Girls Hostel";
        }

        reference = FirebaseDatabase.getInstance().getReference().child("Gallery").child(hostel_name);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if(snapshot.exists()) {

                         for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                             String data = (String) snapshot1.getValue();

                             slideModels.add(new SlideModel(data, ScaleTypes.FIT));


                             if (slideModels.size() > 0) {

                                 imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                             } else {

                                 slideModels.add(new SlideModel(R.drawable.s1,ScaleTypes.FIT));
                                 slideModels.add(new SlideModel(R.drawable.s2,ScaleTypes.FIT));
                                 slideModels.add(new SlideModel(R.drawable.s5,ScaleTypes.FIT));
                                 slideModels.add(new SlideModel(R.drawable.s3,ScaleTypes.FIT));
                                 imageSlider.setImageList(slideModels, ScaleTypes.FIT);

                             }
                         }
                     }
                     else {
                         slideModels.add(new SlideModel(R.drawable.s1,ScaleTypes.FIT));
                         slideModels.add(new SlideModel(R.drawable.s2,ScaleTypes.FIT));
                         slideModels.add(new SlideModel(R.drawable.s5,ScaleTypes.FIT));
                         slideModels.add(new SlideModel(R.drawable.s3,ScaleTypes.FIT));
                         imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                     }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void fetch_leave(String st_hostel_x) {
        if (st_hostel_x.equals(apj_hostel) || st_hostel_x.equals(sv_hostel)) {
            hostel_name = "Boys Hostel";
            empty_notes.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            fetch_request(hostel_name);


        } else if (st_hostel_x.equals(girls_hostel)) {
            hostel_name = "Girls Hostel";
            empty_notes.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            fetch_request(hostel_name);
        }
    }

    private void fetch_request(String hostel_name) {


        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Request").child(hostel_name).child("Leave").child(userid);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                adapter_1.notifyDataSetChanged();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    user_request_fragment_data data = snapshot1.getValue(user_request_fragment_data.class);
                    arrayList.add(data);

                }
                if(arrayList.size()>0) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    empty_notes.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter_1);
                }else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    empty_notes.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                   progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Sorry , can't retrieve leave ", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void openLogin() {

        startActivity(new Intent(MainActivity.this, Login.class));

        finish();

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
        if (item.getItemId() == R.id.my_Request) {
            drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, user_request_activity.class);
            intent.putExtra("name", user_name_var);
            intent.putExtra("phone", user_phone_var);
            intent.putExtra("parent_name", user_parent_var);
            intent.putExtra("userId", userid);
            startActivity(intent);

        } else if (item.getItemId() == R.id.Service) {
            drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, user_service_activity.class);
            intent.putExtra("name", user_name_var);
            intent.putExtra("phone", user_phone_var);
            intent.putExtra("parent_name", user_parent_var);
            intent.putExtra("userId", userid);
            startActivity(intent);
        } else if (item.getItemId() == R.id.Support) {
            drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(MainActivity.this, user_support_activity.class);
            intent.putExtra("name", user_name_var);
            intent.putExtra("phone", user_phone_var);
            intent.putExtra("parent_name", user_parent_var);
            intent.putExtra("userId", userid);
            startActivity(intent);
        } else if (item.getItemId() == R.id.share) {
            drawerLayout.closeDrawer(GravityCompat.START);
            try {
                Intent s = new Intent(Intent.ACTION_SEND);
                s.setType("text/plain");
                s.putExtra(Intent.EXTRA_SUBJECT, "GPJ Hostel");
                s.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                startActivity(Intent.createChooser(s, "Share with"));
            } catch (Exception e) {
                Toast.makeText(this, "Unable to share this application", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.rate_a) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
            Intent r = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(r);
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        } else if (item.getItemId() == R.id.Logout) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            openLogin();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

}

