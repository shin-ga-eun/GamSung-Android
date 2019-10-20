package com.example.gamsung;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.TextView;

        import java.util.ArrayList;

public class MainHomeListViewAdapter extends BaseAdapter {

    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의 -> 입장 버튼에 사용.
    public interface ListBtnClickListener {
        void onListBtnClick(String name) ;
    }
    private ListBtnClickListener listBtnClickListener ;


    //Adapter에 추가된 데이터를 저장하기위한  ArrayList
    private ArrayList<MainHomeListViewItem> listViewItemList = new ArrayList<MainHomeListViewItem>();

    //InterestListViewAdapter생성자
    public MainHomeListViewAdapter(ListBtnClickListener clickListener){
        this.listBtnClickListener =  clickListener;
    }

    //Adapter에 사용되는 데이터의 개수 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    //position에 위치한 데이터를 화면에 출력하는데 사용될 View 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //roomlist_item 레이아웃을 inflate하여 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mainhome_content_item, parent, false);
        }

        //화면에 표시될 View(Layout이 inflate로 된)으로부터 위젯에 대한 참조 획득
        final TextView textHashTagName = (TextView)convertView.findViewById(R.id.textHashTagName);

        //Data set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final MainHomeListViewItem listViewItem = listViewItemList.get(position);

        //아이템 내 각 위젯에 데이터 반영
        textHashTagName.setText(listViewItem.getHashTagName());

        //버튼 클릭
        Button btnHashTag = (Button)convertView.findViewById(R.id.btnHashTag);
        btnHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textHashTagName.getText().toString();

                listBtnClickListener.onListBtnClick(name);
            }
        });

        return convertView;
    }

    //BaseAdapter 필수함수
    @Override
    public long getItemId(int position) {
        return position;
    }

    //BaseAdapter 필수함수
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    //아이템 데이터 추가를 위한 함수
    public void addItem(String hashname){
        MainHomeListViewItem item = new MainHomeListViewItem();

        item.setHashTagName(hashname);

        listViewItemList.add(item);
    }


}
