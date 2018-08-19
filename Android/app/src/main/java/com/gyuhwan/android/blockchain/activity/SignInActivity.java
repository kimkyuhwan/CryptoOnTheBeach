package com.gyuhwan.android.blockchain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.R;
import com.gyuhwan.android.blockchain.dataSchema.Code;
import com.gyuhwan.android.blockchain.dataSchema.User;
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

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.inputEmail)
    EditText inputEmail;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.signUpBtn)
    Button signUpBtn;
    @BindView(R.id.loginBtn)
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        inputPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    if(isPossibleToLogin()){
                        login();
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.signUpBtn, R.id.loginBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signUpBtn:
                Intent it=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(it);

                break;
            case R.id.loginBtn:
                if(isPossibleToLogin()){
                    login();
                }
                break;
        }
    }
    public boolean isPossibleToLogin(){
        if(inputEmail.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"이메일을 입력해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        if(inputPassword.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void login(){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", inputEmail.getText().toString())
                .addFormDataPart("password", inputPassword.getText().toString())
                .build();
        MApplication.getInstance().getApiService().login(requestBody).
                enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            String result=response.body().getCode();
                            SharedPreferenceBase.putUserDataSharedPreference("user",response.body().getUserdata());
                            Log.w("DEBUGYU`","SUCCESS"+response.body().getCode());
                            if(result.equals("NOT_FOUND")){
                                Toast.makeText(getApplicationContext(),"없는 이메일 주소입니다",Toast.LENGTH_LONG).show();
                                inputEmail.setText("");
                                inputPassword.setText("");
                            }
                            else if(result.equals("WRONG_PASSWORD")){
                                Toast.makeText(getApplicationContext(),"비밀번호가 잘못되었습니다",Toast.LENGTH_LONG).show();;
                                inputPassword.setText("");
                            }
                            else if(result.equals("OK")){
                                Toast.makeText(getApplicationContext(),"로그인 되었습니다",Toast.LENGTH_LONG).show();
                                Intent it=new Intent(SignInActivity.this,MainActivity.class);
                                startActivity(it);
                                finish();
                            }

                        }
                        else{
                            Log.w("DEBUGYU`","DONE "+response.body());
                        }

                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.w("DEBUGYU`","FAIL ");
                    }
                });
    }
}
