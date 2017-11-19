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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kgy_product.teamMake.BottomLayout;
import com.example.kgy_product.teamMake.ImageSelectLayout;
import com.example.kgy_product.teamMake.MakeTeamLayout;
import com.example.kgy_product.teamMake.TeamInfoLayout;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

    private LinearLayout makeTeamContentLayout;
    private ScrollView makeTeamContentScroll;

    private LinearLayout makeTeamBottomLayout;

    private BottomLayout bottomLayout;
    private MakeTeamLayout makeTeamLayout;
    private TeamInfoLayout teamInfoLayout;
    private ImageSelectLayout imageSelectLayout;

    private int currentMode;

    private BottomLayout.ButtonCallback bottomButtonListener;
    private ImageSelectLayout.PhotoActionListener photoActionListener;

    private Uri mImageCaptureUri;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);


        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        Toast t = Toast.makeText(this,location,Toast.LENGTH_LONG);
        t.show();

        init();
    }


    @Override
    protected void onDestroy()
    {
        removeListener();

        if( bottomLayout != null)
        {
            bottomLayout.dispose();
            bottomLayout = null;
        }

        if( makeTeamLayout != null )
        {
            makeTeamLayout.dispose();
            makeTeamLayout = null;
        }

        if( teamInfoLayout != null )
        {
//            teamInfoLayout.dispose();
            teamInfoLayout = null;
        }

        if( imageSelectLayout != null )
        {
//            imageSelectLayout.dispose();
            imageSelectLayout = null;
        }

        if( makeTeamContentLayout != null )
        {
            makeTeamContentLayout.destroyDrawingCache();
            makeTeamContentLayout = null;
        }

        if( makeTeamContentScroll != null )
        {
            makeTeamContentScroll.destroyDrawingCache();
            makeTeamContentScroll = null;
        }

        if( makeTeamBottomLayout != null )
        {
            makeTeamBottomLayout.destroyDrawingCache();
            makeTeamBottomLayout = null;
        }

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
        makeTeamBottomLayout = ( LinearLayout ) findViewById( R.id.makeTeamBottomLayout );
        makeTeamContentScroll = ( ScrollView ) findViewById( R.id.makeTeamContentScroll );

        makeTeamBottomLayout = ( LinearLayout ) findViewById( R.id.makeTeamBottomLayout );

        initBottomLayout();

        setMode( currentMode );
    }

    private void initBottomLayout()
    {
        if( bottomLayout == null )
            bottomLayout = new BottomLayout(this);

        makeTeamBottomLayout.addView( bottomLayout );
    }

    private void setMode( int mode )
    {
        if( makeTeamContentScroll.getChildCount() > 0)
            makeTeamContentScroll.removeViewAt(0);

        switch( mode )
        {
            case MODE_MAKE_TEAM :
                if( makeTeamLayout == null )
                    makeTeamLayout = new MakeTeamLayout(this);
                makeTeamContentScroll.addView( makeTeamLayout );
                break;
            case MODE_INFO_TEAM :
                if( teamInfoLayout == null )
                    teamInfoLayout = new TeamInfoLayout(this);
                makeTeamContentScroll.addView( teamInfoLayout );
                break;
            case MODE_IMAGE_SELECT :
                if( imageSelectLayout == null )
                {
                    imageSelectLayout = new ImageSelectLayout(this);
                    imageSelectLayout.setPhotoActionListener( photoActionListener );
                }
                makeTeamContentScroll.addView( imageSelectLayout );
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

    private void removeListener()
    {
        if( bottomLayout != null )
        {
            bottomLayout.setButtonCallback(null);
        }

        if( imageSelectLayout != null )
        {
            imageSelectLayout.setPhotoActionListener(null);
        }
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
        System.out.println(requestCode);
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

                if (extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
                    String image = getBase64String(photo);

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

    private String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }

}
