package com.example.daboot.Board;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.daboot.Adapter.ComentAdapter;
import com.example.daboot.Login.JoinInfo;
import com.example.daboot.MainActivity;
import com.example.daboot.Message.ChatActivity;
import com.example.daboot.Message.ChatData;
import com.example.daboot.Message.ChatRoomData;
import com.example.daboot.Message.RoomPerUserData;
import com.example.daboot.R;
import com.example.daboot.fragments.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.lang.String;

public class Contents extends AppCompatActivity {

    private int coment_number=1;
    private String uid,imgPath;
    private String writer,coment,time,writer_email;
    private boolean wnat_img = false;
    private ComentAdapter comentAdapter;
    private ArrayList<ComentItem> arrayList;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Button btn_back, btn_msg, btn_edit,btn_del;

    private ImageButton btn_write_coment;
    private TextView tv_title, tv_contetnts, tv_time, tv_img_guide;
    private EditText edt_write_coment;
    private ImageView img_contents;
    private LinearLayout coments_bar,img_bar;
    private LinearLayoutManager layoutManager;
    private RecyclerView view_coments; //?????????

    protected FirebaseDatabase database;
    private DatabaseReference MsgRef;
    protected DatabaseReference roomUserRef;
    private DatabaseReference userRoomRef;
    private DatabaseReference findWriterUid;
    private FirebaseFirestore firestore;
    private DocumentReference docRef;
    protected DocumentReference ChatDocRef;
    protected String writerEmail;
    protected String userEmail;
    protected String roomIdToken;
    protected String roomId;
    protected Boolean userEmailToCompare;
    protected Boolean writerEmailToCompare;

    public void msg(String str){

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(str+"???????????????????")
                .setPositiveButton(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                docRef.delete();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        })
        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();

    }

    public void Call_Toast(String message){ Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();}

    private String getCurrentDate(){ // ?????? ????????? String????????? ??????????????? ??????
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getTime = simpleDate.format(mDate);

        return getTime;
    }

    private void getComentData(){
        firestore.collection("Board").document(uid).collection("Coment")
                .orderBy("time", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String time = (String) document.get("time");
                                String text_anonymous="??????";
                                ComentItem comentItem = new ComentItem(document.getId(),document.get("writer")+"",coment_number++,document.get("coment")+"",time.substring(11,16));
                                arrayList.add(comentItem);
                            }
                            comentAdapter.notifyDataSetChanged(); // ???????????????, ????????????
                        } else {
                            Call_Toast("?????? ???????????? ??????.");
                        }
                    }
                });
        comentAdapter = new ComentAdapter(arrayList,getApplicationContext());
        view_coments.setAdapter(comentAdapter);
    }
    private void getContentImage(ImageView imageView, String path){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef  = storage.getReference();
        storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Call_Toast("??????");
                //todo ????????? ???????????? ????????? textview??? drawabletop?????? ????????????
                Glide.with(getApplicationContext()).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Call_Toast("??????");
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_contents);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//????????? ??????

        arrayList = new ArrayList<ComentItem>();

        uid = getIntent().getStringExtra("uid");
        firestore = FirebaseFirestore.getInstance(); //?????????????????? ??????
        docRef = firestore.collection("Board").document(uid); // ?????????????????? ????????? ??????

        btn_back = findViewById(R.id.btn_board_contents_back);

        btn_msg = findViewById(R.id.btn_go_to_chat);
        btn_edit = findViewById(R.id.btn_board_contents_edit);
        btn_del = findViewById(R.id.btn_board_contents_delete);


        tv_title = findViewById(R.id.tv_board_contents_title);
        tv_time = findViewById(R.id.tv_board_contents_writeTime);
        tv_contetnts = findViewById(R.id.tv_board_contents);

        tv_img_guide = findViewById(R.id.tv_board_contents_guid_img);
        img_bar = findViewById(R.id.board_contents_img_BAR);
        img_contents = findViewById(R.id.img_board_contents_img);

        edt_write_coment = findViewById(R.id.edt_board_contents_write_coment);
        btn_write_coment = findViewById(R.id.btn_board_contents_write_coment);

        coments_bar = findViewById(R.id.board_contents_coments_BAR);
        view_coments = findViewById(R.id.tv_board_contents_coments);
        view_coments.setHasFixedSize(true); // ?????????????????? ????????????
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        view_coments.setLayoutManager(layoutManager);

        //????????? ????????? ?????? test ------------------------------------------------------------------------------------------------------------------------
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // ?????????????????? ????????? ????????????

        MsgRef = database.getReference("Message"); //????????? ?????? ????????? ??????
        roomUserRef = database.getReference("RoomUser"); //???????????? ???????????? ????????? ?????????ID??? ?????? ??? ?????? ????????? ??????
        userRoomRef = database.getReference("UserRoom"); //????????? ??? ?????????????????? ???????????? ??? ??? ?????? ????????? ??????
        findWriterUid = database.getReference("UserAccount");
        user = FirebaseAuth.getInstance().getCurrentUser(); // ?????? ????????? ??? ??????
        ChatDocRef = firestore.collection("UserInfo").document(user.getUid()); // ?????????????????? UserInfo ????????? ??????

        //???????????? ???????????? UID ????????????
        userEmail = user.getEmail();


        DocumentReference getWriterEmail = firestore.collection("Board").document(uid);
        getWriterEmail.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                writerEmail = (String) document.get("writer");
            }
        }); //???????????????????????? ?????? ?????? ????????? UID??? ?????? ???, RoomUser??? ?????? --> firebase??? ?????? firestore??? ??????

        //-------------------------------------------------------------------------------------------------------------------------------------------

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String time = (String) document.get("time");
                        writer_email = document.get("writer")+"";
                        tv_title.setText(document.get("title")+"");
                        tv_time.setText(time.substring(11,16));
                        tv_contetnts.setText(document.get("contents")+"");
                        imgPath = document.get("imgpath")+"";
                        if(imgPath.equals("none")==true){
                            img_bar.setVisibility(View.GONE);
                        }
                        if(writer_email.equals(user.getEmail()+"")){
                            btn_edit.setVisibility(View.VISIBLE);
                            btn_del.setVisibility(View.VISIBLE);
                        }else{btn_msg.setVisibility(View.VISIBLE);}
                    }else{
                        Call_Toast("????????? ????????? ???????????????.");
                        finish();
                    }
                } else { Call_Toast("?????? ??????"); }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });//?????? ??????
