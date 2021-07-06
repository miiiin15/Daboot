package com.example.daboot.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.daboot.R;

import java.util.List;

public class Board extends Fragment {


    Integer [] btn_id = {R.id.btn_All,R.id.btn_Free,R.id.btn_Question,R.id.btn_Market,R.id.btn_Welfare}; //카테고리 버튼 5개의 아이디 값을 넣은 배열
    String [] keyword = {"All","Free","Question","Market","Welfare"}; // 5가지의 카테고리(전체, 자유, 질문, 장터, 정보)
    Button [] category_btn = new Button[5]; //카테고리(전체,자유,장터 등..)를 담을 버튼 배열

    LinearLayout bar_catagory, bar_filter;

    Button btn_filter,btn_close;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        bar_filter = view.findViewById(R.id.bar_Filter);
        bar_catagory = view.findViewById(R.id.bar_Category);

        btn_filter = view.findViewById(R.id.btn_Filter);
        btn_close = view.findViewById(R.id.btn_close);

        /* 반복문을 이용한 5개의 카테고리 버튼에 대한 onClickListener 선언  */
        for(int i=0; i<5; i++){
            category_btn[i] = view.findViewById((btn_id[i]));
            final int index = i;

            category_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category_btn[index].setSelected(true);

                    //카테고리에 따른 게시판 정렬을 해줄 함수가 들어갈 자리

                    Toast.makeText(getContext(),keyword[index],Toast.LENGTH_SHORT).show();
                }
            });
        }

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_catagory.setVisibility(View.GONE);
                bar_filter.setVisibility(View.VISIBLE);
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_filter.setVisibility(View.GONE);
                bar_catagory.setVisibility(View.VISIBLE);
            }
        });

        return view;

    }
}