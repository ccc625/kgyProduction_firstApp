package com.example.kgy_product;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.kgy_product.networkTask.NetworkdAdaptor;
import com.example.kgy_product.scheduler.ScheduleNode;
import com.example.kgy_product.scheduler.Scheduler;
import com.example.kgy_product.teamMake.BottomLayout;
import com.example.kgy_product.teamMake.ImageSelectLayout;
import com.example.kgy_product.teamMake.MakeTeamLayout;
import com.example.kgy_product.teamMake.TeamInfoLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ccc62 on 2017-03-25.
 */

public class TeamMakeActivity extends AppCompatActivity
{
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

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

    private JSONArray arrAlcohol;

    private String location;
    private HashMap<String, String> makeTeamLayoutData;
    private HashMap<String, String> teamInfoLayoutData;
    private String strImage;

    private BottomLayout.ButtonCallback bottomButtonListener;
    private ImageSelectLayout.PhotoActionListener photoActionListener;

    private Uri mImageCaptureUri;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);


        Intent intent = getIntent();
        location = intent.getStringExtra("location");

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
            teamInfoLayout.dispose();
            teamInfoLayout = null;
        }

        if( imageSelectLayout != null )
        {
            imageSelectLayout.dispose();
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

        Scheduler.OnCompleteSchedulerListener onCompleteSchedulerListener = new Scheduler.OnCompleteSchedulerListener()
        {
            public void onComplete()
            {
                System.out.println("onCompleteScheduler");
            }
        };

        Scheduler scheduler = new Scheduler(onCompleteSchedulerListener);
        ScheduleNode node;

        ScheduleNode.ScheduleAction alcoholAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(final Callback callback)
            {
                NetworkdAdaptor.NetworkCallback networkCallback = new NetworkdAdaptor.NetworkCallback() {
                    @Override
                    public void onResponse(JSONObject data)
                    {
                        System.out.println(data);

                        try
                        {
                            arrAlcohol = (JSONArray) data.get("result");
                        }
                        catch( JSONException exception )
                        {
                            exception.printStackTrace();
                        }

                        callback.excute();
                    }
                };

                NetworkdAdaptor.instance().getCommonList(networkCallback, "ALCOHOL");
            }
        };

        node = new ScheduleNode("alcoholAction", alcoholAction);
        scheduler.add( node );

        ScheduleNode.ScheduleAction initDisplayAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(Callback callback)
            {
                initDisplayObject();
                callback.excute();
            }
        };

        node = new ScheduleNode("initDisplayAction", initDisplayAction);
        scheduler.add(node);

        ScheduleNode.ScheduleAction initListenerAction = new ScheduleNode.ScheduleAction() {
            @Override
            public void excute(Callback callback)
            {
                initListener();
                callback.excute();
            }
        };

        node = new ScheduleNode("initListenerAction", initListenerAction);
        scheduler.add(node);

        scheduler.start();
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
                if( teamInfoLayout == null ) {
                    teamInfoLayout = new TeamInfoLayout(this);
                    teamInfoLayout.setData(arrAlcohol);
                }
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

                doActionLayoutExit();

                if( currentMode > MODE_MIN )
                    currentMode -= 1;
                else
                    return;

                setMode( currentMode );
            }

            @Override
            public void onClickNextButton()
            {
                System.out.println("btnNext");

                doActionLayoutExit();

                if( currentMode < MODE_MAX ) {
                    currentMode += 1;
                }
                else
                {
                    NetworkdAdaptor.NetworkCallback callback = new NetworkdAdaptor.NetworkCallback() {
                        @Override
                        public void onResponse(JSONObject data)
                        {
                            ///TODO 다음 액티비티로 넘겨주기
                        }
                    };

                    registerTeam(callback);
                    return;
                }


                setMode( currentMode );
            }
        };

        photoActionListener = new ImageSelectLayout.PhotoActionListener()
        {
            @Override
            public void doTakePhotoAction()
            {
                takePhotoAction();
            }

            @Override
            public void doTakeAlbumAction()
            {
                takeAlbumAction();
            }
        };

        bottomLayout.setButtonCallback( bottomButtonListener );
    }

    private void doActionLayoutExit()
    {
        switch( currentMode )
        {
            case MODE_MAKE_TEAM :
                makeTeamLayoutData = makeTeamLayout.getData();
                break;

            case MODE_INFO_TEAM :
                teamInfoLayoutData = teamInfoLayout.getData();
                break;

            case MODE_IMAGE_SELECT :
                break;
        }
    }

    private void registerTeam(final NetworkdAdaptor.NetworkCallback inCallback)
    {
        HashMap<String, String> teamRegisterData = new HashMap<>();

        for( Map.Entry<String, String> entry : makeTeamLayoutData.entrySet() )
        {
            teamRegisterData.put(entry.getKey(), entry.getValue());
        }

        for( Map.Entry<String, String> entry : teamInfoLayoutData.entrySet() )
        {
            teamRegisterData.put(entry.getKey(), entry.getValue());
        }

        teamRegisterData.put("img_file", strImage);
        teamRegisterData.put("area", location);

        TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = "";

        try
        {
            if (telephony.getLine1Number() != null)
            {
                phoneNumber = telephony.getLine1Number();
            }
            else
            {
                if (telephony.getSimSerialNumber() != null)
                {
                    phoneNumber = telephony.getSimSerialNumber();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        teamRegisterData.put("team_phone", phoneNumber);

        NetworkdAdaptor.NetworkCallback callback = new NetworkdAdaptor.NetworkCallback() {
            @Override
            public void onResponse(JSONObject data)
            {
                try {
                    if(data.getBoolean("success") == true){
                        String id = (String)data.getJSONArray("result").get(0);
                        saveLogin(id);
                    } else {

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            //    inCallback.onResponse(data);
            }
        };

        NetworkdAdaptor.instance().setMakeRegister(callback, teamRegisterData);
    }

    private void saveLogin(String id){
        try {
            SharedPreferences setting = getSharedPreferences("setting",MODE_PRIVATE);
            SharedPreferences.Editor editor = setting.edit();

            Date date = new Date();

            SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd");
            String nowDate = sdf.format(date);

            editor.putString("date",nowDate);
            editor.putString("id",id);
            editor.commit();


            Intent intent = new Intent(getApplicationContext(),TeamSearchActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);

        } catch (Exception e){
            e.printStackTrace();
        }

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
                    strImage = BitmapUtil.getStringToBitamp(photo);

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
}
