package com.example.gamsung.MainHome.HashSearch.HashList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

//서버로부터 이미지 받아와야함
public class HashTagGridViewAdapter extends BaseAdapter {

    private Context context;

    //Adapter에 추가된 데이터를 저장하기위한  ArrayList
    private ArrayList<HashTagGridViewItem> hashtaglist = new ArrayList<>();

    public HashTagGridViewAdapter(Context context){
        this.context = context;

    }

    @Override
    public int getCount() {
        return hashtaglist.size();
    }

    @Override
    public Object getItem(int position) {
        return hashtaglist.get(position);
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

        HashTagGridViewItem hashTagGridViewItem = hashtaglist.get(position);

        imageUrl = hashTagGridViewItem.getImageUrl();

        Glide.with(context)
                .load(imageUrl) // image url
                //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.isloading) // any placeholder to load at start
                .error(R.drawable.emptyheart)  // any image in case of error
                .override(180, 175) // resizing
                .centerCrop()
                .into(imgCard);  // imageview object


        //imgCard.setImageResource(R.drawable.emptyheart);
        imgCard.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgCard.setAlpha(0.5f);
        imgCard.setPadding(5,5,5,5);
        textContent.setText(hashTagGridViewItem.getContent());
        int fontSize = (int)((hashTagGridViewItem.getFontsize()) * 0.6);
        textContent.setTextSize(fontSize);

        return row;
    }

    //아이템 데이터 추가를 위한 함수
    public void addItem(Long cno, String content, String imageUrl, int fontsize){
        HashTagGridViewItem item = new HashTagGridViewItem();

        Log.d("addItem 안", "여기는?");

        item.setCno(cno);
        item.setContent(content);
        item.setImageUrl(imageUrl);
        item.setFontsize(fontsize);

        hashtaglist.add(item);
    }

}
