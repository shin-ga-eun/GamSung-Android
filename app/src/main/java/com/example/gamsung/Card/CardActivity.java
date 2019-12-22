package com.example.gamsung.Card;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gamsung.Main.MyProfile.MyProfileActivity;
import com.example.gamsung.Main.Write.CardCommentWriteActivity;
import com.example.gamsung.R;
import com.example.gamsung.domain.dto.card.CardDeleteDto;
import com.example.gamsung.domain.dto.card.GetCardDto;
import com.example.gamsung.domain.dto.card.reply.GetReplyByCnoDto;
import com.example.gamsung.network.NetRetrofit;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardActivity  extends AppCompatActivity {

    Long cno; //그리드뷰로부터 받은 게시글번호 저장하는 변수
    Button btnMyProfile, btnNickname;
    ImageButton btnComment, btnHeart, btnDelete;
    TextView textCard, textTime;
    TextView textComment, textHeart;
    ImageView imgCard; //배경이미지

    private SharedPreferences userInfo;
    private SharedPreferences.Editor loginEditor;
    String identity;

    RecyclerView recyclerview;
    CardRecyclerViewAdapter adapter;
    ArrayList<CardRecyclerItem> cardlist = new ArrayList<>();

    boolean heart = false; //공감버튼을 위한 토글변수 -> 서버에서 가져올 boolean 값
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Intent inIntent = getIntent();
        cno = inIntent.getLongExtra("cno",0);

        //로그인 사용자 identity 가져오기 -> getSharedPreferences()
        userInfo=getSharedPreferences("UserInformation", Activity.MODE_PRIVATE);
        loginEditor = userInfo.edit();
        identity = userInfo.getString("identity",null); //현재 로그인된 사용자 identity

        imgCard = (ImageView)findViewById(R.id.imgCard);
        textCard = (TextView)findViewById(R.id.textCard);
        textTime = (TextView)findViewById(R.id.textTime);
        textComment = (TextView)findViewById(R.id.textComment);
        textHeart = (TextView)findViewById(R.id.textHeart);
        btnMyProfile = (Button)findViewById(R.id.btnMyProfile);
        btnNickname = (Button)findViewById(R.id.btnNickname);
        btnComment = (ImageButton)findViewById(R.id.btnComment);
        btnHeart = (ImageButton)findViewById(R.id.btnHeart);
        btnDelete = (ImageButton)findViewById(R.id.btnDelete);

        Toast.makeText(getApplicationContext(), ""+cno, Toast.LENGTH_SHORT).show();
        //레트로핏 연동 -카드
        Call<GetCardDto> response= NetRetrofit.getInstance().getNetRetrofitInterface().getCardByCno(cno);
        response.enqueue(new Callback<GetCardDto>() {
            @Override
            public void onResponse(Call<GetCardDto> call, Response<GetCardDto> response) {
                if(response.isSuccessful()) {

                    GetCardDto getCard = response.body();
                    btnNickname.setText(getCard.getIdentity());
                    textCard.setText(getCard.getContent()); //서버에서 가져올 텍스트
                    textCard.setTextSize(getCard.getFontsize()); //서버에서 가져올 폰트크기
                    imageUrl = getCard.getImageUrl();
                    textTime.setText(getCard.getRegDate()); //서버에서 가져올 작성시간
                    textHeart.setText(""+getCard.getHeart());

                    if(!btnNickname.getText().toString().equals(identity)) {
                        btnDelete.setVisibility(View.INVISIBLE);
                    }

                    //프로필이미지 uri Glide를 통해 넣기.
                    Glide.with(getApplicationContext())
                            .load(imageUrl) // image url
                            //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.isloading) // any placeholder to load at start
                            .error(R.drawable.emptyheart)  // any image in case of error
                            .override(350, 350) // resizing
                            .centerCrop()
                            .into(imgCard);  // imageview object

                    imgCard.setAlpha(0.5f);
                }

            }
            @Override
            public void onFailure(Call<GetCardDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });




        ////////////////////댓글카드 리사이클러 어댑터/////////////////////////////////////////////////
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        adapter = new CardRecyclerViewAdapter(cardlist);
        recyclerview.setAdapter(adapter);

        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        //서버연동
        Call<List<GetReplyByCnoDto>> responseCard= NetRetrofit.getInstance().getNetRetrofitInterface().getReply(cno);
        responseCard.enqueue(new Callback<List<GetReplyByCnoDto>>() {
            @Override
            public void onResponse(Call<List<GetReplyByCnoDto>> call, Response<List<GetReplyByCnoDto>> response) {
                if(response.isSuccessful()) {
                    Log.d("getReplyByCnoDto in replyController", "여기 들어와써여");
                    List<GetReplyByCnoDto> resource = response.body();

                    for(GetReplyByCnoDto getReplyByCnoDto: resource){
                        adapter.addItem(getReplyByCnoDto.getRno(), getReplyByCnoDto.getIdentity(), getReplyByCnoDto.getContent(), getReplyByCnoDto.getImageUrl(), getReplyByCnoDto.getFontsize(), getReplyByCnoDto.getRegDate());
                        Log.d("getReplyByCnoDto",getReplyByCnoDto.getRno().toString());
                        Log.d("getReplyByCnoDto",getReplyByCnoDto.getContent());
                        Log.d("getReplyByCnoDto",getReplyByCnoDto.getImageUrl());
                        Log.d("getReplyByCnoDto", ""+getReplyByCnoDto.getFontsize());
                        Log.d("getReplyByCnoDto", ""+getReplyByCnoDto.getRegDate());
                    }
                    adapter.notifyDataSetChanged();

                    //댓글 리사이클러 뷰 갯수 == 댓글 수
                    int commentNum = adapter.getItemCount();
                    Log.d("reply >>>>>>>>>>>>>>> commentNum >>>>>>>>", ""+commentNum);
                    textComment.setText(""+commentNum);

                }
            }
            @Override
            public void onFailure(Call<List<GetReplyByCnoDto>> call, Throwable t) {

            }

        });


        //이전버튼 클릭 시, 내프로필 창으로
        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //닉네임버튼 클릭 시, 해당 프로필 창으로
        btnNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = btnNickname.getText().toString();
                Intent intent = new Intent (getApplicationContext(), MyProfileActivity.class);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
                finish();

            }
        });

        //카드삭제 -> 사용자인 경우 버튼 활성화되어 카드 삭제 가능
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardDeleteDto cardDeleteDto = new CardDeleteDto();
                cardDeleteDto.setCno(cno);

                Call<Void> response= NetRetrofit.getInstance().getNetRetrofitInterface().deleteCard(cardDeleteDto);
                response.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "카드를 삭제하였습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

                Intent intent = new Intent (getApplicationContext(), MyProfileActivity.class);
                intent.putExtra("nickname", identity);
                startActivity(intent);
                finish();

            }

        });
        //댓글버튼 클릭 시, 카드쓰기 창으로
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), CardCommentWriteActivity.class);
                intent.putExtra("cno",cno);
                startActivity(intent);
                finish();
            }
        });

        /*
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

         */


    }


}
