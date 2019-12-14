package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamsung.MainHome.MyProfile.MyProfileActivity;
import com.example.gamsung.MainHome.Write.WriteActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CardActivity  extends AppCompatActivity {

    Long cardID; //그리드뷰로부터 받은 게시글번호 저장하는 변수
    Button btnMyProfile, btnNickname;
    ImageButton btnComment, btnHeart, btnDownload;
    TextView textCard, textTime;
    TextView textComment, textHeart;
    ImageView imgCard; //배경이미지


    RecyclerView recyclerview;
    CardRecyclerViewAdapter adapter;
    ArrayList<CardRecyclerItem> cardlist = new ArrayList<>();

    boolean heart = false; //공감버튼을 위한 토글변수 -> 서버에서 가져올 boolean 값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent inIntent = getIntent();
        cardID = inIntent.getLongExtra("cardID",0);

        imgCard = (ImageView)findViewById(R.id.imgCard);
        textCard = (TextView)findViewById(R.id.textCard);
        textTime = (TextView)findViewById(R.id.textTime);
        textComment = (TextView)findViewById(R.id.textComment);
        textHeart = (TextView)findViewById(R.id.textHeart);
        btnMyProfile = (Button)findViewById(R.id.btnMyProfile);
        btnNickname = (Button)findViewById(R.id.btnNickname);
        btnComment = (ImageButton)findViewById(R.id.btnComment);
        btnHeart = (ImageButton)findViewById(R.id.btnHeart);
        btnDownload = (ImageButton)findViewById(R.id.btnDownload);


        textCard.setText("서버에서 가져올 텍스트"); //서버에서 가져올 텍스트
        textCard.setTextSize(20); //서버에서 가져올 폰트크기
        imgCard.setImageResource(R.drawable.img1); //서버에서 가져올 이미지
        imgCard.setAlpha(0.5f);
        textTime.setText("2019.11.15 23:29"); //서버에서 가져올 작성시간

        //댓글카드 리사이클러 어댑터//////////////////////////////////////////////
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        adapter = new CardRecyclerViewAdapter(cardlist);
        recyclerview.setAdapter(adapter);

        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        cardlist.add(new CardRecyclerItem(R.drawable.img2, "아이유",24,"2019.11.16"));
        cardlist.add(new CardRecyclerItem(R.drawable.img2, "아이유",16,"2019.11.16"));
        cardlist.add(new CardRecyclerItem(R.drawable.img2, "아이유",12,"2019.11.16"));
        cardlist.add(new CardRecyclerItem(R.drawable.img2, "아이유",24,"2019.11.16"));
        cardlist.add(new CardRecyclerItem(R.drawable.img2, "아이유",16,"2019.11.16"));
        cardlist.add(new CardRecyclerItem(R.drawable.img2, "아이유",12,"2019.11.16"));


        //댓글 리사이클러 뷰 갯수 == 댓글 수
        int commentNum = adapter.getItemCount();
        textComment.setText(""+commentNum);

        //이전버튼 클릭 시, 내프로필 창으로
        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //닉네임버튼 클릭 시, 프로필 창으로
        btnNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), MyProfileActivity.class);
                startActivity(intent);

            }
        });

        //댓글버튼 클릭 시, 카드쓰기 창으로
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), WriteActivity.class);
                startActivity(intent);

            }
        });

        //공감버튼 클릭 시, 공감수 1증가
        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(heart == true) {
                    int num = Integer.parseInt(textHeart.getText().toString()) - 1;
                    textHeart.setText("" + num);
                    heart = false;
                    btnHeart.setImageResource(R.drawable.emptyheart);
                    btnHeart.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    btnHeart.requestLayout();

                }else if(heart == false){
                    int num = Integer.parseInt(textHeart.getText().toString()) + 1;
                    textHeart.setText("" + num);
                    heart = true;
                    btnHeart.setImageResource(R.drawable.heart);
                    btnHeart.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    btnHeart.requestLayout();

                }


            }
        });

        //다운로드버튼 클릭 시, 카드를 갤러리에 저장
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SotongCard";
                final RelativeLayout capture = (RelativeLayout) findViewById(R.id.relativelayout);//캡쳐할영역

                File file = new File(path);

                if(!file.exists()){

                    file.mkdirs();

                    Toast.makeText(getApplicationContext(), "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();

                }


                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");

                Date date = new Date();

                capture.buildDrawingCache();

                Bitmap captureview = capture.getDrawingCache();



                FileOutputStream fos = null;

                try{

                    fos = new FileOutputStream(path+"/Capture"+day.format(date)+".jpeg");

                    captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/Capture" + day.format(date) + ".JPEG")));

                    Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show();

                    fos.flush();

                    fos.close();

                    capture.destroyDrawingCache();

                } catch (FileNotFoundException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                }
                */
            }
        });



    }


}
