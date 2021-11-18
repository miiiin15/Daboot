package com.example.daboot.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.daboot.Login.Login;
import com.example.daboot.R;
import com.google.firebase.auth.FirebaseAuth;


public class Setting extends Fragment {

    private Switch swt1, swt2, swt3, swt4, swt5, swt6, swt7, swt8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        swt1 = view.findViewById(R.id.btn_alram_coments); // 댓글
        swt2 = view.findViewById(R.id.btn_alram_message); // 쪽지
        swt3 = view.findViewById(R.id.btn_permission_call); // 전화
        swt4 = view.findViewById(R.id.btn_permission_photo); // 사진
        swt5 = view.findViewById(R.id.btn_permission_location); // 위치
        swt6 = view.findViewById(R.id.btn_sound_all); // 소리-전체
        swt7 = view.findViewById(R.id.btn_sound_coments); // 소리-댓글
        swt8 = view.findViewById(R.id.btn_sound_message); // 소리-쪽지

        swt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "댓글알림 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "댓글알림 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "쪽지알림 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "쪽지알림 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "전화권한 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "전화권한 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "사진권한 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "사진권한 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "위치권한 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "위치권 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "전체소리 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "전체소리 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "댓글소리 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "댓글소리 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "쪽지소리 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "쪽지소리 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}