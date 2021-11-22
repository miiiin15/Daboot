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
import com.example.daboot.R;
import com.example.daboot.fragments.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private RecyclerView view_coments; //댓글창

    private FirebaseFirestore firestore;
    private DocumentReference docRef;

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
        view_coments.setHasFixedSize(true); // 리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        view_coments.setLayoutManager(layoutManager);



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