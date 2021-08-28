package com.example.daboot.Board;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daboot.Adapter.WelfareAdapter;
import com.example.daboot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Welfare extends AppCompatActivity {

    private Integer [] btn_id = {R.id.btn_welfare_Service,R.id.btn_welfare_Policy,R.id.btn_welfare_Health,R.id.btn_welfare_All};
    private String [] keyword = {"Service","Policy","Health","All"};
    private Button[] category_btn = new Button[4];

    private LinearLayout bar_catagory, bar_filter, btns_select_option;

    private RecyclerView view_board; //게시판
    private LinearLayoutManager layoutManager;
    private WelfareAdapter welfareAdapter;
    private ArrayList<WelfareItem> arrayList;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    private Button btn_filter,btn_close, btn_select_option,btn_welfare_back;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_welfare);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        bar_catagory = findViewById(R.id.bar_welfare_Category);
        bar_filter = findViewById(R.id.bar_welfare_Filter);

        /* bar에 버튼들 선언부 */
        btns_select_option = findViewById(R.id.btns_welfare_select_option);
        btn_filter = findViewById(R.id.btn_welfare_Filter);
        btn_close = findViewById(R.id.btn_welfare_close_filter);
        btn_select_option = findViewById(R.id.btn_welfare_select_option);
        btn_welfare_back = findViewById(R.id.btn_welfare_back);

        /* 게시판 관련 선언부 */
        view_board = findViewById(R.id.view_welfare);
        view_board.setHasFixedSize(true); // 리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        view_board.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();


        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // 파이어베이스 기능을 연동해라
        databaseReference = database.getReference("Welfare"); //DB테이블 연결

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot :  dataSnapshot.getChildren()){
                    WelfareItem welfareItem = snapshot.getValue(WelfareItem.class);
                    arrayList.add(welfareItem);
                }
                welfareAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("welfare", String.valueOf(databaseError.toException()));
            }
        });

        welfareAdapter = new WelfareAdapter(arrayList,getApplicationContext());
        view_board.setAdapter(welfareAdapter);

        welfareAdapter.setOnItemClickListener(new WelfareAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WelfareAdapter.ViewHolder holder, View view, int position) {
                WelfareItem item = welfareAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), " 제목 : " + item.getTitle(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), WelfareContents.class);
                startActivity(intent);
            }
        });



        /* 반복문을 이용한 4개의 카테고리 버튼에 대한 onClickListener 선언 부분이며 우리가 아는 setOnClickListener를 반복문으로 짧게 써놓은것   */
        for(int i=0; i<4; i++){
            category_btn[i] = findViewById((btn_id[i]));
            final int index = i;

            category_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category_btn[index].setSelected(true);

                    //todo : 카테고리에 따른 정보게시판 데이터를 호출하는 기능이 들어갈 자리

                    Toast.makeText(getApplicationContext(),keyword[index],Toast.LENGTH_SHORT).show();
                }
            });
        }

        btn_welfare_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }
}
