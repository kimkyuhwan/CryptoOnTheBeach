package com.gyuhwan.android.blockchain.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.Keystore;
import com.gyuhwan.android.blockchain.dataSchema.User;
import com.gyuhwan.android.blockchain.dataSchema.UserData;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1000;

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

    boolean isImageUpdated = false;
    byte[] imagePath;
    @BindView(R.id.inputPrice)
    EditText inputPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
    }


    boolean isPossibleToUpload() {
        if (!isImageUpdated) {
            Toast.makeText(getApplicationContext(), "이미지 등록은 필수입니다", Toast.LENGTH_LONG).show();
            return false;
        } else if (inputName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "상품명 입력은 필수입니다", Toast.LENGTH_LONG).show();
            return false;
        } else if (inputPrice.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "가격 입력은 필수입니다", Toast.LENGTH_LONG).show();
            return false;
        } else if (inputUUID.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "UUID 불러오기 버튼을 눌러주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    void addItem() {
        UserData info = SharedPreferenceBase.getUserDataSharedPreference("user");
        Date now = new Date();
        String filename = info.getEmail() + now.toString();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", inputName.getText().toString())
                .addFormDataPart("category", inputCategory.getText().toString())
                .addFormDataPart("seller_id", String.valueOf(info.getId()))
                .addFormDataPart("uuid", inputUUID.getText().toString())
                .addFormDataPart("price", inputPrice.getText().toString())
                .addFormDataPart("file", filename + ".jpg", RequestBody.create(MediaType.parse("multipart/form-data"), imagePath))
                .build();
        MApplication.getInstance().getApiService().addThing(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.w("DEBUGYU", "SUCCESS");
                    Toast.makeText(AddItemActivity.this, "정상적으로 등록되었습니다", Toast.LENGTH_LONG).show();
                    finish();
                }
                Log.w("DEBUGYU", "DONE!");
                Log.w("DEBUGYU", "URL : !" + call.request().url().toString());
                Log.w("DEBUGYU", filename);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.w("DEBUGYU", "FAIL");
            }
        });

        // Block

        UserData userData = SharedPreferenceBase.getUserDataSharedPreference("user");
        Keystore temp = SharedPreferenceBase.getKeyStoreSharedPreference(userData.getEmail());
        String uuid = inputUUID.getText().toString();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(temp);

        RequestBody requestBody2 = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("keystore", jsonStr)
                .addFormDataPart("password", userData.getPassword())
                .addFormDataPart("sn", uuid)
                .build();
        MApplication.getInstance().getChainService().registerItem(requestBody2).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("DEBUGYU", "SUccess");
                    Toast.makeText(getApplicationContext(), "트랜잭션이 Confirm 되었습니다", Toast.LENGTH_LONG).show();
                } else
                    Log.d("DEBUGYU", "Done");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("DEBUGYU", "Fail");
                Log.w("DEBUGYU", t.getLocalizedMessage());
            }
        });
    }

    @OnClick({R.id.itemImageBg, R.id.itemImgBtn, R.id.uuidLoadBtn, R.id.addItemBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemImageBg:
            case R.id.itemImgBtn:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                break;
            case R.id.uuidLoadBtn:
                inputUUID.setText(GetDevicesUUID(this));
                break;
            case R.id.addItemBtn:
                if(isPossibleToUpload())
                    addItem();
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
        androidId = "" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(data.getData());
                setImage(getBytes(is), data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    public void setImage(byte[] bytes, Uri uri) {
        imagePath = bytes;
        Picasso.get().load(uri).into(itemImageBg);
        itemImgBtn.setVisibility(View.INVISIBLE);
        isImageUpdated = true;
    }
}
