package com.example.gamsung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//서버로부터 이미지 받아와야함
public class MyProfileGridViewAdapter extends BaseAdapter {

    public MyProfileGridViewAdapter(){

    }


    @Override
    public int getCount() {  //카드개수 반환
        return 0;
    }

    private ArrayList<MyProfileGridViewItem> gridViewItemArrayList = new ArrayList<MyProfileGridViewItem>();


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView iv = new ImageView(context);
//        iv.setLayoutParams(new GridView.LayoutParams(500,500));
//        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        iv.setPadding(5,5,5,5);
//
//        iv.setImageResource(cardID[position]);
//        return iv;
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.myprofile_item, parent, false);
        }

        final ImageView imgCard = (ImageView)convertView.findViewById(R.id.imgCard);
        final TextView textCard = (TextView)convertView.findViewById(R.id.textCard);

        final MyProfileGridViewItem gridViewItem = gridViewItemArrayList.get(position);

        imgCard.setLayoutParams(new GridView.LayoutParams(500,500));
        imgCard.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgCard.setPadding(5,5,5,5);

        /*
        imgCard.setImageResource(R.drawable.img2); //서버에서 가져와야할부분 (이미지)
        textCard.setText("아이유입니다."); //서버에서 가져와야할부분 (글내용)
        textCard.setTextSize(20); //서버에서 가져와야할부분 (폰트크기)
        */

        imgCard.setImageResource(gridViewItem.getImg());
        textCard.setText(gridViewItem.getContent());
        textCard.setTextSize(gridViewItem.getFontSize());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return gridViewItemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //아이템 데이터 추가를 위한 함수
    public void addItem(String content, int img, int fontSize){
        MyProfileGridViewItem item = new MyProfileGridViewItem();

        item.setContent(content);
        item.setImg(img);
        item.setFontSize(fontSize);

        gridViewItemArrayList.add(item);
    }

}
