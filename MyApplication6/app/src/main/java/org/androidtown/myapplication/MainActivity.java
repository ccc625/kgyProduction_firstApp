package org.androidtown.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSNSPermissions();
    }

    private void checkSNSPermissions(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this," 권한 없음", Toast.LENGTH_LONG).show();


            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)){
                Toast.makeText(this, "권한 설명 필요함", Toast.LENGTH_LONG).show();
            }else {
                String[] permissions = {
                        Manifest.permission.RECEIVE_SMS
                };

                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode ==1){
            for(int i= 0; i<permissions.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, permissions[i] + "권한 승인 됨.",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this, permissions[i] + "권한이 승인 되지 않음", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
