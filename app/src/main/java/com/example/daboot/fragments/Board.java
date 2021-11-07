package com.example.daboot.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.daboot.Adapter.BoardAdapter;
import com.example.daboot.Board.BoardItem;
import com.example.daboot.Board.Contents;
import com.example.daboot.Board.Welfare;
import com.example.daboot.Login.Join;
import com.example.daboot.Login.JoinInfo;
import com.example.daboot.Login.Login;
import com.example.daboot.Login.MemberInfo;
import com.example.daboot.R;
import com.example.daboot.Board.Write;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Board extends Fragment {


    private Integer [] btn_id = {R.id.btn_board_All,R.id.btn_board_Free,R.id.btn_board_Question,R.id.btn_board_Market}; //카테고리 버튼 "정보" 제외 4개의 아이디 값을 넣은 배열
    private String [] keyword = {"전체","자유","질문","장터"}; // 4가지의 카테고리(전체, 자유, 질문, 장터)
    private Button [] category_btn = new Button[4]; //카테고리(전체,자유,장터 등..)를 담을 버튼 배열

    private LinearLayout bar_catagory, bar_filter, btns_select_option; // 상단바 카테고리(기본), 필터, 필터 옵션

    private RecyclerView view_board; //게시판
    private LinearLayoutManager layoutManager;
    private BoardAdapter boardAdapter;
    private ArrayList<BoardItem> arrayList;

    private Button btn_board_welfare;
    private Button btn_filter,btn_close, btn_select_option,btn_renew;
    private Button btn_write;

    /*파이어베이스 관련*/
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        /* bar 2개(필터,카테고리) */
        bar_catagory = view.findViewById(R.id.bar_board_Category);
        bar_filter = view.findViewById(R.id.bar_board_Filter);

        /* bar에 버튼들 선언부 */
        btns_select_option = view.findViewById(R.id.btns_board_select_option);
        btn_filter = view.findViewById(R.id.btn_board_Filter);
        btn_renew = view.findViewById(R.id.btn_board_Renew);
        btn_close = view.findViewById(R.id.btn_board_close_filter);
        btn_select_option = view.findViewById(R.id.btn_board_select_option);

        /* 게시판 관련 선언부 */
        view_board = view.findViewById(R.id.view_board);
        view_board.setHasFixedSize(true); // 리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        view_board.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        btn_write = view.findViewById(R.id.btn_board_write);
        btn_board_welfare = view.findViewById(R.id.btn_board_Welfare);


        user = FirebaseAuth.getInstance().getCurrentUser(); // 현재 로그인 한 유저
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // 리얼타임데이터베이스 연동
        databaseReference = database.getReference("Board"); //리얼타일데이터베이스 Board 테이블 연결
        db = FirebaseFirestore.getInstance(); //파이어스토어 연동
        docRef = db.collection("UserInfo").document(user.getUid()); // 파이어스토어 UserInfo 테이블 연결


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { //화면이 로딩되자마자 파이어스토어에 현재 로그인한 유저 정보를 조회해서 있으면 toast로 띄워주고 없으면 정보 입력화면으로 이동
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getContext(), document.get("name") + "님은 " + document.get("qual") + " 자격의 사용자입니다.",Toast.LENGTH_SHORT).show();
                        // document에 docRef로 연결된 파이어스토어 조회결과 넣어놓음 sql로 치면 ( select name, qual form UserInfo )랑 비슷 get(ㅁㅁㅁ) ㅁㅁㅁ에 조회할 속성 적으면 됨
                    } else {
                        Toast.makeText(getContext(), "정보입력 화면으로 이동합니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), JoinInfo.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getContext(), "실패",Toast.LENGTH_SHORT).show();
                }
            }
        });


        db.collection("Board")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String time = (String) document.get("time");
                                Log.d("가져온 값 !", document.getId() + " => " + document.getData());
                                BoardItem boardItem = new BoardItem(document.getId(),document.get("category")+"",document.get("title")+"",time.substring(11,16));
                                arrayList.add(boardItem);
                            }
                            boardAdapter.notifyDataSetChanged(); // 리스트저장, 새로고침
                        } else {

                        }
                    }
                });


        boardAdapter = new BoardAdapter(arrayList,getContext());
        view_board.setAdapter(boardAdapter);


        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position) {
                BoardItem item = boardAdapter.getItem(position);

                Intent intent = new Intent(getContext(), Contents.class);
                intent.putExtra("uid",item.getUid());
                startActivity(intent);
            }
        });

        /* 반복문을 이용한 4개의 카테고리 버튼에 대한 onClickListener 선언 부분이며 우리가 아는 setOnClickListener를 반복문으로 짧게 써놓은것   */
        for(int i=0; i<4; i++){
            category_btn[i] = view.findViewById((btn_id[i]));
            final int index = i;

            category_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category_btn[index].setSelected(true);

                    //todo : 카테고리에 따른 게시글 데이터를 호출하는 기능이 들어갈 자리 1.전체 2.자유 3.질문 4. 장터

                    Toast.makeText(getContext(),keyword[index],Toast.LENGTH_SHORT).show();
                }
            });
        }
        //새로고침 버튼 클릭시
        btn_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(getContext(), document.get("name") + " / " + document.get("qual") + " 자격의 사용자입니다.",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "데이터가 없습니다.",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        // 카테고리 - 정보 탬 클릭시
        btn_board_welfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Welfare.class);
                startActivity(intent);
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_catagory.setVisibility(View.GONE);
                bar_filter.setVisibility(View.VISIBLE);
            }
        }); // 상단바 전환(카테고리 -> 필터)

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_filter.setVisibility(View.GONE);
                bar_catagory.setVisibility(View.VISIBLE);
            }
        }); // 상단바 전환(필터 -> 카테고리)

        btn_select_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btns_select_option.getVisibility() == View.VISIBLE){
                    btns_select_option.setVisibility(View.GONE);
                    return;
                }
                btns_select_option.setVisibility(View.VISIBLE);

            }
        }); // (필터링 상태일때) 필터 옵션(제목 / 내용) on/off


        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Write.class);
                startActivity(intent);
            }
        });

        return view;

    }

}