//---------------------------------------------------------------------------------------------------------------------------------------------------
        btn_msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*roomUserRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {
                        ChatRoomData bringValue = dataSnapshot.getValue(ChatRoomData.class);
                        userEmailToCompare = Boolean.valueOf(bringValue.getUserEmail());
                        writerEmailToCompare = Boolean.valueOf(bringValue.getWriterEmail());
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {}
                    @Override
                    public void onChildRemoved(@NonNull @NotNull DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(@NonNull @NotNull DataSnapshot dataSnapshot, @Nullable @org.jetbrains.annotations.Nullable String s) {}
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {}
                });
                if(userEmailToCompare && writerEmailToCompare){
                    Log.e("Can I ride this Logic?","");
                    Intent itt = new Intent(Contents.this, ChatActivity.class);
                    itt.putExtra("roomId",roomUserRef.child("roomIdToken").toString());
                    startActivity(itt);//????????? ????????????
                }else{

                }*/

                roomId = UUID.randomUUID().toString().replace("-","");
                if(userEmail != writerEmail){
                    ChatRoomData roomUser = new ChatRoomData(roomIdToken, writerEmail, userEmail);
                    roomUser.setRoomIdToken(roomId);
                    roomUser.setWriterEmail(writerEmail);
                    roomUser.setUserEmail(userEmail);
                    roomUserRef.child(roomId).setValue(roomUser);
                }

                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            }
        });
        //??????????????? ???????????? ?????? --> ??? ?????? ?????????????????? ?????? 211124 lsj
        //-------------------------------------------------------------------------------------------------------------------------------------------

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("??????");
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("??????");
            }
        });

        getComentData();

        comentAdapter.setOnItemClickListener(new ComentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ComentAdapter.ViewHolder holder, View view, int position) {
                ComentItem item = comentAdapter.getItem(position);
                //todo:?????? ????????? = ???????????? ????????? ?????? ??? ?????? ???????????? ?????????.
            }
        });

        img_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????? ????????? ????????? none??? ?????????
                if(imgPath.equals("none")==false) {

                    // ????????? ????????? ?????????
                    if (wnat_img == false) {
                        getContentImage(img_contents,imgPath);
                        view_coments.setVisibility(View.GONE);
                        coments_bar.setVisibility(View.GONE);
                        tv_img_guide.setVisibility(View.GONE);
                        img_contents.setLayoutParams(new LinearLayout.LayoutParams(
                                500,500));
                        wnat_img = true;
                    } else {
                        img_contents.setImageResource(R.drawable.ic_image);
                        view_coments.setVisibility(View.VISIBLE);
                        coments_bar.setVisibility(View.VISIBLE);
                        tv_img_guide.setVisibility(View.VISIBLE);
                        img_contents.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        wnat_img = false;
                    }
                }
            }
        });



        btn_write_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writer = user.getEmail();
                coment = edt_write_coment.getText().toString();
                time = getCurrentDate();

                if(writer.length()>0 && coment.length()>0 && time.length()>0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    ComentInfo comentInfo = new ComentInfo(writer,coment,time);
                    db.collection("Board").document(uid).collection("Coment").add(comentInfo)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    edt_write_coment.setText("");
                                    arrayList.clear();
                                    coment_number=1;
                                    Call_Toast("????????? ?????????????????????.");
                                    db.collection("Board").document(uid).update("coments", FieldValue.increment(1));
                                    //?????? ??? ??????
                                    getComentData();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { Call_Toast("?????? ??????."); }
                    });
                } else Call_Toast("?????? ?????? ??????????????????.");
            }
        });

    }
}