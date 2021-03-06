package com.example.daboot.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daboot.Login.JoinInfo;
import com.example.daboot.Login.Login;
import com.example.daboot.R;
import com.example.daboot.WelfareInfoDone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class Info extends Fragment {

    private static int REQUEST_FIND_CODE = 1;
    private Uri photoUri,file = null;
    private Bitmap bitmap = null;
    private boolean imgseting=false;
    private String imgname="";
    private String qual="";

    private FirebaseAuth mFirebaseAuth;
    private ImageButton btn_modify,btn_logout;
    private Switch swt1, swt2, swt3;
    private ImageView imageView;

    // ??????????????????
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private boolean a = true;
    private boolean b = false;
    private boolean c = false;

    // id??? ????????????
    private TextView tv_name, tv_id, tv_email, tv_qualification, tv_field;
    String id;

    public void Call_Toast(String message){ Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();}


    private void getUserImage(ImageView imageView, String path){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef  = storage.getReference();
        storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Call_Toast("????????? ????????????");
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        swt1 = view.findViewById(R.id.switch1);
        swt2 = view.findViewById(R.id.switch2);
        swt3 = view.findViewById(R.id.switch3);

        swt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    a = true;
                    Toast.makeText(getActivity(), "???????????? ?????????", Toast.LENGTH_SHORT).show();
                } else {
                    a = false;
                    Toast.makeText(getActivity(), "???????????? ????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b = true;
                    Toast.makeText(getActivity(), "???????????? ?????????", Toast.LENGTH_SHORT).show();
                } else {
                    b = false;
                    Toast.makeText(getActivity(), "???????????? ????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    c = true;
                    Toast.makeText(getActivity(), "???????????? ?????????", Toast.LENGTH_SHORT).show();
                } else {
                    c = false;
                    Toast.makeText(getActivity(), "???????????? ????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_name = view.findViewById(R.id.tv_info_name);
        tv_id = view.findViewById(R.id.tv_info_id);
        tv_email = view.findViewById(R.id.tv_info_email);
        tv_qualification = view.findViewById(R.id.tv_info_qualification);
        tv_field = view.findViewById(R.id.tv_info_field);
        imageView = view.findViewById(R.id.img_info_profile);
        btn_modify = view.findViewById(R.id.btn_info_modify);
        btn_logout = view.findViewById(R.id.btn_info_logout);

        mFirebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser(); // ?????? ????????? ??? ??????
        db = FirebaseFirestore.getInstance(); // ?????????????????? ??????
        docRef = db.collection("UserInfo").document(user.getUid()); // ?????????????????? UserInfo ????????? ??????
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // ?????????????????????????????? ??????
        databaseReference = database.getReference("UserInfo"); //?????????????????????????????? UserInfo ????????? ??????

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_FIND_CODE);
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgseting) { // ????????? ?????? ????????? ?????? ????????? ?????? ????????? ??????
                    StorageReference storageRef = storage.getReference();
                    StorageReference riverRef = storageRef.child(imgname);
                    UploadTask uploadTask = riverRef.putFile(file);
                    if (bitmap != null) {
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Call_Toast("?????? ?????? ??????.");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Call_Toast("?????? ?????? ??????.");
                            }
                        });
                    }
                }else{Call_Toast("???????????? ????????? ????????? ????????? ????????????.");}
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        id = user.getEmail();
        // ????????? ????????? ????????? ?????????
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    qual = (String)document.get("qual");
                    imgname = "User/"+qual+"/"+user.getUid().substring(0,5);
                    tv_name.setText((String)document.get("name"));
                    tv_id.setText(id);
                    tv_email.setText((String)document.get("email"));
                    tv_qualification.setText(qual);
                    tv_field.setText((String)document.get("field"));
                    getUserImage(imageView,imgname);
                } else {
                    Toast.makeText(getContext(), "??????",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FIND_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    file = data.getData();
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    imageView.setImageBitmap(bitmap);
                    imgseting = true;
                    Call_Toast("?????? ?????? ????????? ????????? ???????????? ????????? ?????????.");
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Call_Toast("?????? ?????? ??????");
            }
        }
    }
}