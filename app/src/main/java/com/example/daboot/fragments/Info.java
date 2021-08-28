package com.example.daboot.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.daboot.Login.Login;
import com.example.daboot.R;
import com.google.firebase.auth.FirebaseAuth;


public class Info extends Fragment {

    private FirebaseAuth mFirebaseAuth;


    private ImageButton btn_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();

        btn_logout = view.findViewById(R.id.btn_info_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}