package com.gyuhwan.android.blockchain.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.R;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.textAddItem)
    TextView textAddItem;
    @BindView(R.id.itemImageBg)
    ImageView itemImageBg;
    @BindView(R.id.itemImgBtn)
    ImageButton itemImgBtn;
    @BindView(R.id.inputName)
    EditText inputName;
    @BindView(R.id.inputDescription)
    EditText inputDescription;
    @BindView(R.id.inputCategory)
    EditText inputCategory;
    @BindView(R.id.inputUUID)
    TextView inputUUID;
    @BindView(R.id.uuidLoadBtn)
    ImageButton uuidLoadBtn;
    @BindView(R.id.addItemBtn)
    Button addItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.itemImageBg, R.id.itemImgBtn, R.id.uuidLoadBtn, R.id.addItemBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemImageBg:
                break;
            case R.id.itemImgBtn:
                break;
            case R.id.uuidLoadBtn:
                inputUUID.setText(GetDevicesUUID(this));
                break;
            case R.id.addItemBtn:
                break;
        }
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

}
