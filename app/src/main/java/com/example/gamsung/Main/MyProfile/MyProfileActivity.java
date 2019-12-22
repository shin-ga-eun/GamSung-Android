package com.example.gamsung.Main.MyProfile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gamsung.Card.CardActivity;
import com.example.gamsung.LoginActivity;
import com.example.gamsung.Main.Hash.HashSearch.HashSearchActivity;
import com.example.gamsung.Main.MainHome.MainHomeActivity;
import com.example.gamsung.Main.UserSearch.UserSearchActivity;
import com.example.gamsung.Main.Write.WriteActivity;
import com.example.gamsung.R;
import com.example.gamsung.controller.UserController;
import com.example.gamsung.domain.dto.card.GetCardByIdentityDto;
import com.example.gamsung.domain.dto.user.GetProfileDto;
import com.example.gamsung.domain.dto.user.UserUpdateDto;
import com.example.gamsung.network.NetRetrofit;

import java.io.File;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity  extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private Uri mImageCaptureUri;

    private SharedPreferences userInfo;
    private SharedPreferences.Editor loginEditor;


    GridView gridview;
    Button btnMainHome;
    Button btnMain, btnSearch, btnCard, btnTimeLine, btnLogout; // 하단버튼목록들
    ImageButton btnImgModify , btnProfileModify;
    TextView textNickname, textTotalView, textProfile;
    ImageView ImgProfile;
    EditText dlgEdtProfile;

    RequestBody body2; //userUpdateDto -> identity
    MultipartBody.Part body; //imgCard
    String json; //서버에 보낼 json

    View dialogView;
    String identity; //로그인 사용자 identity
    String cardToIdentity; //cardActivity에서 넘어온 identity(키값: nickname)
    Long uno; //로그인 사용자 uno ??
    String imageUrl; //사용자 프로필 이미지 uri
    UserUpdateDto userUpdateDto;
    UserController userController;
    MyProfileGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        ////////////////////////////////////////////////////////참조획득/////////////////////////////

        btnMainHome = (Button)findViewById(R.id.btnMainHome);
        btnImgModify = (ImageButton)findViewById(R.id.btnImgModify);
        btnProfileModify = (ImageButton)findViewById(R.id.btnProfileModify);

        textNickname = (TextView)findViewById(R.id.textNickname);
        textTotalView = (TextView)findViewById(R.id.textTotalView);
        ImgProfile = (ImageView)findViewById(R.id.ImgProfile);
        textProfile = (TextView)findViewById(R.id.textProfile);

        //로그인 사용자 identity 가져오기 -> getSharedPreferences()
        userInfo=getSharedPreferences("UserInformation", Activity.MODE_PRIVATE);
        loginEditor = userInfo.edit();
        identity = userInfo.getString("identity",null); //현재 로그인된 사용자 identity

        userController = new UserController(getApplicationContext());

        //CardActivity에서 넘어온 경우, getintent to nickname -> identity = cardToIdentity
        Intent inIntent = getIntent();
        cardToIdentity = inIntent.getStringExtra("nickname");

        Log.d("identity compare >>>>>>>>>>>>>>>>>>>",identity +"            "+cardToIdentity);
        if(cardToIdentity != null) {
            if(!cardToIdentity.equals(identity)) {
                btnImgModify.setVisibility(View.INVISIBLE);
                btnProfileModify.setVisibility(View.INVISIBLE);
            }
            else
                Toast.makeText(getApplicationContext(), cardToIdentity + "의 프로필입니다.", Toast.LENGTH_SHORT).show();

            identity = cardToIdentity;
        }

        ////////게시물 그리드뷰 ////////////////////////////////////////////////////////////////////
        gridview = (GridView)findViewById(R.id.gridview);
        adapter = new MyProfileGridViewAdapter(this);
        gridview.setAdapter(adapter);

        //서버연동
        Log.d("서버 연동 시 참조하는 identity>>>",identity);
        Call<List<GetCardByIdentityDto>> responseCard= NetRetrofit.getInstance().getNetRetrofitInterface().getCardByIdentity(identity);
        responseCard.enqueue(new Callback<List<GetCardByIdentityDto>>() {
            @Override
            public void onResponse(Call<List<GetCardByIdentityDto>> call, Response<List<GetCardByIdentityDto>> response) {
                if(response.isSuccessful()) {
                    Log.d("getCardByTagDto in cardController", "여기 들어와써여");
                    List<GetCardByIdentityDto> resource = response.body();

                    for(GetCardByIdentityDto getCardByIdentityDto: resource){
                        adapter.addItem(getCardByIdentityDto.getCno(), identity, getCardByIdentityDto.getContent(), getCardByIdentityDto.getImageUrl(), getCardByIdentityDto.getFontsize());
                        Log.d("getCardByTagDto",getCardByIdentityDto.getCno().toString());
                        Log.d("getCardByTagDto",getCardByIdentityDto.getContent());
                        Log.d("getCardByTagDto",getCardByIdentityDto.getImageUrl());
                        Log.d("getCardByTagDto", ""+getCardByIdentityDto.getFontsize());
                    }
                    adapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onFailure(Call<List<GetCardByIdentityDto>> call, Throwable t) {

            }

        });


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //카드 cno 넘기기
                Long cno = Long.valueOf(String.valueOf(gridview.getAdapter().getItem(position)));
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra("cno",cno);
                startActivity(intent);
            }
        });



        //프로필사진 수정버튼
        btnImgModify.setOnClickListener(this);

        //자기소개글 수정버튼
        btnProfileModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View)View.inflate(MyProfileActivity.this, R.layout.myprofile_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MyProfileActivity.this);
                dlg.setTitle("자기소개글 입력");
                dlg.setIcon(R.drawable.pencil);
                dlg.setView(dialogView);
                dlg.setPositiveButton("수정",null);
                dlg.setNegativeButton("취소", null);
                dlgEdtProfile = (EditText)dialogView.findViewById(R.id.dlgEdtProfile);
                dlgEdtProfile.setText(textProfile.getText().toString());

                dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textProfile.setText(dlgEdtProfile.getText().toString());

                        userUpdateDto = new UserUpdateDto(identity, textProfile.getText().toString());
                        userController.userUpdate(userUpdateDto);

                    }
                });

                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.show();
            }
        });

        //레트로핏 연동 -회원정보
        Call<GetProfileDto> response= NetRetrofit.getInstance().getNetRetrofitInterface().getProfile(identity);
        response.enqueue(new Callback<GetProfileDto>() {
            @Override
            public void onResponse(Call<GetProfileDto> call, Response<GetProfileDto> response) {
                if(response.isSuccessful()) {

                    GetProfileDto getProfile = response.body();
                    textNickname.setText(getProfile.getNickname());
                    textProfile.setText(getProfile.getProfileText());
                    textTotalView.setText(getProfile.getTotal());
                    imageUrl = getProfile.getImageUrl();
                    uno = getProfile.getUno();

                    Log.d("myProfile imageUrl", imageUrl);
                    Log.d("myProfile uno",uno.toString());

                    //프로필이미지 uri Glide를 통해 넣기.

                    Glide.with(getApplicationContext())
                            .load(imageUrl) // image url
                            //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.isloading) // any placeholder to load at start
                            .error(R.drawable.emptyheart)  // any image in case of error
                            .override(180, 175) // resizing
                            .centerCrop()
                            .into(ImgProfile);  // imageview object

                    Log.d("Glide imageUrl", imageUrl);

                }

            }
            @Override
            public void onFailure(Call<GetProfileDto> call, Throwable t) {

            }

        });

        //상단 메인홈버튼
        btnMainHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /////////////////////////////////////////////////////////////하단버튼목록들////////
        btnMain = (Button) findViewById(R.id.btnMain);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnCard = (Button) findViewById(R.id.btnCard);
        btnTimeLine = (Button) findViewById(R.id.btnTimeLine);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HashSearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserSearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEditor.putString("identity","");
                loginEditor.commit();
                Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_LONG).show();

                Intent Logout=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(Logout);
                finish();
            }
        });



    }

    //    갤러리에서 이미지 가져오는 코드 start ////////////////////////////////////////////////////////////
    //앨범에서 이미지 가져오기
    private void doTakeAlbumAction()
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //실제 사진 경로 받아오기 -> 안드로이드 내부저장소 경로가 아닌, 외부저장소 경로를 가지고 옴
    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
        {
            return;
        }
        switch(requestCode){
            case CROP_FROM_CAMERA:
            {
                final Bundle extras = data.getExtras();

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
                    ImgProfile.setImageBitmap(photo);
                }

                // multipart/////////////////////////////////////////////////////////////////////////////////////////////
                File file = new File(getRealPathFromURI(mImageCaptureUri));

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestFile);

                json = "{\"identity\":\""+identity+"\""+"}";
                Log.d("json",json);
                body2 = RequestBody.create(MediaType.parse("multipart/form-data"), json);

                //회원정보 이미지 수정 retrofit 연동
                Call<ResponseBody> response = NetRetrofit.getInstance().getNetRetrofitInterface().userImageUpdate(body, body2);
                response.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "프로필 수정 성공", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "이미지 업로드 실패"+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                break;
            }

            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
            }


            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기 결정 -> 이미지 크롭 어플리케이션을 호출
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 90);
                intent.putExtra("outputY", 90);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;
            }


        }
    }

    @Override
    public void onClick(View v)
    {

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        };

        //다이얼로그 창
        new android.app.AlertDialog.Builder(this)
                .setTitle("사진 선택")
                .setNegativeButton("취소", cancelListener)
                .setPositiveButton("앨범선택", albumListener)
                .show();

    }
    //    갤러리에서 이미지 가져오는 코드 end ////////////////////////////////////////////////////////////

}


