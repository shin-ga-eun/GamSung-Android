package com.example.gamsung.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamsung.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {
    private ArrayList<CardRecyclerItem> cardlist = null ;
    private OnItemClickListener listener = null;
    boolean heart = false;

    //어댑터 내 커스텀 리스너 인터페이스
    public interface  OnItemClickListener {
        void onItemClick(View v, int position);
    }
    //리스너 객체 참조를 어댑터에 전달하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCommentCard ;
        TextView textCommetCard, textCommentTime;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            imgCommentCard = itemView.findViewById(R.id.imgCommentCard) ;
            textCommetCard = itemView.findViewById(R.id.textCommetCard) ;
            textCommentTime = itemView.findViewById(R.id.textCommentTime) ;

        }
    }
    // 생성자에서 데이터 리스트 객체를 전달받음.
    CardRecyclerViewAdapter(ArrayList<CardRecyclerItem> list) {
        cardlist = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public CardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.card_comment_item, parent, false) ;
        CardRecyclerViewAdapter.ViewHolder vh = new CardRecyclerViewAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(CardRecyclerViewAdapter.ViewHolder holder, int position) {
        CardRecyclerItem item = cardlist.get(position) ;
        holder.imgCommentCard.setImageResource(item.getImg());
        holder.imgCommentCard.setAlpha(0.5f);
        holder.textCommetCard.setText(item.getContent()) ;
        holder.textCommetCard.setTextSize(item.getFontSize());
        holder.textCommentTime.setText(item.getTime());

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return cardlist.size() ;
    }


}
