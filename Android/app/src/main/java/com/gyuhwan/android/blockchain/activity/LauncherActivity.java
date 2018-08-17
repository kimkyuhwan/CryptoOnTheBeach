package com.gyuhwan.android.blockchain.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.R;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.functions.Action;

public class LauncherActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_READ_STATE = 0x01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        CheckPermission();

    }

    private String GetDevicesUUID(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
           return "No Permission";
        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }

    private void CheckPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)){
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("핸드폰 UUID를 확인하기 위해서는 해당 권한을 허용해야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:"+getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},MY_PERMISSION_READ_STATE);
            }
        }
        else{
            Log.d("DEBUGYU",GetDevicesUUID(this));
            Completable.timer(2000, TimeUnit.MILLISECONDS)
                    .subscribe(new Action() {
                                   @Override
                                   public void run() throws Exception {
                                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                       finish();
                                   }
                               }
                    );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_READ_STATE:
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]<0){
                        Toast.makeText(getApplicationContext(),"해당 권한을 활성화하셔야 합니다.",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Log.d("DEBUGYU",GetDevicesUUID(this));
                Completable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribe(new Action() {
                                       @Override
                                       public void run() throws Exception {
                                           startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                           finish();
                                       }
                                   }
                        );
                break;
        }
    }
}
