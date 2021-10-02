package com.example.daboot.Board;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.Message.ChatActivity;
import com.example.daboot.R;

public class Contents extends AppCompatActivity {

    private Button btn_back;
    private EditText edt_coments;
    private LinearLayout coments_bar;
    private Button btn_go_to_chat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_contents);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        Intent chatIntent = new Intent(this, ChatActivity.class);

        btn_back = findViewById(R.id.btn_board_contents_back);

        edt_coments = findViewById(R.id.edt_board_contents_coment);

        coments_bar = findViewById(R.id.board_contents_coments_BAR);

        btn_go_to_chat = findViewById(R.id.btn_go_to_chat);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_go_to_chat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(chatIntent);
            }
        });

    }

    //채팅 기능 테스트를 위해 더보기 버튼 잠시 주
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.side_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);

        switch(item.getItemId())
        {
            case R.id.send_msg:
                toast.setText("Select Menu1");
                break;
            case R.id.send_rpt:
                toast.setText("Select Menu2");
                break;
        }

        toast.show();

        return super.onOptionsItemSelected(item);
    }*/
}
