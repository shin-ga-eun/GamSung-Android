package com.example.gamsung;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//서버로부터 이미지 받아와야함
public class HashTagGridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashTagGridViewItem> hashtaglist;

    public HashTagGridViewAdapter(Context context, ArrayList<HashTagGridViewItem> myprofileList){
        this.context = context;
        this.hashtaglist = myprofileList;
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
        if(row == null){
            LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
            row = layoutInflater.inflate(R.layout.myprofile_item, parent, false);
        }
        ImageView imgCard = (ImageView)row.findViewById(R.id.imgCard);
        TextView textContent = (TextView)row.findViewById(R.id.textContent);

        HashTagGridViewItem hashTagGridViewItem = hashtaglist.get(position);

        imgCard.setImageResource(hashTagGridViewItem.getImg());
        imgCard.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgCard.setAlpha(0.5f);
        imgCard.setPadding(5,5,5,5);
        textContent.setText(hashTagGridViewItem.getContent());
        int fontSize = (int)((hashTagGridViewItem.getFontSize()) * 0.6);
        textContent.setTextSize(fontSize);

        return row;
    }
}
