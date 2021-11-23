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
<<<<<<< HEAD
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.daboot.Adapter.ChatAdapter;
=======
>>>>>>> b91c27d7767899b6543253e2b83d5ada4d86fc8e
import com.example.daboot.Adapter.ComentAdapter;
import com.example.daboot.Login.JoinInfo;
import com.example.daboot.MainActivity;
import com.example.daboot.Message.ChatActivity;
import com.example.daboot.Message.ChatData;
import com.example.daboot.Message.ChatRoomData;
import com.example.daboot.R;
import com.example.daboot.fragments.Board;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

<<<<<<< HEAD
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
=======
>>>>>>> b91c27d7767899b6543253e2b83d5ada4d86fc8e
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Contents extends AppCompatActivity {

    private int coment_number=1;
    private String uid,imgPath;
    private String writer,coment,time,writer_email;
    private boolean wnat_img = false;
    private ComentAdapter comentAdapter;
    private ArrayList<ComentItem> arrayList;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

<<<<<<< HEAD
    private Button btn_back, btn_go_to_chat;
=======
    private Button btn_msg, btn_edit,btn_del;
>>>>>>> b91c27d7767899b6543253e2b83d5ada4d86fc8e
    private ImageButton btn_write_coment;
    private TextView tv_title, tv_contetnts, tv_time, tv_img_guide;
    private EditText edt_write_coment;
    private ImageView img_contents;
    private LinearLayout coments_bar,img_bar;
    private LinearLayoutManager layoutManager;
    private RecyclerView view_coments; //댓글창

    private FirebaseDatabase database;
    private DatabaseReference MsgRef;
    private DatabaseReference roomUserRef;
    private DatabaseReference userRoomRef;
    private FirebaseFirestore firestore;
    private DocumentReference docRef;
    private DocumentReference ChatDocRef;
    protected String email;
    protected String writerIdToken;
    protected String userIdToken;

    public void msg(String str){

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(str+"하시겠습니까?")
                .setPositiveButton(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                docRef.delete();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        })
        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();

    }

    public void Call_Toast(String message){ Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();}

    private String getCurrentDate(){ // 현재 시간을 String형으로 반환해주는 함수
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
                                String text_anonymous="익명";
                                ComentItem comentItem = new ComentItem(document.getId(),document.get("writer")+"",coment_number++,document.get("coment")+"",time.substring(11,16));
                                arrayList.add(comentItem);
                            }
                            comentAdapter.notifyDataSetChanged(); // 리스트저장, 새로고침
                        } else {
                            Call_Toast("댓글 불러오기 실패.");
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
                Call_Toast("성공");
                //todo 비트맵 이미지를 받아서 textview에 drawabletop속성 추가하기
                Glide.with(getApplicationContext()).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Call_Toast("실패");
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_contents);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        arrayList = new ArrayList<ComentItem>();

        uid = getIntent().getStringExtra("uid");
        firestore = FirebaseFirestore.getInstance(); //파이어스토어 연동
        docRef = firestore.collection("Board").document(uid); // 파이어스토어 테이블 연결

        btn_back = findViewById(R.id.btn_board_contents_back);
<<<<<<< HEAD
        btn_go_to_chat = findViewById(R.id.btn_go_to_chat);
=======
        btn_msg = findViewById(R.id.btn_go_to_chat);
        btn_edit = findViewById(R.id.btn_board_contents_edit);
        btn_del = findViewById(R.id.btn_board_contents_delete);
>>>>>>> b91c27d7767899b6543253e2b83d5ada4d86fc8e

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
        view_coments.setHasFixedSize(true); // 리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        view_coments.setLayoutManager(layoutManager);

<<<<<<< HEAD
        //채팅방 구현을 위한 test
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // 파이어베이스 기능을 연동해라
        MsgRef = database.getReference("Message"); //채팅방 자체 테이블 연동
        roomUserRef = database.getReference("RoomUser"); //글쓴이와 사용자가 포함된 채팅방ID를 담을 수 있는 테이블 연동
        userRoomRef = database.getReference("UserRoom"); //사용자 별 참가되어있는 채팅방을 알 수 있는 테이블 연동
        user = FirebaseAuth.getInstance().getCurrentUser(); // 현재 로그인 한 유저
        ChatDocRef = firestore.collection("UserInfo").document(user.getUid()); // 파이어스토어 UserInfo 테이블 연결

        //사용자와 글쓴이의 UID 가져오기
        userIdToken = user.getUid();

        DocumentReference getWriterEmail = firestore.collection("Board").document(uid);
        getWriterEmail.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                email = (String) document.get("writer");
            }
        }); //파이어스토어에서 해당 글의 글쓴이 이메일을 조회
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        DocumentReference getWriterUid = firestore.collection("UserInfo").document(email);
        getWriterUid.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                writerIdToken = (String) document.getId();
                Log.e("Message", writerIdToken);
            }
        });

        /*Query getWriterUid = firestore.collection("UserInfo").whereEqualTo("email", email);
        getWriterUid.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        writerIdToken = document.getId();
                        Log.e("Message", writerIdToken);
                    }
                } else {
                }
            }
        });*/

        ChatRoomData roomUser = new ChatRoomData(writerIdToken, userIdToken);
        roomUser.setWrterIdToken(writerIdToken);
        roomUser.setUserIdToken(userIdToken);
=======

>>>>>>> b91c27d7767899b6543253e2b83d5ada4d86fc8e

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
                        Call_Toast("잘못된 게시글 형식입니다.");
                        finish();
                    }
                } else { Call_Toast("호출 실패"); }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });//취소 버튼

<<<<<<< HEAD
        btn_go_to_chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String roomId = ""; //채팅방 고유 UID 생성 변수

                if(roomUserRef.child(roomId).getKey().equals(userIdToken) && roomUserRef.child(roomId).getKey().equals(writerIdToken)){
                    userRoomRef.child(userIdToken).getRef();
                } else {
                    roomId = UUID.randomUUID().toString().replaceAll("-", "");//채딩방 고유 uid 값을 random으로 생성
                    MsgRef.push().setValue(roomId);
                    roomUserRef.push().setValue(roomId);

                    roomUserRef.child(roomId).setValue(writerIdToken);
                    roomUserRef.child(roomId).setValue(userIdToken);

                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                }
            }
        });//채팅방으로 이동하는 로직 --> 20211119 lsj
=======
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("수정");
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg("삭제");
            }
        });
>>>>>>> b91c27d7767899b6543253e2b83d5ada4d86fc8e

        getComentData();

        comentAdapter.setOnItemClickListener(new ComentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ComentAdapter.ViewHolder holder, View view, int position) {
                ComentItem item = comentAdapter.getItem(position);
                //todo:댓글 작성자 = 로그인한 유저가 동일 할 경우 삭제기능 활성화.
            }
        });

        img_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //게시글 이미지 링크가 none이 아니면
                if(imgPath.equals("none")==false) {

                    // 이미지 노출을 원하면
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
                                    Call_Toast("댓글을 등록하였습니다.");
                                    db.collection("Board").document(uid).update("coments", FieldValue.increment(1));
                                    //댓글 수 증가
                                    getComentData();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { Call_Toast("등록 실패."); }
                    });
                } else Call_Toast("입력 값을 확인해주세요.");
            }
        });

    }
}