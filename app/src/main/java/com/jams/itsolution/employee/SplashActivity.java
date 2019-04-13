package com.jams.itsolution.employee;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class SplashActivity extends Activity {
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        cd = new ConnectionDetector(SplashActivity.this);

       if(cd.isConnectingToInternet()){
           callMainAct();
       }else {
           Toast.makeText(getBaseContext(),"No Internet...",Toast.LENGTH_SHORT).show();
       }


    }


    public void  callMainAct(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            if (weHavePermissionToReadExternalStorage() && weHavePermissionToWriteExternalStorage()) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }, 1500);


            } else {

                requestWriteExternalStoragePermissionFirst();


            }

        } else {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }, 1500);


        }

    }
    private boolean weHavePermissionToReadExternalStorage() {
            return ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean weHavePermissionToWriteExternalStorage() {
            return ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestWriteExternalStoragePermissionFirst() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            requestForResultWriteExternalStoragePermission();
        } else {
            requestForResultWriteExternalStoragePermission();
        }
    }

    private void requestForResultWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
    }


    private void requestReadExternalStoragePermissionFirst() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            requestForResultReadExternalStoragePermission();
        } else {
            requestForResultReadExternalStoragePermission();
        }
    }

    private void requestForResultReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 112);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == 111&& grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            if(!weHavePermissionToReadExternalStorage()){

                requestReadExternalStoragePermissionFirst();


            }else {

                callMainAct();

            }


        }else if (requestCode == 112&& grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

              callMainAct();


        }



    }


}
