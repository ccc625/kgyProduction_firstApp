package com.example.kgy_product;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {


    static final int PICK_FROM_CAMERA = 0;
    static final int PICK_FROM_ALBUM = 1;
    static final int CROP_FROM_IMAGE = 2;
 static final int   PERMISSIONS_REQUEST_CAMERA=3;


    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absoultePath;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_main);


        iv_UserPhoto = (ImageView) this.findViewById(R.id.user_image);
        Button btn_agreeJoin = (Button) this.findViewById(R.id.btn_UploadPicture);
        btn_agreeJoin.setOnClickListener(this);

        int permissionCheckResult = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        );
        if (permissionCheckResult != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA
            );
        }

        else {
            doTakePhotoAction();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if ((grantResults.length == 0) || (grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
            return;
        }

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA:
                doTakePhotoAction();
                break;
        }
    }

    public void onClick(View v) {
        id_view = v.getId();
        if (v.getId() == R.id.btn_UploadPicture) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {
                    doTakePhotoAction();
                }

            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    doTakeAlbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            };
            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영",cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소" , cancelListener)
                    .show();

        }
    }


    public void doTakePhotoAction()
    {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void doTakeAlbumAction()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent , PICK_FROM_ALBUM);

    }
    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode !=RESULT_OK)
            return;

        switch (requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
                Log.d("hi",mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:
            {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");

                intent.putExtra("outputX",200);
                intent.putExtra("outputY",200);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;

            }
        case CROP_FROM_IMAGE:
        {
            if(resultCode !=RESULT_OK){
                return;
            }
            final Bundle extras = data.getExtras();

            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    "/SmartWheel/" +System.currentTimeMillis()+".jpg";

            if(extras !=null)
            {
                Bitmap photo = extras.getParcelable("data");
                iv_UserPhoto.setImageBitmap(photo);

                storeCropImage(photo,filePath);
                absoultePath=filePath;
                  break;

            }
            File f = new File(mImageCaptureUri.getPath());
            if (f.exists())
            {
                f.delete();
            }
        }
    }



}

    private void storeCropImage(Bitmap bitmap , String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if (!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();  //아웃스트림등의 모든버퍼가 채워지면 flush나 close의 모든내용을 하드디스크파일에 출력한다.
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


