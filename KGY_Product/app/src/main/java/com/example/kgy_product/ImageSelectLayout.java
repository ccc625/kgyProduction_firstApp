package com.example.kgy_product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

/**
 * Created by ccc62 on 2017-05-13.
 */

public class ImageSelectLayout extends LinearLayout
{
    private Context mContext;

    private LinearLayout rootLayout;
    private Button btnUploadPicture;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absoultePath;

    private OnClickListener onClickListener;
    private PhotoActionListener photoActionListener;

    public ImageSelectLayout( Context context )
    {
        super( context );

        mContext = context;
        init();
    }

    public void setPhotoActionListener( PhotoActionListener listener )
    {
        photoActionListener = listener;
    }

    public void setImage( Bitmap image )
    {
        iv_UserPhoto.setImageBitmap(image);
    }

    private void init()
    {
        initDisplayObject();
        initListener();
    }

    private void initDisplayObject()
    {
        rootLayout = (LinearLayout) inflate( mContext, R.layout.image_select_layout, null );
        addView( rootLayout, new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ) );

        iv_UserPhoto = (ImageView) rootLayout.findViewById( R.id.imgUser );
        btnUploadPicture = (Button) rootLayout.findViewById( R.id.btnUploadPicture );
    }

    private void initListener()
    {
        onClickListener = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if( v.getId() == btnUploadPicture.getId() )
                {
                    DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialogInterface, int i) {
                            photoActionListener.doTakePhotoAction();
                        }

                    };
                    DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            photoActionListener.doTakeAlbumAction();
                        }
                    };
                    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    };
                    new AlertDialog.Builder(mContext)
                            .setTitle("업로드할 이미지 선택")
                            .setPositiveButton("사진촬영",cameraListener)
                            .setNeutralButton("앨범선택", albumListener)
                            .setNegativeButton("취소" , cancelListener)
                            .show();
                }
            }
        };

        btnUploadPicture.setOnClickListener( onClickListener );
    }

    public interface PhotoActionListener
    {
        void doTakePhotoAction();
        void doTakeAlbumAction();
    }
}
