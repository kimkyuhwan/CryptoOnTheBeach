package com.gyuhwan.android.blockchain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.Code;
import com.gyuhwan.android.blockchain.dataSchema.EthereumAccount;
import com.gyuhwan.android.blockchain.dataSchema.Keystore;
import com.gyuhwan.android.blockchain.util.SharedPreferenceBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.inputName)
    EditText inputName;
    @BindView(R.id.inputEthAddress)
    EditText inputEthAddress;
    @BindView(R.id.joinBtn)
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        inputEthAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    if(isPossibleToJoin()){
                        join();
                    }
                }
                return false;
            }
        });
    }



    @OnClick(R.id.joinBtn)
    public void onViewClicked() {
        if(isPossibleToJoin()){
            join();
        }
    }

    boolean isPossibleToJoin(){
        if(inputEmail.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"이메일을 입력해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(inputPassword.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(inputName.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"사용자 이름을 입력해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(inputEthAddress.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"이더리움 주소를 입력해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void join(){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", inputEmail.getText().toString())
                .addFormDataPart("password", inputPassword.getText().toString())
                .addFormDataPart("username", inputName.getText().toString())
                .addFormDataPart("etheraddress", inputEthAddress.getText().toString())
                .build();
        MApplication.getInstance().getApiService().addUser(requestBody).
                enqueue(new Callback<Code>() {
                    @Override
                    public void onResponse(Call<Code> call, Response<Code> response) {
                        if(response.isSuccessful()){
                            String result=response.body().getCode();
                            Log.w("DEBUGYU`","SUCCESS"+response.body().getCode());
                            if(result.equals("EXIST")){
                                Toast.makeText(getApplicationContext(),"이미 존재하는 이메일 주소입니다",Toast.LENGTH_LONG).show();
                                inputEmail.setText("");
                                inputPassword.setText("");
                            }
                            else if(result.equals("WRONG_PASSWORD")){
                                Toast.makeText(getApplicationContext(),"비밀번호가 잘못되었습니다",Toast.LENGTH_LONG).show();;
                                inputPassword.setText("");
                            }
                            else if(result.equals("OK")){
                                getEthereumAccount();
                                Toast.makeText(getApplicationContext(),"회원가입 되었습니다",Toast.LENGTH_LONG).show();
                                Intent it=new Intent(SignUpActivity.this,SignInActivity.class);
                                startActivity(it);
                                finish();
                            }

                        }
                        else{
                            Log.w("DEBUGYU`","DONE "+response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Code> call, Throwable t) {

                    }
                });
    }
    public void getEthereumAccount() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("password", inputPassword.getText().toString())
                .build();
        Log.w("DEBUGYU", requestBody.toString());
        MApplication.getInstance().getChainService().getEthereumAccount(requestBody)
                .enqueue(new Callback<EthereumAccount>() {
                    @Override
                    public void onResponse(Call<EthereumAccount> call, Response<EthereumAccount> response) {
                        if (response.isSuccessful()) {
                            EthereumAccount temp = response.body();
                            Keystore beSavedKeyStore = temp.getData().getKeystore();
                            Log.d("DEBUGYU", beSavedKeyStore.getAddress());
                            SharedPreferenceBase.putKeyStoreSharedPreference(inputEmail.getText().toString(), beSavedKeyStore);

                        } else {

                            Log.w("DEBUGYU", "IN : " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<EthereumAccount> call, Throwable t) {
                        Log.w("DEBUGYU", "FAIL : " + t.getMessage());
                        Log.w("DEBUGYU", "URL : " + call.request().url().toString());

                        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
