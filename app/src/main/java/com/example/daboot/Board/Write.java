package com.example.daboot.Board;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.daboot.MainActivity;
import com.example.daboot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Write extends AppCompatActivity {

    private Integer [] btn_id = {R.id.btn_board_write_Free,R.id.btn_board_write_Question,R.id.btn_board_write_Market}; //카테고리 버튼 "정보" 제외 4개의 아이디 값을 넣은 배열
    private String [] keyword = {"자유","질문","장터"}; // 4가지의 카테고리(전체, 자유, 질문, 장터)
    private Button [] category_btn = new Button[3]; //카테고리(전체,자유,장터 등..)를 담을 버튼 배열

    private String category = "자유";
    private String imageFilePath,imageFilename;
    private String title,contents, writer,time,anonymous="true",imgpath="none";
    private boolean want_anonymous = true,want_photo=false;
    private Bitmap bitmap = null;
    private Uri photoUri,file = null;
    private static int REQUEST_CAMERA_CODE = 0,REQUEST_FIND_CODE = 1;

    private Button btn_cancel, btn_finish;
    private TextView tv_imgpath;
    private EditText edt_title, edt_contents;
    private Switch btn_anonymous;
    private ImageView imageView;
    private ImageButton btn_camera, btn_find_img, btn_delete_img;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public void Call_Toast(String message){ Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();}

    private String getCurrentDate(SimpleDateFormat simpleDate){ // 현재 시간을 String형으로 반환해주는 함수
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        String getTime = simpleDate.format(mDate);
        return getTime;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        tv_imgpath = findViewById(R.id.tv_board_write_imgpath);
        edt_title = findViewById(R.id.edt_board_write_title);
        edt_contents = findViewById(R.id.edt_board_write_contents);

        btn_anonymous = findViewById(R.id.btn_board_write_anonymous);
        btn_finish = findViewById(R.id.btn_baord_wrtie_finish);
        btn_cancel = findViewById(R.id.btn_board_write_cancel);

        imageView = findViewById(R.id.img_board_write1);
        btn_camera = findViewById(R.id.btn_board_camera);
        btn_find_img = findViewById(R.id.btn_board_find_img);
        btn_delete_img = findViewById(R.id.btn_board_delete_img);


        /* 반복문을 이용한 4개의 카테고리 버튼에 대한 onClickListener 선언 부분이며 우리가 아는 setOnClickListener를 반복문으로 짧게 써놓은것   */
        for(int i=0; i<3; i++){
            category_btn[i] = findViewById((btn_id[i]));
            final int index = i;

            category_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category_btn[index].setSelected(true);
                    category = keyword[index];
                    Toast.makeText(getApplicationContext(),keyword[index],Toast.LENGTH_SHORT).show();
                }
            });
        }


        btn_anonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                want_anonymous = !want_anonymous;
                anonymous = want_anonymous ? "true" : "false";
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {

                    }
                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getApplication(), getPackageName(), photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
                    }
                }
            }
        });

        btn_find_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_FIND_CODE);
            }
        });

        btn_delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.ic_image);
                tv_imgpath.setText("업로드 된 이미지가 없습니다.");
                bitmap = null;
                want_photo = false;
            }
        });


        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writer = user.getEmail();
                title = edt_title.getText().toString();
                contents = edt_contents.getText().toString();
                time = getCurrentDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                if(want_photo) {
                    imageFilename = getCurrentDate(new SimpleDateFormat("yyyyMMdd_HHmmss"));
                    imgpath = "Board/" + category + "/" + imageFilename + ".png";
                }

                if(category.length()>0 && writer.length()>0 && title.length()>0 && contents.length()>0 && time.length()>0 && anonymous.length()>0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                   BoardInfo boardInfo = new BoardInfo(category,anonymous,title, writer,contents,0,time,imgpath);
                    db.collection("Board").add(boardInfo)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    if(want_photo) { // 게시글 등록 성공시 사진 여부를 보고 사진도 올림
                                        StorageReference storageRef = storage.getReference();
                                        StorageReference riverRef = storageRef.child(imgpath);
                                        UploadTask uploadTask = riverRef.putFile(file);
                                        if (bitmap != null) {
                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Call_Toast("사진 등록 실패");
                                                }
                                            });
                                        }
                                    }
                                    Call_Toast("게시글을 등록하였습니다..");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { Call_Toast("실패."); }
                        });
                } else { Call_Toast("입력 값을 확인해주세요."); }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FIND_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    file = data.getData();
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    tv_imgpath.setText(file.getPath());
                    imageView.setImageBitmap(bitmap);
                    want_photo=true;
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Call_Toast("사진 선택 취소");
            }
        }
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK) {
            file = photoUri;
            tv_imgpath.setText(file.getPath()+"");
            imageView.setImageURI(photoUri);
            want_photo=true;
        }
    }
}
