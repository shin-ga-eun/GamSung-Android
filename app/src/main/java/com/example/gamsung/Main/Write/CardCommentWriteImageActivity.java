package com.example.gamsung.Main.Write;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamsung.Card.CardActivity;
import com.example.gamsung.R;
import com.example.gamsung.network.NetRetrofit;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gamsung.LoginActivity.loginCheck;

//카드작성 페이지2
//서버로 넘길 데이터-> imgCard, content, fontSize, cno
public class CardCommentWriteImageActivity extends AppCompatActivity implements View.OnClickListener {

    MultipartBody.Part body; //imgCard
    RequestBody body2; //replySaveDto-> content, fontSize, identity
    String json; //서버에 보낼 json
    Long cno;

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
    int fontSize = 20; //폰트크기 받는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardcommentwriteimage);

        //해당 카드 글번호
        Intent inIntent = getIntent();
        cno = inIntent.getLongExtra("cno",0);

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

                if (body != null) {
                    Call<ResponseBody> response = NetRetrofit.getInstance().getNetRetrofitInterface().saveReply(body, body2);
                    response.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "댓글 카드 작성 성공", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "이미지 업로드 실패" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                    intent.putExtra("cno",cno);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "이미지를 등록해주세요.", Toast.LENGTH_LONG).show();

            }
            });

    }

    /////갤러리에서 이미지 가져오는 코드 start ////////////////////////////////////////////////////////////

    //앨범에서 이미지 가져오기
    private void doTakeAlbumAction()
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(intent, PICK_FROM_ALBUM); //case문 이동
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
                final Bundle extras = data.getExtras();

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
                    imgCard.setImageBitmap(photo);
                    imgCard.setAlpha(0.5f);
                }

                // multipart/////////////////////////////////////////////////////////////////////////////////////////////
                File file = new File(getRealPathFromURI(mImageCaptureUri));

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestFile);
                content = content.replace("\n","__"); //서버에 전송 전 개행문자 처리

                json = "{\"identity\":\""+loginCheck+"\",\"cno\":"+cno+",\"content\":\""+content+"\",\"fontsize\":"+fontSize+"}";
                Log.d("json",json);
                body2 = RequestBody.create(MediaType.parse("multipart/form-data"), json);

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
        new AlertDialog.Builder(this)
                .setTitle("사진 선택")
                .setNegativeButton("취소", cancelListener)
                .setPositiveButton("앨범선택", albumListener)
                .show();

    }


}
