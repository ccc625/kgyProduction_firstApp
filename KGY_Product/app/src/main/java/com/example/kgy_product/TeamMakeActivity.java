package com.example.kgy_product;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.kgy_product.teamMake.BottomLayout;
import com.example.kgy_product.teamMake.ImageSelectLayout;
import com.example.kgy_product.teamMake.MakeTeamLayout;
import com.example.kgy_product.teamMake.TeamInfoLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ccc62 on 2017-03-25.
 */

public class TeamMakeActivity extends AppCompatActivity
{
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private static final int PERMISSIONS_REQUEST_CAMERA = 1001;

    private static final int MODE_MIN = 101;
    private static final int MODE_MAX = 103;
    private static final int MODE_MAKE_TEAM = 101;
    private static final int MODE_INFO_TEAM = 102;
    private static final int MODE_IMAGE_SELECT = 103;

    private static final int CONTENT_INDEX = 1;
    private static final int BOTTOM_INDEX = 2;

    private LinearLayout lMakeTeam;

    private EditText txtTeamName;
    private Spinner memberCountSpinner;

    private BottomLayout bottomLayout;
    private MakeTeamLayout makeTeamLayout;
    private TeamInfoLayout teamInfoLayout;
    private ImageSelectLayout imageSelectLayout;

    private int currentMode;

    private BottomLayout.ButtonCallback bottomButtonListener;
    private ImageSelectLayout.PhotoActionListener photoActionListener;

    private Uri mImageCaptureUri;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);

        init();
    }

    @Override
    protected void onDestroy()
    {
        txtTeamName = null;
        memberCountSpinner = null;

        bottomLayout = null;

        super.onDestroy();
    }

    private void init()
    {
        currentMode = 101;

        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
        lMakeTeam = ( LinearLayout ) findViewById( R.id.lMakeTeam );

        initBottomLayout();

        setMode( currentMode );
    }

    private void initBottomLayout()
    {
        if( bottomLayout == null )
            bottomLayout = new BottomLayout(this);
        lMakeTeam.addView( bottomLayout );
    }

    private void setMode( int mode )
    {
        if( lMakeTeam.getChildCount() >= 3 )
            lMakeTeam.removeViewAt( CONTENT_INDEX );

        switch( mode )
        {
            case MODE_MAKE_TEAM :
                if( makeTeamLayout == null )
                    makeTeamLayout = new MakeTeamLayout(this);
                lMakeTeam.addView( makeTeamLayout, CONTENT_INDEX );
                break;
            case MODE_INFO_TEAM :
                if( teamInfoLayout == null )
                    teamInfoLayout = new TeamInfoLayout(this);
                lMakeTeam.addView( teamInfoLayout, CONTENT_INDEX );
                break;
            case MODE_IMAGE_SELECT :
                if( imageSelectLayout == null )
                {
                    imageSelectLayout = new ImageSelectLayout(this);
                    imageSelectLayout.setPhotoActionListener( photoActionListener );
                }
                lMakeTeam.addView( imageSelectLayout, CONTENT_INDEX );
                break;
        }
    }

    private void initListener()
    {
        bottomButtonListener = new BottomLayout.ButtonCallback() {
            @Override
            public void onClickPrevButton()
            {
                System.out.println("btnPrev");
                if( currentMode > MODE_MIN )
                    currentMode -= 1;

                setMode( currentMode );
            }

            @Override
            public void onClickNextButton()
            {
                System.out.println("btnNext");
                if( currentMode < MODE_MAX )
                    currentMode += 1;

                setMode( currentMode );
            }
        };

        photoActionListener = new ImageSelectLayout.PhotoActionListener()
        {
            @Override
            public void doTakePhotoAction()
            {
                checkPermission();
            }

            @Override
            public void doTakeAlbumAction()
            {
                takeAlbumAction();
            }
        };

        bottomLayout.setButtonCallback( bottomButtonListener );
    }

    private void takePhotoAction()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void checkPermission()
    {
        int permissionCheckResult = ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA );

        if (permissionCheckResult != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions( this, new String[]{ Manifest.permission.CAMERA }, PERMISSIONS_REQUEST_CAMERA );
        }
        else
        {
            takePhotoAction();
        }
    }

    private void takeAlbumAction()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent , PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
                Log.d("hi", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:
            {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;

            }
            case CROP_FROM_IMAGE:
            {
                Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/SmartWheel/" + System.currentTimeMillis() + ".jpg";

                if (extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
                    storeCropImage( photo, filePath );

                    if( imageSelectLayout != null )
                    {
                        imageSelectLayout.setImage( photo );
                    }
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

    private void storeCropImage(Bitmap bitmap , String filePath)
    {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if (!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try
        {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();  //아웃스트림등의 모든버퍼가 채워지면 flush나 close의 모든내용을 하드디스크파일에 출력한다.
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
