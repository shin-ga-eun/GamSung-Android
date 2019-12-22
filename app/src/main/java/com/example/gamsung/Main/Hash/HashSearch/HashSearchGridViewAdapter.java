package com.example.gamsung.Main.Hash.HashSearch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gamsung.R;

import java.util.ArrayList;

public class HashSearchGridViewAdapter extends BaseAdapter {

    private Context context;

    //Adapter에 추가된 데이터를 저장하기위한  ArrayList
    private ArrayList<HashSearchGridViewItem> hashtaglist = new ArrayList<>();

    public HashSearchGridViewAdapter(Context context){
        this.context = context;

    }

    @Override
    public int getCount() {
        return hashtaglist.size();
    }

    @Override
    public Object getItem(int position) {
        return hashtaglist.get(position).getCno();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final Context context = parent.getContext();
        String imageUrl;

        if(row == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            row = layoutInflater.inflate(R.layout.myprofile_item, parent, false);
        }
        ImageView imgCard = (ImageView)row.findViewById(R.id.imgCard);
        TextView textContent = (TextView)row.findViewById(R.id.textContent);

        HashSearchGridViewItem hashSearchGridViewItem = hashtaglist.get(position);

        //서버에서 imageUrl 받아와서 Glide를 통해 이미지뷰에 이미지 출력
        imageUrl = hashSearchGridViewItem.getImageUrl();
        Glide.with(context)
                .load(imageUrl) // image url
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.isloading) // any placeholder to load at start
                .error(R.drawable.emptyheart)  // any image in case of error
                .override(180, 175) // resizing
                .centerCrop()
                .into(imgCard);  // imageview object

        imgCard.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgCard.setAlpha(0.5f);
        imgCard.setPadding(5,5,5,5);
        textContent.setText(hashSearchGridViewItem.getContent());
        int fontSize = (int)((hashSearchGridViewItem.getFontsize()) * 0.6);
        textContent.setTextSize(fontSize);

        return row;
    }

    //아이템 데이터 추가를 위한 함수
    public void addItem(Long cno, String content, String imageUrl, int fontsize){
        HashSearchGridViewItem item = new HashSearchGridViewItem();

        item.setCno(cno);
        item.setContent(content);
        item.setImageUrl(imageUrl);
        item.setFontsize(fontsize);

        hashtaglist.add(item);
    }

    public void deleteItem() {

        hashtaglist.clear();
    }


}

