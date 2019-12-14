package com.example.gamsung.MainHome.MyProfile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamsung.R;

import java.util.ArrayList;

//서버로부터 이미지 받아와야함
public class MyProfileGridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MyProfileGridViewItem> myprofileList;

    public MyProfileGridViewAdapter(Context context, ArrayList<MyProfileGridViewItem> myprofileList){
        this.context = context;
        this.myprofileList = myprofileList;
    }

    @Override
    public int getCount() {
        return myprofileList.size();
    }

    @Override
    public Object getItem(int position) {
        return myprofileList.get(position);
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

        MyProfileGridViewItem myProfileGridViewItem = myprofileList.get(position);

        imgCard.setImageResource(myProfileGridViewItem.getImg());
        imgCard.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgCard.setAlpha(0.5f);
        imgCard.setPadding(5,5,5,5);
        textContent.setText(myProfileGridViewItem.getContent());
        int fontSize = (int)((myProfileGridViewItem.getFontSize()) * 0.6);
        textContent.setTextSize(fontSize);

        return row;
    }
}
