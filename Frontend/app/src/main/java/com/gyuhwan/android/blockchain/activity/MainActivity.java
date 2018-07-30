package com.gyuhwan.android.blockchain.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gyuhwan.android.blockchain.R;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread() {
            public void run() {
                Web3j web3j = Web3jFactory.build(new HttpService());
                try

                {
                    Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
                    String clientversion = web3ClientVersion.getWeb3ClientVersion();
                    Log.d("DEBUGYU", "Ver : " + clientversion);
                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                }
            }
        }.start();

        //      Credentials credentials = WalletUtils.loadCredentials();
    }
}
