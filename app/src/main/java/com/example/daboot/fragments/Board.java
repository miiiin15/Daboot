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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.daboot.Adapter.BoardAdapter;
import com.example.daboot.Board.BoardItem;
import com.example.daboot.Board.Contents;
import com.example.daboot.Board.Welfare;
import com.example.daboot.Login.JoinInfo;
import com.example.daboot.R;
import com.example.daboot.Board.Write;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Board extends Fragment {

    private static int category_index=0; //마지막 누른 카테고리 정보를 담기위한 static변수
    private String category="전체";
    private String input_option = "contents";
    private Integer [] btn_id = {R.id.btn_board_All,R.id.btn_board_Free,R.id.btn_board_Question,R.id.btn_board_Market}; //카테고리 버튼 "정보" 제외 4개의 아이디 값을 넣은 배열
    private String [] keyword = {"전체","자유","질문","장터"}; // 4가지의 카테고리(전체, 자유, 질문, 장터)
    private Button [] category_btn = new Button[4]; //카테고리(전체,자유,장터 등..)를 담을 버튼 배열

    private RelativeLayout board_guide; //게시판 안내 텍스트뷰 모음
    private LinearLayout bar_catagory, bar_filter, btns_select_option; // 상단바 카테고리(기본), 필터, 필터 옵션

    private RecyclerView view_board; //게시판
    private LinearLayoutManager layoutManager;
    private BoardAdapter boardAdapter;
    private ArrayList<BoardItem> arrayList;

    private Button btn_board_welfare;
    private Button btn_filter, btn_close, btn_renew;
    private Button btn_search, btn_select_option, btn_option_title,btn_option_contents;
    private Button btn_write;

    private EditText edt_input;

    /*파이어베이스 관련*/
    private FirebaseDatabase database;
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference databaseReference;
    private Query query_all, query_another, query_search;

    private void Call_Toast(String message){ Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();}

    private String getContentsDateFormat(String writeTime){
        String date = "";
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = simpleDate.format(mDate);

        if(writeTime.substring(0,10).equals(getTime)) {
            date = writeTime.substring(11, 16);
        }
        else { date = writeTime.substring(5, 7) + "/" + writeTime.substring(8, 10); }

        return date;
    }

    //쿼리에 맞는 파이어베이스 정보 가져오기
    private void getBoardData(Query query){
        arrayList.clear();
        query.orderBy("time", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String time = (String) document.get("time");
                                int coments_counter = Integer.parseInt(String.valueOf(document.get("coments")));
                                BoardItem boardItem = new BoardItem(document.getId(),document.get("category")+"",document.get("title")+"",coments_counter,getContentsDateFormat(time));
                                arrayList.add(boardItem);
                            }
                            boardAdapter.notifyDataSetChanged(); // 리스트저장, 새로고침
                        }else { Call_Toast("실패"); }
                    }
                });
        boardAdapter = new BoardAdapter(arrayList,getContext());
        view_board.setAdapter(boardAdapter);

        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position) {
                BoardItem item = boardAdapter.getItem(position);
                startActivity(new Intent(getContext(), Contents.class).putExtra("uid",item.getUid()));
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        /* bar 2개(필터,카테고리) */
        bar_catagory = view.findViewById(R.id.bar_board_Category);
        bar_filter = view.findViewById(R.id.bar_board_Filter);

        /* bar에 버튼들 선언부 */
        btn_filter = view.findViewById(R.id.btn_board_Filter);
        btn_renew = view.findViewById(R.id.btn_board_Renew);
        btn_close = view.findViewById(R.id.btn_board_close_filter);

        edt_input = view.findViewById(R.id.edt_board_input);
        btn_search = view.findViewById(R.id.btn_board_search);
        btns_select_option = view.findViewById(R.id.btns_board_select_option);
        btn_select_option = view.findViewById(R.id.btn_board_select_option);
        btn_option_title = view.findViewById(R.id.btn_board_option_title);
        btn_option_contents = view.findViewById(R.id.btn_board_option_contents);

        /* 게시판 관련 선언부 */
        board_guide = view.findViewById(R.id.tvs_board_guide);
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
        firestore = FirebaseFirestore.getInstance(); //파이어스토어 연동
        docRef = firestore.collection("UserInfo").document(user.getUid()); // 파이어스토어 UserInfo 테이블 연결
        query_all = firestore.collection("Board");

        //화면이 로딩되자마자 파이어스토어에 현재 로그인한 유저 정보를 조회해서 있으면 toast로 띄워주고 없으면 정보 입력화면으로 이동
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                    }else{
                        Call_Toast("정보입력 화면으로 이동합니다.");
                        startActivity(new Intent(getContext(), JoinInfo.class));
                        getActivity().finish();
                    }
                } else { Call_Toast("실패"); }
            }
        });
        getBoardData(query_all);

        /*boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BoardAdapter.ViewHolder holder, View view, int position) {
                BoardItem item = boardAdapter.getItem(position);
                startActivity(new Intent(getContext(), Contents.class).putExtra("uid",item.getUid()));
            }
        });*/

        /* 반복문을 이용한 4개의 카테고리 버튼에 대한 onClickListener 선언 부분이며 우리가 아는 setOnClickListener를 반복문으로 짧게 써놓은것   */
        for(int i=0; i<4; i++){
            category_btn[i] = view.findViewById((btn_id[i]));
            final int index = i;
            query_another = firestore.collection("Board").whereEqualTo("category",keyword[index]);
            Query query = index==0 ? query_all : query_another;
            category_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category_index = index;
                    category_btn[index].setSelected(true);
                    getBoardData(query);
                }
            });
        }
        //새로고침 버튼 클릭시
        btn_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query_another = firestore.collection("Board").whereEqualTo("category",keyword[category_index]);
                Query query = category_index==0 ? query_all : query_another;
                getBoardData(query);
            }
        });


        // 카테고리 - 정보 탬 클릭시
        btn_board_welfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(getContext(), Welfare.class)); }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_option = "title";
                bar_catagory.setVisibility(View.GONE);
                bar_filter.setVisibility(View.VISIBLE);
                board_guide.setVisibility(View.INVISIBLE);
                view_board.setVisibility(View.INVISIBLE);
            }
        }); // 상단바 전환(카테고리 -> 필터)

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_catagory.setVisibility(View.VISIBLE);
                bar_filter.setVisibility(View.GONE);
                board_guide.setVisibility(View.VISIBLE);
                view_board.setVisibility(View.VISIBLE);
                if(btns_select_option.getVisibility() == View.VISIBLE){
                    btns_select_option.setVisibility(View.GONE);
                }
            }
        }); // 상단바 전환(필터 -> 카테고리)

        btn_search.setOnClickListener(new View.OnClickListener() { //todo:필터 닫으면 visible다 바꾸고 게시판 갱신

            @Override
            public void onClick(View v) {
                String input_value = edt_input.getText()+"";
                query_search = firestore.collection("Board").whereEqualTo(input_option, input_value);
                getBoardData(query_search);

                bar_catagory.setVisibility(View.VISIBLE);
                bar_filter.setVisibility(View.GONE);
                board_guide.setVisibility(View.VISIBLE);
                view_board.setVisibility(View.VISIBLE);
                if(btns_select_option.getVisibility() == View.VISIBLE){
                    btns_select_option.setVisibility(View.GONE);
                }
                edt_input.setText("");

            }
        });

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

        btn_option_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_option = "title";
                btns_select_option.setVisibility(View.GONE);
            }
        });

        btn_option_contents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_option = "contents";
                btns_select_option.setVisibility(View.GONE);
            }
        });

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(getContext(), Write.class)); }
        });

        return view;

    }

}

