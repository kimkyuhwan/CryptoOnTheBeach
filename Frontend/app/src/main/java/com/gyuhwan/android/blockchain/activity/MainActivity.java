package com.gyuhwan.android.blockchain.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gyuhwan.android.blockchain.R;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gyuhwan.android.blockchain.contract.SimpleStorage;
import com.gyuhwan.android.blockchain.contract.SimpleStroageTranscationManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_value)
    EditText editValue;
    @BindView(R.id.btnsetValue)
    Button btnsetValue;
    @BindView(R.id.btngetValue)
    Button btngetValue;
    @BindView(R.id.tx_value)
    TextView txValue;
    SimpleStroageTranscationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initManager();


    }

    void initManager(){
        manager=SimpleStroageTranscationManager.getInstance();
    }


    @OnClick({R.id.btnsetValue, R.id.btngetValue})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.btnsetValue:
                manager.setContractValueExecute(editValue.getText().toString());
                break;
            case R.id.btngetValue:
                manager.getContractValueAndSetTextView(txValue);
                break;
        }
    }
}