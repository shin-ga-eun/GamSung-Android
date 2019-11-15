package com.example.gamsung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

//카드작성 페이지2
//서버로 넘길 데이터-> imgCard, content, fontSize
public class CardCommentWriteImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private Uri mImageCaptureUri;

    Button btnWrite;
    Button btnFont;
    TextView textCard;
    ImageView imgCard; //배경이미지

    ImageButton btnGallery, btnCheck;

    String content; //카드내용 받는 변수
    int fontSize; //폰트크기 받는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardcommentwriteimage);

        imgCard = (ImageView)findViewById(R.id.imgCard);
        textCard = (TextView)findViewById(R.id.textCard);
        btnFont = (Button)findViewById(R.id.btnFont);
        btnWrite = (Button)findViewById(R.id.btnWrite);
        btnCheck = (ImageButton)findViewById(R.id.btnCheck);
        btnGallery = (ImageButton)findViewById(R.id.btnGallery);

        //갤러리이동
        btnGallery.setOnClickListener(this);

        //이전 버튼 클릭 시, 글쓰기 페이지로 이동
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        content = intent.getStringExtra("content");

        //content에 널값이 넘어왔을 때 처리
        if(content.getBytes().length <= 0){
            Toast.makeText(getApplicationContext(), "카드내용을 입력하지 않았습니다.", Toast.LENGTH_LONG).show();
            content = "자유롭게 글을 써보세요...";
        }

        textCard.setText(content);
        imgCard.setAlpha(0.5f);

        //폰트크기조절 버튼// 크게->작게->원본
        btnFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (btnFont.getText().toString()){
                    case "크게":
                        fontSize = 30;
                        textCard.setTextSize(fontSize);
                        btnFont.setText("작게");
                        break;
                    case "작게":
                        fontSize = 15;
                        textCard.setTextSize(fontSize);
                        btnFont.setText("원본");
                        break;
                    case "원본":
                        fontSize = 20;
                        textCard.setTextSize(fontSize);
                        btnFont.setText("크게");
                        break;
                }

            }
        });

        //완료체크 버튼 클릭 시, 이전 카드페이지로 이동, 서버에 (댓글) 이미지,폰트크기,내용을 보냄.
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), CardActivity.class);
                startActivity(intent);
                finish();
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
                    imgCard.setImageBitmap(photo);
                    imgCard.setAlpha(0.5f);
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
        new AlertDialog.Builder(this)
                .setTitle("사진 선택")
                .setNegativeButton("취소", cancelListener)
                .setPositiveButton("앨범선택", albumListener)
                .show();

    }


}
