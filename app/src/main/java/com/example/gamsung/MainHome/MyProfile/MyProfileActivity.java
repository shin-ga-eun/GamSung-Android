package com.example.gamsung.MainHome.MyProfile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.example.gamsung.CardActivity;
import com.example.gamsung.LoginActivity;
import com.example.gamsung.MainHome.HashSearch.HashSearchActivity;
import com.example.gamsung.MainHome.MainHomeActivity;
import com.example.gamsung.MainHome.UserSearch.UserSearchActivity;
import com.example.gamsung.MainHome.Write.WriteActivity;
import com.example.gamsung.R;
import com.example.gamsung.controller.UserController;
import com.example.gamsung.domain.dto.user.GetProfileDto;
import com.example.gamsung.domain.dto.user.UserUpdateDto;
import com.example.gamsung.network.NetRetrofit;

import java.io.File;
import java.util.ArrayList;

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
    TextView textNickname, textTodayView, textTotalView, textProfile;
    ImageView ImgProfile;
    EditText dlgEdtProfile;

    RequestBody body2; //userUpdateDto -> identity
    MultipartBody.Part body; //imgCard
    String json; //서버에 보낼 json

    View dialogView;
    String identity; //로그인 사용자 identity
    Long uno; //로그인 사용자 uno ??
    String imageUrl; //사용자 프로필 이미지 uri
    UserUpdateDto userUpdateDto;
    UserController userController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        //로그인 사용자 identity 가져오기 -> getSharedPreferences()
        userInfo=getSharedPreferences("UserInformation", Activity.MODE_PRIVATE);

        identity = userInfo.getString("identity",null);

        userController = new UserController(getApplicationContext());
        ////////게시물 그리드뷰 /////////////////////////////////////////////////////////////////////서버에서 게시글번호를 통해 데이터를 받아야하는지.
        gridview = (GridView)findViewById(R.id.gridview);

        ArrayList<MyProfileGridViewItem> myprofileList = new ArrayList<>();
        myprofileList.add(new MyProfileGridViewItem(R.drawable.img2, "아이유",30));
        myprofileList.add(new MyProfileGridViewItem(R.drawable.img2, "아이유",20));
        myprofileList.add(new MyProfileGridViewItem(R.drawable.img2, "아이유",15));
        myprofileList.add(new MyProfileGridViewItem(R.drawable.img2, "아이유",30));
        myprofileList.add(new MyProfileGridViewItem(R.drawable.img2, "아이유",20));
        myprofileList.add(new MyProfileGridViewItem(R.drawable.img2, "아이유",15));

        MyProfileGridViewAdapter adapter = new MyProfileGridViewAdapter(this, myprofileList);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("iii",""+id);
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra("cardID",id);
                startActivity(intent);
            }
        });

        ////////////////////////////////////////////////////////참조획득/////////////////////////////

        btnMainHome = (Button)findViewById(R.id.btnMainHome);
        btnImgModify = (ImageButton)findViewById(R.id.btnImgModify);
        btnProfileModify = (ImageButton)findViewById(R.id.btnProfileModify);

        textNickname = (TextView)findViewById(R.id.textNickname);
        textTodayView = (TextView)findViewById(R.id.textTodayView);
        textTotalView = (TextView)findViewById(R.id.textTotalView);
        ImgProfile = (ImageView)findViewById(R.id.ImgProfile);
        textProfile = (TextView)findViewById(R.id.textProfile);



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
                    textTodayView.setText(getProfile.getToday());
                    textTotalView.setText(getProfile.getTotal());
                    imageUrl = getProfile.getImageUrl();
                    uno = getProfile.getUno();

                    Log.d("myProfile imageUrl", imageUrl);
                    Log.d("myProfile uno",uno.toString());

                }

            }
            @Override
            public void onFailure(Call<GetProfileDto> call, Throwable t) {

            }

        });

        //프로필이미지 uri Glide를 통해 넣기.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(getApplicationContext())
                        .load(imageUrl) // image url
                        .placeholder(R.drawable.isloading) // any placeholder to load at start
                        .error(R.drawable.emptyheart)  // any image in case of error
                        .override(180, 175) // resizing
                        .centerCrop()
                        .into(ImgProfile);  // imageview object

                Log.d("Glide imageUrl", imageUrl);


                }
            }, 1800);



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

            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HashSearchActivity.class);
                startActivity(intent);
            }
        });
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });
        btnTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserSearchActivity.class);
                startActivity(intent);
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
            }
        });



    }

    //    갤러리에서 이미지 가져오는 코드 start ////////////////////////////////////////////////////////////
    //카메라에서 이미지 가져오기
    private void doTakePhotoAction()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        //intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);

    }

    //앨범에서 이미지 가져오기
    private void doTakeAlbumAction()
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

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
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
                    ImgProfile.setImageBitmap(photo);
//                    ImgProfile.setAlpha(0.5f);
                }
                // 임시 파일 삭제
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

//                if(f.exists())
//                {
//                    f.delete();
//                }
                break;
            }

            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
            }

            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
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

        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }

        };

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


