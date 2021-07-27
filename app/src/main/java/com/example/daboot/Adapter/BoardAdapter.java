package com.example.daboot.Adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.daboot.Board.BoardItem;
import com.example.daboot.R;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    ArrayList<BoardItem> items = new ArrayList<BoardItem>();
    Context context; //선택한 activty에 대한 context

    //클릭이벤트처리 정의
    OnItemClickListener listener;
    public  static interface  OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }


    public BoardAdapter(ArrayList<BoardItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override //null이 아니라면 어댑터에서 관리하는 아이템의 개수를 반환
    public int getItemCount() { return (items != null? items.size() : 0); }


    @NonNull
    @Override //뷰홀더가 만들어지는 시점에 호출되는 메소드
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.board_item,  viewGroup, false);//viewGroup는 각각의 아이템을 위해서 정의한 xml레이아웃의 최상위 레이아우싱다.

        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.tv_category.setText(items.get(position).getCategory());
        viewHolder.tv_title.setText(items.get(position).getTitle());
        viewHolder.tv_comment.setText(items.get(position).getComment());
        viewHolder.tv_writetime.setText(items.get(position).getTime());
        //클릭리스너
        viewHolder.setOnItemClickListener(listener);

    }

    //아이템 추가
    public  void addItem(BoardItem item){
        items.add(item);
    }

    //한꺼번에 추가
    public void addItems(ArrayList<BoardItem> items){
        this.items = items;
    }


    public BoardItem getItem(int position){
        return  items.get(position);
    }

    //클릭리스너관련
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //뷰홀더
    //뷰를 담아두는 역할 / 뷰에 표시될 데이터를 설정하는 역할
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category,tv_title, tv_comment,tv_writetime;



        OnItemClickListener listenr; //클릭이벤트처리관련 변수

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tv_category = itemView.findViewById(R.id.tv_category);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_writetime = itemView.findViewById(R.id.tv_write_time);


            //아이템 클릭이벤트
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listenr != null ){
                        listenr.onItemClick(ViewHolder.this, itemView, position);
                    }
                }
            });
        }


        public void setOnItemClickListener(OnItemClickListener listenr){
            this.listenr = listenr;
        }


    }
}