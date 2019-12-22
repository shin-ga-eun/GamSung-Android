package com.example.gamsung.Card;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gamsung.Main.MyProfile.MyProfileActivity;
import com.example.gamsung.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {
    private ArrayList<CardRecyclerItem> cardlist = null ;
    private OnItemClickListener listener = null;
    private String imageUrl;
    boolean heart = false;

    private Context context;

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
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            imgCommentCard = itemView.findViewById(R.id.imgCommentCard) ;
            textCommetCard = itemView.findViewById(R.id.textCommetCard) ;
            textCommentTime = itemView.findViewById(R.id.textCommentTime) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        CardRecyclerItem item = cardlist.get(pos);
                        String identity = item.getIdentity();
                        Log.d("recyclerview clickListener identity >>>>>>>>>>", identity);
                        Intent intent = new Intent(context.getApplicationContext(), MyProfileActivity.class);
                        intent.putExtra("nickname", identity);
                        context.startActivity(intent);

                    }
                }
            });
        }
    }
    // 생성자에서 데이터 리스트 객체를 전달받음.
    CardRecyclerViewAdapter(ArrayList<CardRecyclerItem> list) {
        cardlist = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public CardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.card_comment_item, parent, false) ;
        CardRecyclerViewAdapter.ViewHolder vh = new CardRecyclerViewAdapter.ViewHolder(view) ;
        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(CardRecyclerViewAdapter.ViewHolder holder, int position) {
        CardRecyclerItem item = cardlist.get(position) ;

        Log.d("reply getImageUrl()>>>>>>>>>>>>>> ", item.getImageUrl());
        imageUrl = item.getImageUrl();
        Glide.with(context)
                .load(imageUrl) // image url
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.isloading) // any placeholder to load at start
                .error(R.drawable.emptyheart)  // any image in case of error
                .override(180, 175) // resizing
                .centerCrop()
                .into(holder.imgCommentCard);  // imageview object

        holder.imgCommentCard.setAlpha(0.5f);
        holder.textCommetCard.setText(item.getContent()) ;
        holder.textCommetCard.setTextSize(item.getFontsize());
        holder.textCommentTime.setText(item.getRegDate());



    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return cardlist.size();
    }

    //아이템 데이터 추가를 위한 함수
    public void addItem(Long rno, String identity, String content, String imageUrl, int fontsize, String regDate){
        CardRecyclerItem item = new CardRecyclerItem();

        item.setRno(rno);
        item.setContent(content);
        item.setImageUrl(imageUrl);
        item.setFontsize(fontsize);
        item.setIdentity(identity);
        item.setRegDate(regDate);

        cardlist.add(item);
    }

}
