package com.example.gamsung.MainHome.MyProfile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.example.gamsung.CardActivity;
import com.example.gamsung.LoginActivity;
import com.example.gamsung.MainHome.HashSearch.HashSearchActivity;
import com.example.gamsung.MainHome.MainHomeActivity;
import com.example.gamsung.MainHome.UserSearch.TimeLineActivity;
import com.example.gamsung.MainHome.Write.WriteActivity;
import com.example.gamsung.R;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    View dialogView;
    String contentProfile; //자기소개변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

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

        ////////////////////////////////////////////////////////////////////////////////////////////

        btnMainHome = (Button)findViewById(R.id.btnMainHome);
        btnImgModify = (ImageButton)findViewById(R.id.btnImgModify);
        btnProfileModify = (ImageButton)findViewById(R.id.btnProfileModify);
        textNickname = (TextView)findViewById(R.id.textNickname);
        textTodayView = (TextView)findViewById(R.id.textTodayView);
        textTotalView = (TextView)findViewById(R.id.textTotalView);
        ImgProfile = (ImageView)findViewById(R.id.ImgProfile);
        textProfile = (TextView)findViewById(R.id.textProfile);

        btnMainHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
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

        /////////////////////////////////////////////////////////////하단버튼목록들////////
        btnMain = (Button) findViewById(R.id.btnMain);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnCard = (Button) findViewById(R.id.btnCard);
        btnTimeLine = (Button) findViewById(R.id.btnTimeLine);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        userInfo=getSharedPreferences("UserInformation", Activity.MODE_PRIVATE);
        loginEditor = userInfo.edit();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), TimeLineActivity.class);
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
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
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


